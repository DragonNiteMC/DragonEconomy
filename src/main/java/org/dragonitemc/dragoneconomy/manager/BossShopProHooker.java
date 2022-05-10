package org.dragonitemc.dragoneconomy.manager;


import com.ericlam.mc.eld.misc.DebugLogger;
import com.ericlam.mc.eld.services.LoggingService;
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

    private final DebugLogger logger;

    @Inject
    public BossShopProHooker(LoggingService loggingService) {
        this.logger = loggingService.getLogger(BossShopProHooker.class);
    }


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


        logger.info("正在強行改寫 BossShopPro 的 RewardTypes...");

        try {
           var field =  BSRewardType.class.getDeclaredField("types");
           field.setAccessible(true);
           field.set(null, new ArrayList<>());
        }catch (Exception e){
            logger.warn(e, "嘗試強行修改 types 屬性失敗。");
            e.printStackTrace();
        }

        BSRewardType.BungeeCordCommand = BSRewardType.registerType(new BSRewardTypeBungeeCordCommand(){
            @Override
            public boolean allowAsync() {
                return true;
            }
        });

        logger.info("已改寫 BungeeCordCommand");

        BSRewardType.BungeeCordServer = BSRewardType.registerType(new BSRewardTypeBungeeCordServer(){
            @Override
            public boolean allowAsync() {
                return true;
            }
        });

        logger.info("已改寫 BungeeCordServer");

        BSRewardType.Command = BSRewardType.registerType(new BSRewardTypeCommand(){
            @Override
            public boolean allowAsync() {
                return true;
            }
        });

        logger.info("已改寫 Command");

        BSRewardType.Enchantment = BSRewardType.registerType(new BSRewardTypeEnchantment(){
            @Override
            public boolean allowAsync() {
                return true;
            }
        });

        logger.info("已改寫 Enchantment");

        BSRewardType.Exp = BSRewardType.registerType(new BSRewardTypeExp(){
            @Override
            public boolean allowAsync() {
                return true;
            }
        });

        logger.info("已改寫 Exp");

        BSRewardType.Item = BSRewardType.registerType(new BSRewardTypeItem(){
            @Override
            public boolean allowAsync() {
                return true;
            }
        });

        logger.info("已改寫 Item");

        BSRewardType.ItemAll = BSRewardType.registerType(new BSRewardTypeItemAll(){
            @Override
            public boolean allowAsync() {
                return true;
            }
        });

        logger.info("已改寫 ItemAll");

        BSRewardType.Money = BSRewardType.registerType(new BSRewardTypeMoney(){
            @Override
            public boolean allowAsync() {
                return true;
            }
        });

        logger.info("已改寫 Money");

        BSRewardType.Permission = BSRewardType.registerType(new BSRewardTypePermission(){
            @Override
            public boolean allowAsync() {
                return true;
            }
        });

        logger.info("已改寫 Permission");

        BSRewardType.PlayerCommand = BSRewardType.registerType(new BSRewardTypePlayerCommand(){
            @Override
            public boolean allowAsync() {
                return true;
            }
        });

        logger.info("已改寫 PlayerCommand");

        BSRewardType.PlayerCommandOp = BSRewardType.registerType(new BSRewardTypePlayerCommandOp(){
            @Override
            public boolean allowAsync() {
                return true;
            }
        });

        logger.info("已改寫 PlayerCommandOp");


        BSRewardType.Points = BSRewardType.registerType(new BSRewardTypePoints(){
            @Override
            public boolean allowAsync() {
                return false;
            }
        });

        logger.info("已改寫 Points");

        BSRewardType.Shop = BSRewardType.registerType(new BSRewardTypeShop(){
            @Override
            public boolean allowAsync() {
                return true;
            }
        });

        logger.info("已改寫 Shop");

        BSRewardType.ShopPage = BSRewardType.registerType(new BSRewardTypeShopPage(){
            @Override
            public boolean allowAsync() {
                return true;
            }
        });

        logger.info("已改寫 ShopPage");

        wrldRewardType.register();
        wrldPriceType.register();

        logger.info("成功掛接 $wrld 為 PriceType 和 RewardType");


        logger.debug("開始 檢查 是否有被改寫: ");
        BSRewardType.values().forEach(type -> logger.debug("{0} 的 allowAsync 是 {1}", type.createNames()[0], type.allowAsync()));

    }
}
