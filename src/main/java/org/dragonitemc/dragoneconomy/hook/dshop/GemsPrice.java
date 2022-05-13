package org.dragonitemc.dragoneconomy.hook.dshop;

import org.bukkit.entity.Player;
import org.dragonitemc.dragoneconomy.api.EconomyService;
import org.dragonitemc.dragoneconomy.api.UpdateResult;
import org.dragonitemc.dragoneconomy.config.DragonEconomyMessage;
import org.dragonitemc.dragonshop.api.AsyncPriceTask;
import org.dragonitemc.dragonshop.api.PurchaseResult;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;

public final class GemsPrice extends AsyncPriceTask<Double> {

    @Inject
    private EconomyService economyService;

    @Inject
    private DragonEconomyMessage msg;

    public GemsPrice() {
        super("gems");

    }

    @Override
    public CompletableFuture<PurchaseResult> doPurchaseAsync(Double price, Player player) {
        return CompletableFuture.supplyAsync(() -> {
            var result = economyService.withdrawPlayer(player.getUniqueId(), price);
            if (result == UpdateResult.SUCCESS){
                return PurchaseResult.success();
            } else {
                return PurchaseResult.failed(msg.getResultMessage(result));
            }
        });
    }
}
