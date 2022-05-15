package org.dragonitemc.dragoneconomy.hook.dshop;

import org.bukkit.entity.Player;
import org.dragonitemc.dragoneconomy.api.EconomyService;
import org.dragonitemc.dragoneconomy.config.DragonEconomyMessage;
import org.dragonitemc.dragonshop.api.AsyncRewardTask;

import java.util.concurrent.CompletableFuture;

public final class GemsReward extends AsyncRewardTask<Object> {


    private final DragonEconomyMessage msg;

    private final EconomyService economyService;

    public GemsReward(DragonEconomyMessage msg, EconomyService economyService) {
        super("gems");
        this.msg = msg;
        this.economyService = economyService;
    }

    @Override
    public CompletableFuture<Void> giveRewardAsync(Object c, Player player) {
        var price = GemsPrice.toDouble(c, player);
        return CompletableFuture.runAsync(() -> {
            var result = economyService.depositPlayer(player.getUniqueId(), price);
            player.sendMessage(msg.getResultMessage(result));
        });
    }
}
