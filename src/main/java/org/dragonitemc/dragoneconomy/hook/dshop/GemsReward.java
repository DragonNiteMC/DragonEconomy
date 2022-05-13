package org.dragonitemc.dragoneconomy.hook.dshop;

import org.bukkit.entity.Player;
import org.dragonitemc.dragoneconomy.api.EconomyService;
import org.dragonitemc.dragoneconomy.config.DragonEconomyMessage;
import org.dragonitemc.dragonshop.api.AsyncRewardTask;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;

public final class GemsReward extends AsyncRewardTask<Double> {


    @Inject
    private DragonEconomyMessage msg;

    @Inject
    private EconomyService economyService;

    public GemsReward() {
        super("gems");
    }

    @Override
    public CompletableFuture<Void> giveRewardAsync(Double price, Player player) {
        return CompletableFuture.runAsync(() -> {
            var result = economyService.depositPlayer(player.getUniqueId(), price);
            player.sendMessage(msg.getResultMessage(result));
        });
    }
}
