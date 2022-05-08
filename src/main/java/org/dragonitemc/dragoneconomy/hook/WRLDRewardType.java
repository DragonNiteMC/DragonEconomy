package org.dragonitemc.dragoneconomy.hook;

import jnr.ffi.annotations.In;
import org.black_ixx.bossshop.core.BSBuy;
import org.black_ixx.bossshop.core.rewards.BSRewardTypeNumber;
import org.black_ixx.bossshop.managers.ClassManager;
import org.black_ixx.bossshop.managers.misc.InputReader;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.dragonitemc.dragoneconomy.api.NFTokenService;
import org.dragonitemc.dragoneconomy.config.DragonEconomyMessage;

import javax.inject.Inject;
import java.text.MessageFormat;

public final class WRLDRewardType extends BSRewardTypeNumber {

    @Inject
    private NFTokenService nfTokenService;
    @Inject
    private DragonEconomyMessage message;

    @Override
    public boolean isIntegerValue() {
        return false;
    }

    @Override
    public void giveReward(Player player, BSBuy bsBuy, Object o, ClickType clickType, int i) {
        try {
            double reward = ClassManager.manager.getMultiplierHandler().calculateRewardWithMultiplier(player, bsBuy, clickType, (double) o) * i;
            // 使用了阻塞運行
            nfTokenService.depositToken(player, reward, MessageFormat.format("在 {0} 商店的交易", bsBuy.getShop().getDisplayName())).block();
        } catch (Throwable e) {
            e.printStackTrace();
            ClassManager.manager.getBugFinder().severe("$wrld 交易失敗: " + e.getMessage());
        }
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
    public boolean canBuy(Player player, BSBuy bsBuy, boolean b, Object o, ClickType clickType) {
        return true;
    }

    @Override
    public String getDisplayReward(Player player, BSBuy bsBuy, Object o, ClickType clickType) {
        return ClassManager.manager
                .getMultiplierHandler()
                .calculateRewardDisplayWithMultiplier(player, bsBuy, clickType, (double) o,
                        message.getDisplay("wrld", "%number%")
                );
    }

    @Override
    public String[] createNames() {
        return new String[]{"$wrld", "wrld"};
    }

    @Override
    public boolean mightNeedShopUpdate() {
        return true;
    }

    @Override
    public boolean allowAsync() {
        return true;
    }

}
