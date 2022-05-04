package org.dragonitemc.dragoneconomy.api;

import com.ericlam.mc.eld.services.ScheduleService;
import com.nftworlds.wallet.event.PeerToPeerPayEvent;
import com.nftworlds.wallet.event.PlayerTransactEvent;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public interface NFTokenService {

    double getTokenPrice(OfflinePlayer player);

    ScheduleService.BukkitPromise<Void> depositToken(OfflinePlayer player, double amount, String reason);

    <T> void withdrawToken(Player player, double amount, T payload, String reason, Consumer<PlayerTransactEvent<T>> callback);

    void transferToken(Player from, Player to, double amount, String reason, Consumer<PeerToPeerPayEvent> callback);
}
