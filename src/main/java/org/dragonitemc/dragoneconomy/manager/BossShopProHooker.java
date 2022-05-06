package org.dragonitemc.dragoneconomy.manager;


import org.black_ixx.bossshop.BossShop;
import org.black_ixx.bossshop.managers.ClassManager;
import org.black_ixx.bossshop.managers.features.PointsManager;
import org.bukkit.plugin.Plugin;
import org.dragonitemc.dragoneconomy.hook.BossShopDragems;
import org.dragonitemc.dragoneconomy.hook.WRLDPriceType;
import org.dragonitemc.dragoneconomy.hook.WRLDRewardType;

import javax.inject.Inject;

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

        wrldRewardType.register();
        wrldPriceType.register();

    }
}
