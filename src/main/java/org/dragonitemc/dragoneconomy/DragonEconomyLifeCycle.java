package org.dragonitemc.dragoneconomy;

import com.ericlam.mc.eld.ELDLifeCycle;
import org.bukkit.plugin.java.JavaPlugin;
import org.dragonitemc.dragoneconomy.hook.PlaceholderHook;
import org.dragonitemc.dragoneconomy.hook.dshop.GemsPrice;
import org.dragonitemc.dragoneconomy.hook.dshop.GemsReward;
import org.dragonitemc.dragoneconomy.hook.dshop.WRLDPrice;
import org.dragonitemc.dragoneconomy.hook.dshop.WRLDReward;
import org.dragonitemc.dragonshop.api.ShopTaskService;

import javax.inject.Inject;

public class DragonEconomyLifeCycle implements ELDLifeCycle {

    @Inject
    private PlaceholderHook placeholderHook;

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

    @Override
    public void onEnable(JavaPlugin plugin) {
        if (plugin.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            plugin.getLogger().info("PlaceholderAPI is found, hooking into PlaceholderAPI...");
            placeholderHook.register();
            plugin.getServer().getPluginManager().registerEvents(placeholderHook, plugin);
        }

        taskService.addPriceTask(gemsPrice);
        taskService.addRewardTask(gemsReward);

        taskService.addPriceTask(wrldPrice);
        taskService.addRewardTask(wrldReward);

    }

    @Override
    public void onDisable(JavaPlugin plugin) {
    }


}
