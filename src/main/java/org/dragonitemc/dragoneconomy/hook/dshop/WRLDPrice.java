package org.dragonitemc.dragoneconomy.hook.dshop;

import org.bukkit.entity.Player;
import org.dragonitemc.dragoneconomy.api.NFTokenService;
import org.dragonitemc.dragonshop.api.AsyncPriceTask;
import org.dragonitemc.dragonshop.api.PurchaseResult;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public final class WRLDPrice extends AsyncPriceTask<Object> {


    private final NFTokenService tokenService;

    public WRLDPrice(NFTokenService tokenService) {
        super("wrld");
        this.tokenService = tokenService;
    }

    @Override
    public CompletableFuture<PurchaseResult> doPurchaseAsync(Object content, Player player) {
        var price = GemsPrice.toDouble(content, player);
        if (tokenService.getTokenPrice(player) < price){
            return CompletableFuture.completedFuture(PurchaseResult.failed("insufficient tokens"));
        }
        CompletableFuture<PurchaseResult> future = new CompletableFuture<>();
        tokenService.withdrawToken(player, price, "DragonEconomy", "&eDragonShop 交易&r", (result) -> {
            future.complete(PurchaseResult.success());
        });
        return future.completeOnTimeout(PurchaseResult.failed("&c交易失敗或逾時"), 1, TimeUnit.MINUTES);
    }

    @Override
    public CompletableFuture<Void> doRollBackAsync(Object content, Player player) {
        var aDouble = GemsPrice.toDouble(content, player);
        tokenService.depositToken(player, aDouble,  "&eDragonShop 交易&r");
        return CompletableFuture.completedFuture(null);
    }
}
