package org.dragonitemc.dragoneconomy.hook;

import com.ericlam.mc.eld.ELDependenci;
import org.dragonitemc.dragoneconomy.api.EconomyService;
import org.dragonitemc.dragoneconomy.api.NFTokenService;
import org.dragonitemc.dragoneconomy.config.DragonEconomyMessage;
import org.dragonitemc.dragoneconomy.hook.dshop.GemsPrice;
import org.dragonitemc.dragoneconomy.hook.dshop.GemsReward;
import org.dragonitemc.dragoneconomy.hook.dshop.WRLDPrice;
import org.dragonitemc.dragoneconomy.hook.dshop.WRLDReward;
import org.dragonitemc.dragonshop.api.ShopTaskService;

import javax.inject.Inject;

public class DragonShopHook {

    @Inject
    private EconomyService economyService;

    @Inject
    private NFTokenService tokenService;

    @Inject
    private DragonEconomyMessage message;

    public void hook() {

        ShopTaskService taskService = ELDependenci.getApi().exposeService(ShopTaskService.class);
        taskService.addPriceTask(new GemsPrice(economyService, message));
        taskService.addRewardTask(new GemsReward(message, economyService));

        taskService.addPriceTask(new WRLDPrice(tokenService));
        taskService.addRewardTask(new WRLDReward(tokenService));
    }
}
