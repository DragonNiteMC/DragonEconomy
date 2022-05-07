package org.dragonitemc.dragoneconomy.manager;

import com.ericlam.mc.eld.services.ScheduleService;
import org.dragonitemc.dragoneconomy.DragonEconomy;
import org.dragonitemc.dragoneconomy.api.AsyncEconomyService;
import org.dragonitemc.dragoneconomy.api.EconomyService;
import org.dragonitemc.dragoneconomy.api.UpdateResult;

import javax.inject.Inject;
import java.util.UUID;

public final class AsyncEconomyManager implements AsyncEconomyService {

    @Inject
    private EconomyService economyService;

    @Inject
    private ScheduleService scheduleService;

    @Inject
    private DragonEconomy dragonEconomy;

    @Override
    public ScheduleService.BukkitPromise<Boolean> hasAccount(UUID player) {
        return scheduleService.callAsync(dragonEconomy, () -> economyService.hasAccount(player));
    }

    @Override
    public ScheduleService.BukkitPromise<Double> getBalance(UUID player) {
        return scheduleService.callAsync(dragonEconomy, () -> economyService.getBalance(player));
    }

    @Override
    public ScheduleService.BukkitPromise<UpdateResult> withdrawPlayer(UUID player, double value) {
        return withdrawPlayer(player, value, "console");
    }

    @Override
    public ScheduleService.BukkitPromise<UpdateResult> withdrawPlayer(UUID player, double value, String operator) {
        return scheduleService.callAsync(dragonEconomy, () -> economyService.withdrawPlayer(player, value, operator));
    }

    @Override
    public ScheduleService.BukkitPromise<UpdateResult> depositPlayer(UUID player, double value) {
        return depositPlayer(player, value, "console");
    }

    @Override
    public ScheduleService.BukkitPromise<UpdateResult> depositPlayer(UUID player, double value, String operator) {
        return scheduleService.callAsync(dragonEconomy, () -> economyService.depositPlayer(player, value, operator));
    }

    @Override
    public ScheduleService.BukkitPromise<UpdateResult> setPlayer(UUID player, double value) {
        return setPlayer(player, value, "console");
    }

    @Override
    public ScheduleService.BukkitPromise<UpdateResult> setPlayer(UUID player, double value, String operator) {
        return scheduleService.callAsync(dragonEconomy, () -> economyService.setPlayer(player, value, operator));
    }

    @Override
    public ScheduleService.BukkitPromise<UpdateResult> transfer(UUID from, UUID to, double amount) {
        return scheduleService.callAsync(dragonEconomy, () -> economyService.transfer(from, to, amount));
    }
}
