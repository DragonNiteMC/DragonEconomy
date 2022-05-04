package org.dragonitemc.dragoneconomy.manager;

import com.ericlam.mc.eld.services.ScheduleService;
import org.bukkit.OfflinePlayer;
import org.dragonitemc.dragoneconomy.DragonEconomy;
import org.dragonitemc.dragoneconomy.api.AsyncEconomyService;
import org.dragonitemc.dragoneconomy.api.EconomyService;
import org.dragonitemc.dragoneconomy.api.UpdateResult;

import javax.inject.Inject;

public final class AsyncEconomyManager implements AsyncEconomyService {

    @Inject
    private EconomyService economyService;

    @Inject
    private ScheduleService scheduleService;

    @Inject
    private DragonEconomy dragonEconomy;

    @Override
    public ScheduleService.BukkitPromise<Boolean> hasAccount(OfflinePlayer player) {
        return scheduleService.callAsync(dragonEconomy, () -> economyService.hasAccount(player));
    }

    @Override
    public ScheduleService.BukkitPromise<Double> getBalance(OfflinePlayer player) {
        return scheduleService.callAsync(dragonEconomy, () -> economyService.getBalance(player));
    }

    @Override
    public ScheduleService.BukkitPromise<UpdateResult> withdrawPlayer(OfflinePlayer player, double value) {
        return withdrawPlayer(player, value, "console");
    }

    @Override
    public ScheduleService.BukkitPromise<UpdateResult> withdrawPlayer(OfflinePlayer player, double value, String operator) {
        return scheduleService.callAsync(dragonEconomy, () -> economyService.withdrawPlayer(player, value, operator));
    }

    @Override
    public ScheduleService.BukkitPromise<UpdateResult> depositPlayer(OfflinePlayer player, double value) {
        return depositPlayer(player, value, "console");
    }

    @Override
    public ScheduleService.BukkitPromise<UpdateResult> depositPlayer(OfflinePlayer player, double value, String operator) {
        return scheduleService.callAsync(dragonEconomy, () -> economyService.depositPlayer(player, value, operator));
    }

    @Override
    public ScheduleService.BukkitPromise<UpdateResult> setPlayer(OfflinePlayer player, double value) {
        return setPlayer(player, value, "console");
    }

    @Override
    public ScheduleService.BukkitPromise<UpdateResult> setPlayer(OfflinePlayer player, double value, String operator) {
        return scheduleService.callAsync(dragonEconomy, () -> economyService.setPlayer(player, value, operator));
    }

    @Override
    public ScheduleService.BukkitPromise<UpdateResult> transfer(OfflinePlayer from, OfflinePlayer to, double amount) {
        return scheduleService.callAsync(dragonEconomy, () -> economyService.transfer(from, to, amount));
    }
}
