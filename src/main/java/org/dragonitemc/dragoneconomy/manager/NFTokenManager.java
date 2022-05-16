package org.dragonitemc.dragoneconomy.manager;

import com.ericlam.mc.eld.misc.DebugLogger;
import com.ericlam.mc.eld.services.LoggingService;
import com.ericlam.mc.eld.services.ScheduleService;
import com.nftworlds.wallet.api.WalletAPI;
import com.nftworlds.wallet.event.PeerToPeerPayEvent;
import com.nftworlds.wallet.event.PlayerTransactEvent;
import com.nftworlds.wallet.objects.Network;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.dragonitemc.dragoneconomy.DragonEconomy;
import org.dragonitemc.dragoneconomy.api.NFTokenService;
import org.dragonitemc.dragoneconomy.payload.DragonEconomyPayload;

import javax.inject.Inject;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public final class NFTokenManager implements NFTokenService, Listener {

    @Inject
    private WalletAPI walletAPI;

    @Inject
    private ScheduleService scheduleService;

    @Inject
    private DragonEconomy dragonEconomy;

    private final Map<UUID, Consumer<PlayerTransactEvent<?>>> trasactCallbackMap = new ConcurrentHashMap<>();
    private final Map<String, Consumer<PeerToPeerPayEvent>> peerCallbackMap = new ConcurrentHashMap<>();
    private final DebugLogger logger;

    @Inject
    public NFTokenManager(LoggingService loggingService) {
        this.logger = loggingService.getLogger(NFTokenService.class);
    }


    @Override
    public double getTokenPrice(OfflinePlayer player) {
        return walletAPI.getPrimaryWallet(player.getUniqueId()).getWRLDBalance(Network.POLYGON);
    }

    @Override
    public void depositToken(OfflinePlayer player, double amount, String reason) {
        walletAPI.getNFTPlayer(player.getUniqueId()).sendWRLD(amount, Network.POLYGON, reason);
    }

    @Override
    public <T> void withdrawToken(Player player, double amount, T payload, String reason, Consumer<PlayerTransactEvent<T>> callback) {
        scheduleService.runAsync(dragonEconomy, () -> {
            var economyPayload = new DragonEconomyPayload<>(payload);
            this.trasactCallbackMap.put(economyPayload.getId(), e -> callback.accept((PlayerTransactEvent<T>) e));
            walletAPI.getNFTPlayer(player.getUniqueId()).requestWRLD(amount, Network.POLYGON, reason, false, economyPayload);
        }).join();
    }

    @Override
    public void transferToken(Player from, Player to, double amount, String reason, Consumer<PeerToPeerPayEvent> callback) {
        scheduleService.runAsync(dragonEconomy, () -> {
            String id = UUID.randomUUID().toString();
            String reasonStr = id + ":" + reason;
            this.peerCallbackMap.put(id, callback);
            var fromNft = walletAPI.getNFTPlayer(from.getUniqueId());
            var toNft = walletAPI.getNFTPlayer(to.getUniqueId());
            fromNft.createPlayerPayment(toNft, amount, Network.POLYGON, reasonStr);
        }).join();
    }


    @EventHandler
    public void onPlayerTransact(PlayerTransactEvent<?> e) {
        if (!(e.getPayload() instanceof DragonEconomyPayload dragonPayload)) return;
        logger.infoF("WRLD Transaction Completed for %s, Reason: %s, Amount: %.2f, Id: %s",
                e.getPlayer().getName(), e.getReason(), e.getAmount(), dragonPayload.getId());
        var callback = this.trasactCallbackMap.remove(dragonPayload.getId());
        if (callback == null) return;
        callback.accept(e);
        logger.debugF("WRLD Transaction Callback completed for %s, Reason: %s, Amount: %.2f, Id: %s",
                e.getPlayer().getName(), e.getReason(), e.getAmount(), dragonPayload.getId());
    }

    @EventHandler
    public void onPeerToPeerPay(PeerToPeerPayEvent e) {
        var reasonStr = e.getReason();
        var id = reasonStr.split(":")[0];
        logger.infoF("WRLD PeerToPeer Transaction Completed for %s, Reason: %s, Amount: %.2f, Id: %s",
                e.getFrom().getName(), e.getReason(), e.getAmount(), id);
        var callback = this.peerCallbackMap.remove(id);
        if (callback == null) return;
        callback.accept(e);
        logger.debugF("WRLD PeerToPeer Transaction Callback completed for %s, Reason: %s, Amount: %.2f, Id: %s",
                e.getFrom().getName(), e.getReason(), e.getAmount(), id);
    }
}
