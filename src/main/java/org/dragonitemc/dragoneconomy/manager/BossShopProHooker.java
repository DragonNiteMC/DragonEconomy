package org.dragonitemc.dragoneconomy.manager;


import org.black_ixx.bossshop.BossShop;
import org.black_ixx.bossshop.core.rewards.*;
import org.black_ixx.bossshop.managers.ClassManager;
import org.black_ixx.bossshop.managers.features.PointsManager;
import org.bukkit.plugin.Plugin;
import org.dragonitemc.dragoneconomy.hook.BossShopDragems;
import org.dragonitemc.dragoneconomy.hook.WRLDPriceType;
import org.dragonitemc.dragoneconomy.hook.WRLDRewardType;

import javax.inject.Inject;
import java.util.ArrayList;

public final class BossShopProHooker {

    @Inject
    private BossShopDragems bossShopDragems;

    @Inject
    private WRLDRewardType wrldRewardType;

    @Inject
    private WRLDPriceType wrldPriceType;

    public void hook(Plugin bossShop) {

        // var bs = (BossShop) bossShop;

        if (!ClassManager.manager.getSettings().getPurchaseAsync()) {
            bossShop.getLogger().warning("發現 BossShopPro 的 config.yml 沒有設置 AsynchronousActions 為 true 。這將會導致主線程卡頓。");
            bossShop.getLogger().warning("目前已強制設置其為 true, 但請之後自行到 config.yml 設置 AsynchronousActions: true");
            ClassManager.manager.getSettings().setPurchaseAsyncEnabled(true);
        }

        bossShopDragems.register();
        ClassManager.manager.getSettings().setPointsEnabled(true);
        PointsManager.PointsPlugin plugin = PointsManager.PointsPlugin.CUSTOM;
        plugin.setCustom(bossShopDragems.getName());
        ClassManager.manager.getSettings().setPointsPlugin(plugin);


        BSRewardType.BungeeCordCommand = BSRewardType.registerType(new BSRewardTypeBungeeCordCommand(){
            @Override
            public boolean allowAsync() {
                return true;
            }
        });
        BSRewardType.BungeeCordServer = BSRewardType.registerType(new BSRewardTypeBungeeCordServer(){
            @Override
            public boolean allowAsync() {
                return true;
            }
        });
        BSRewardType.Command = BSRewardType.registerType(new BSRewardTypeCommand(){
            @Override
            public boolean allowAsync() {
                return true;
            }
        });
        BSRewardType.Enchantment = BSRewardType.registerType(new BSRewardTypeEnchantment(){
            @Override
            public boolean allowAsync() {
                return true;
            }
        });
        BSRewardType.Exp = BSRewardType.registerType(new BSRewardTypeExp(){
            @Override
            public boolean allowAsync() {
                return true;
            }
        });
        BSRewardType.Item = BSRewardType.registerType(new BSRewardTypeItem(){
            @Override
            public boolean allowAsync() {
                return true;
            }
        });
        BSRewardType.ItemAll = BSRewardType.registerType(new BSRewardTypeItemAll(){
            @Override
            public boolean allowAsync() {
                return true;
            }
        });
        BSRewardType.Money = BSRewardType.registerType(new BSRewardTypeMoney(){
            @Override
            public boolean allowAsync() {
                return true;
            }
        });
        BSRewardType.Permission = BSRewardType.registerType(new BSRewardTypePermission(){
            @Override
            public boolean allowAsync() {
                return true;
            }
        });
        BSRewardType.PlayerCommand = BSRewardType.registerType(new BSRewardTypePlayerCommand(){
            @Override
            public boolean allowAsync() {
                return true;
            }
        });
        BSRewardType.PlayerCommandOp = BSRewardType.registerType(new BSRewardTypePlayerCommandOp(){
            @Override
            public boolean allowAsync() {
                return true;
            }
        });
        BSRewardType.Points = BSRewardType.registerType(new BSRewardTypePoints(){
            @Override
            public boolean allowAsync() {
                return false;
            }
        });
        BSRewardType.Shop = BSRewardType.registerType(new BSRewardTypeShop(){
            @Override
            public boolean allowAsync() {
                return true;
            }
        });
        BSRewardType.ShopPage = BSRewardType.registerType(new BSRewardTypeShopPage(){
            @Override
            public boolean allowAsync() {
                return true;
            }
        });

        wrldRewardType.register();
        wrldPriceType.register();

    }
}
