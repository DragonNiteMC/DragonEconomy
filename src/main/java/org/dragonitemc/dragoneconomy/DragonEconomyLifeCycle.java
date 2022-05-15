package org.dragonitemc.dragoneconomy;

import com.ericlam.mc.eld.ELDLifeCycle;
import org.bukkit.plugin.java.JavaPlugin;
import org.dragonitemc.dragoneconomy.hook.DragonShopHook;
import org.dragonitemc.dragoneconomy.hook.PlaceholderHook;

import javax.inject.Inject;

public class DragonEconomyLifeCycle implements ELDLifeCycle {

    @Inject
    private PlaceholderHook placeholderHook;

    @Inject
    private DragonShopHook dragonShopHook;

    @Override
    public void onEnable(JavaPlugin plugin) {
        if (plugin.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            plugin.getLogger().info("PlaceholderAPI is found, hooking into PlaceholderAPI...");
            placeholderHook.register();
            plugin.getServer().getPluginManager().registerEvents(placeholderHook, plugin);
        }


        if (plugin.getServer().getPluginManager().getPlugin("DragonShop") != null) {
            plugin.getLogger().info("DragonShop is found, hooking into DragonShop...");
            dragonShopHook.hook();
        }

    }

    @Override
    public void onDisable(JavaPlugin plugin) {
    }


}
