package org.dragonitemc.dragoneconomy.api;

import com.ericlam.mc.eld.services.ScheduleService;
import org.bukkit.OfflinePlayer;

public interface AsyncEconomyService {

    ScheduleService.BukkitPromise<Boolean> hasAccount(OfflinePlayer player);

    ScheduleService.BukkitPromise<Double> getBalance(OfflinePlayer player);

    ScheduleService.BukkitPromise<UpdateResult> withdrawPlayer(OfflinePlayer player, double value);

    ScheduleService.BukkitPromise<UpdateResult> withdrawPlayer(OfflinePlayer player, double value, String operator);

    ScheduleService.BukkitPromise<UpdateResult> depositPlayer(OfflinePlayer player, double value);

    ScheduleService.BukkitPromise<UpdateResult> depositPlayer(OfflinePlayer player, double value, String operator);

    ScheduleService.BukkitPromise<UpdateResult> setPlayer(OfflinePlayer player, double value);

    ScheduleService.BukkitPromise<UpdateResult> setPlayer(OfflinePlayer player, double value, String operator);

    ScheduleService.BukkitPromise<UpdateResult> transfer(OfflinePlayer from, OfflinePlayer to, double amount);
    
}
