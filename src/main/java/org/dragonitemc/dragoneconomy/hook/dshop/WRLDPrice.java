package org.dragonitemc.dragoneconomy.hook.dshop;

import org.bukkit.entity.Player;
import org.dragonitemc.dragoneconomy.api.NFTokenService;
import org.dragonitemc.dragonshop.api.AsyncPriceTask;
import org.dragonitemc.dragonshop.api.PurchaseResult;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public final class WRLDPrice extends AsyncPriceTask<Double> {


    @Inject
    private NFTokenService tokenService;

    public WRLDPrice() {
        super("wrld");
    }

    @Override
    public CompletableFuture<PurchaseResult> doPurchaseAsync(Double price, Player player) {
        CompletableFuture<PurchaseResult> future = new CompletableFuture<>();
        tokenService.withdrawToken(player, price, "DragonEconomy", "&eDragonShop 交易&r", (result) -> {
            future.complete(PurchaseResult.success());
        });
        return future.completeOnTimeout(PurchaseResult.failed("&c交易失敗或逾時"), 1, TimeUnit.MINUTES);
    }
}
