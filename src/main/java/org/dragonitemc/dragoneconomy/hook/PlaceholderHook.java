package org.dragonitemc.dragoneconomy.hook;

import jnr.ffi.annotations.In;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.dragonitemc.dragoneconomy.DragonEconomy;
import org.dragonitemc.dragoneconomy.api.AsyncEconomyService;
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

    private final Map<UUID, Double> balanceCache = new ConcurrentHashMap<>();

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        return switch (params.toLowerCase(Locale.ROOT)) {
            case "balance" -> String.valueOf(balanceCache.getOrDefault(player.getUniqueId(), 0.0));
            case "wrld" -> String.valueOf(nfTokenService.getTokenPrice(player));
            default -> null;
        };
    }

    @EventHandler
    public void onTransactionLog(TransactionLogEvent event){
        TransactionLog log = event.getLog();
        economyService.getBalance(log.getUser().getUuid()).thenRunAsync(balance -> {
            this.balanceCache.put(log.getUser().getUuid(), balance);
            plugin.getLogger().info("Cache updated.");
        });
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        economyService.getBalance(e.getPlayer().getUniqueId()).thenRunAsync(balance -> {
            this.balanceCache.put(e.getPlayer().getUniqueId(), balance);
            plugin.getLogger().info("Cache updated.");
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
}
