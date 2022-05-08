package org.dragonitemc.dragoneconomy;

import com.ericlam.mc.eld.ELDLifeCycle;
import com.ericlam.mc.eld.services.ScheduleService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.dragonitemc.dragoneconomy.db.EconomyUser;
import org.dragonitemc.dragoneconomy.hook.PlaceholderHook;
import org.dragonitemc.dragoneconomy.manager.BossShopProHooker;
import org.dragonitemc.dragoneconomy.repository.EconomyUserRepository;

import javax.inject.Inject;

public class DragonEconomyLifeCycle implements ELDLifeCycle {

    @Inject
    private BossShopProHooker bossShopProHooker;

    @Inject
    private PlaceholderHook placeholderHook;

    @Override
    public void onEnable(JavaPlugin plugin) {

        var bossShop = plugin.getServer().getPluginManager().getPlugin("BossShopPro");
        if (bossShop != null) {
            plugin.getLogger().info("BossShopPro is found, hooking into BossShopPro...");
            bossShopProHooker.hook(bossShop);
        }

        if (plugin.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            plugin.getLogger().info("PlaceholderAPI is found, hooking into PlaceholderAPI...");
            placeholderHook.register();
            plugin.getServer().getPluginManager().registerEvents(placeholderHook, plugin);
        }
    }

    @Override
    public void onDisable(JavaPlugin plugin) {
    }


}
