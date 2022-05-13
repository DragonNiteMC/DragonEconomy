package org.dragonitemc.dragoneconomy.hook.bsp;

import com.nftworlds.wallet.event.PlayerTransactEvent;
import org.black_ixx.bossshop.core.BSBuy;
import org.black_ixx.bossshop.core.prices.BSPriceTypeNumber;
import org.black_ixx.bossshop.managers.ClassManager;
import org.black_ixx.bossshop.managers.misc.InputReader;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.dragonitemc.dragoneconomy.api.NFTokenService;
import org.dragonitemc.dragoneconomy.api.UpdateResult;
import org.dragonitemc.dragoneconomy.config.DragonEconomyMessage;

import javax.inject.Inject;
import java.text.MessageFormat;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class WRLDPriceType extends BSPriceTypeNumber {

    @Inject
    private NFTokenService nfTokenService;

    @Inject
    private DragonEconomyMessage message;

    @Override
    public boolean isIntegerValue() {
        return false;
    }

    @Override
    public String takePrice(Player player, BSBuy bsBuy, Object o, ClickType clickType, int i) {
        try {
            double prices = ClassManager.manager.getMultiplierHandler().calculatePriceWithMultiplier(player, bsBuy, clickType, (Double) o) * i;
            CompletableFuture<PlayerTransactEvent<String>> future = new CompletableFuture<>();
            nfTokenService.withdrawToken(player, prices, "DragonEconomy",
                    MessageFormat.format("商店 {0} 的交易", bsBuy.getShop().getDisplayName()),
                    future::complete
            );
            future.get(1, TimeUnit.MINUTES);
        }catch (Throwable e) {
            if (!(e instanceof TimeoutException)) {
                ClassManager.manager.getBugFinder().severe("$wrld 交易失敗: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return message.getDisplay("wrld", nfTokenService.getTokenPrice(player));
    }

    @Override
    public boolean hasPrice(Player player, BSBuy bsBuy, Object o, ClickType clickType, int i, boolean b) {
        double prices = ClassManager.manager.getMultiplierHandler().calculatePriceWithMultiplier(player, bsBuy, clickType, (Double) o) * i;
        if (nfTokenService.getTokenPrice(player) < prices) {
            player.sendMessage(message.getResultMessage(UpdateResult.BALANCE_INSUFFICIENT));
            return false;
        }
        return true;
    }

    @Override
    public String getDisplayBalance(Player player, BSBuy bsBuy, Object o, ClickType clickType) {
        return message.getDisplay("wrld", nfTokenService.getTokenPrice(player));
    }

    @Override
    public Object createObject(Object o, boolean b) {
        return InputReader.getDouble(o, -1);
    }

    @Override
    public boolean validityCheck(String s, Object o) {
        if ((Double) o != -1) {
            return true;
        }
        ClassManager.manager.getBugFinder().severe("物品" + s + "為無效的數值: " + o);
        return false;
    }

    @Override
    public void enableType() {

    }

    @Override
    public String getDisplayPrice(Player player, BSBuy bsBuy, Object o, ClickType clickType) {
        return ClassManager.manager.getMultiplierHandler().calculatePriceDisplayWithMultiplier(player, bsBuy, clickType, (Double) o, message.getDisplay("wrld", "%number%"));
    }

    @Override
    public String[] createNames() {
        return new String[]{"$wrld", "wrld"};
    }

    @Override
    public boolean mightNeedShopUpdate() {
        return true;
    }
}
