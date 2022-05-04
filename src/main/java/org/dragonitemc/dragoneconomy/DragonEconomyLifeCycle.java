package org.dragonitemc.dragoneconomy;

import com.ericlam.mc.eld.ELDLifeCycle;
import com.ericlam.mc.eld.annotations.ELDPlugin;
import org.bukkit.plugin.java.JavaPlugin;


@ELDPlugin(
        lifeCycle = DragonEconomyLifeCycle.class,
        registry = DragonEconomyRegistry.class
)
public class DragonEconomyLifeCycle implements ELDLifeCycle {
    
    @Override
    public void onEnable(JavaPlugin plugin) {
        
    }

    @Override
    public void onDisable(JavaPlugin plugin) {

    }
}
