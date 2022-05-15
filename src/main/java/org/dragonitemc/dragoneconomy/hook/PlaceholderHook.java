package org.dragonitemc.dragoneconomy.hook;

import org.dragonitemc.dragoneconomy.DragonEconomy;
import org.dragonitemc.dragoneconomy.api.AsyncEconomyService;
import org.dragonitemc.dragoneconomy.api.FERService;
import org.dragonitemc.dragoneconomy.api.NFTokenService;

import javax.inject.Inject;

public class PlaceholderHook {

    @Inject
    private DragonEconomy plugin;

    @Inject
    private AsyncEconomyService economyService;

    @Inject
    private NFTokenService nfTokenService;

    @Inject
    private FERService ferService;


    public void hook(){
        var papi = new DragonEconomyPlaceholder(plugin, economyService, nfTokenService, ferService);
        plugin.getServer().getPluginManager().registerEvents(papi, plugin);
        papi.register();
    }
}
