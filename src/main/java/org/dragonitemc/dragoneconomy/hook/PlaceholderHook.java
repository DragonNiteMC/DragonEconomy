package org.dragonitemc.dragoneconomy.hook;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.dragonitemc.dragoneconomy.DragonEconomy;
import org.dragonitemc.dragoneconomy.api.AsyncEconomyService;
import org.dragonitemc.dragoneconomy.api.FERService;
import org.dragonitemc.dragoneconomy.api.NFTokenService;
import org.dragonitemc.dragoneconomy.api.TransactionLogEvent;
import org.dragonitemc.dragoneconomy.db.TransactionLog;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class PlaceholderHook extends PlaceholderExpansion implements Listener {

    @Inject
    private DragonEconomy plugin;

    @Inject
    private AsyncEconomyService economyService;

    @Inject
    private NFTokenService nfTokenService;

    @Inject
    private FERService ferService;

    private final Map<UUID, Double> balanceCache = new ConcurrentHashMap<>();

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        return switch (params.toLowerCase(Locale.ROOT)) {
            case "balance" -> String.format("%.2f", balanceCache.getOrDefault(player.getUniqueId(), 0.0));
            case "wrld" -> String.format("%.2f", nfTokenService.getTokenPrice(player));
            case "fer" -> String.format("%.2f", ferService.getExchangeRate());
            default -> null;
        };
    }

    @EventHandler
    public void onTransactionLog(TransactionLogEvent event){
        TransactionLog log = event.getLog();
        economyService.getBalance(log.getTarget().getId()).thenRunSync(balance -> {
            this.balanceCache.put(log.getTarget().getId(), balance);
            plugin.getLogger().info("Cache updated: "+log.getTarget().getId());
        }).joinWithCatch(ex -> {
            plugin.getLogger().warning("Failed to update cache: "+ex.getMessage());
            ex.printStackTrace();
        });
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        economyService.getBalance(e.getPlayer().getUniqueId()).thenRunSync(balance -> {
            this.balanceCache.put(e.getPlayer().getUniqueId(), balance);
            plugin.getLogger().info("Cache updated: "+e.getPlayer().getUniqueId());
        }).joinWithCatch(ex -> {
            plugin.getLogger().warning("Failed to update cache: "+ex.getMessage());
            ex.printStackTrace();
        });
    }

    @Override
    public @NotNull String getIdentifier() {
        return plugin.getName().toLowerCase(Locale.ROOT);
    }

    @Override
    public @NotNull String getAuthor() {
        return plugin.getDescription().getAuthors().toString();
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }
}
