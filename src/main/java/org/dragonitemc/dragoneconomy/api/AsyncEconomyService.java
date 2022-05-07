package org.dragonitemc.dragoneconomy.api;

import com.ericlam.mc.eld.services.ScheduleService;

import java.util.UUID;

public interface AsyncEconomyService {

    ScheduleService.BukkitPromise<Boolean> hasAccount(UUID player);

    ScheduleService.BukkitPromise<Double> getBalance(UUID player);

    ScheduleService.BukkitPromise<UpdateResult> withdrawPlayer(UUID player, double value);

    ScheduleService.BukkitPromise<UpdateResult> withdrawPlayer(UUID player, double value, String operator);

    ScheduleService.BukkitPromise<UpdateResult> depositPlayer(UUID player, double value);

    ScheduleService.BukkitPromise<UpdateResult> depositPlayer(UUID player, double value, String operator);

    ScheduleService.BukkitPromise<UpdateResult> setPlayer(UUID player, double value);

    ScheduleService.BukkitPromise<UpdateResult> setPlayer(UUID player, double value, String operator);

    ScheduleService.BukkitPromise<UpdateResult> transfer(UUID from, UUID to, double amount);

}
