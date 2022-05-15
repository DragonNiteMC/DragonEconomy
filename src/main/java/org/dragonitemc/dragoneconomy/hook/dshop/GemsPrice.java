package org.dragonitemc.dragoneconomy.hook.dshop;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.dragonitemc.dragoneconomy.api.EconomyService;
import org.dragonitemc.dragoneconomy.api.UpdateResult;
import org.dragonitemc.dragoneconomy.config.DragonEconomyMessage;
import org.dragonitemc.dragonshop.ShopException;
import org.dragonitemc.dragonshop.api.AsyncPriceTask;
import org.dragonitemc.dragonshop.api.PurchaseResult;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;

public final class GemsPrice extends AsyncPriceTask<Object> {

    @Inject
    private EconomyService economyService;

    @Inject
    private DragonEconomyMessage msg;

    public GemsPrice() {
        super("gems");

    }

    static double toDouble(Object price, Player player){

        if (price instanceof Double){
            return (double) price;
        }
        if (price instanceof Integer){
            return (int) price;
        }
        if (price instanceof Float){
            return (float) price;
        }


        if (price instanceof String ps){
            try{
                return Double.parseDouble(PlaceholderAPI.setPlaceholders(player, ps));
            }catch (NumberFormatException e){
                throw new ShopException("設置錯誤", "String 返回的不是數值類型: " + e.getMessage());
            }
        }

        throw new ShopException("設置錯誤", "不支援的 Gems 類型: " + price.getClass().getName() + ", 只支援: Double, Integer, Float, String");
    }

    @Override
    public CompletableFuture<PurchaseResult> doPurchaseAsync(Object c, Player player) {
        var price = toDouble(c, player);
        return CompletableFuture.supplyAsync(() -> {
            var result = economyService.withdrawPlayer(player.getUniqueId(), price);
            if (result == UpdateResult.SUCCESS){
                return PurchaseResult.success();
            } else {
                return PurchaseResult.failed(msg.getResultMessage(result));
            }
        });
    }

    @Override
    public CompletableFuture<Void> doRollBackAsync(Object c, Player player) {
        var price = toDouble(c, player);
        return CompletableFuture.runAsync(() -> economyService.depositPlayer(player.getUniqueId(), price));
    }
}
