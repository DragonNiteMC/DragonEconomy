package org.dragonitemc.dragoneconomy.hook;

import org.dragonitemc.dragoneconomy.hook.dshop.GemsPrice;
import org.dragonitemc.dragoneconomy.hook.dshop.GemsReward;
import org.dragonitemc.dragoneconomy.hook.dshop.WRLDPrice;
import org.dragonitemc.dragoneconomy.hook.dshop.WRLDReward;
import org.dragonitemc.dragonshop.api.ShopTaskService;

import javax.inject.Inject;

public class DragonShopHook {
    @Inject
    private GemsReward gemsReward;
    @Inject
    private GemsPrice gemsPrice;
    @Inject
    private WRLDReward wrldReward;
    @Inject
    private WRLDPrice wrldPrice;
    @Inject
    private ShopTaskService taskService;

    public void hook(){
        taskService.addPriceTask(gemsPrice);
        taskService.addRewardTask(gemsReward);

        taskService.addPriceTask(wrldPrice);
        taskService.addRewardTask(wrldReward);
    }
}
