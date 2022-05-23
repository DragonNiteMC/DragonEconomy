package org.dragonitemc.dragoneconomy;

import com.ericlam.mc.eld.bukkit.ELDLifeCycle;
import org.bukkit.plugin.java.JavaPlugin;
import org.dragonitemc.dragoneconomy.hook.PlaceholderHook;

import javax.inject.Inject;

public class DragonEconomyLifeCycle implements ELDLifeCycle {

    @Inject
    private PlaceholderHook placeholderHook;

    @Override
    public void onEnable(JavaPlugin plugin) {
        if (plugin.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            plugin.getLogger().info("PlaceholderAPI is found, hooking into PlaceholderAPI...");
            placeholderHook.hook();
        }
    }

    @Override
    public void onDisable(JavaPlugin plugin) {
    }


}
