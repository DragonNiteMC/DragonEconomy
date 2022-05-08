package org.dragonitemc.dragoneconomy;

import com.ericlam.mc.eld.services.ScheduleService;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.dragonitemc.dragoneconomy.api.TransactionLogEvent;
import org.dragonitemc.dragoneconomy.db.EconomyUser;
import org.dragonitemc.dragoneconomy.repository.EconomyUserRepository;

import javax.inject.Inject;

public class DragonEconomyListener implements Listener {

    @Inject
    private EconomyUserRepository repository;

    @Inject
    private ScheduleService scheduleService;

    @Inject
    private DragonEconomy dragonEconomy;

    @EventHandler
    public void onTransactionLog(TransactionLogEvent e) {

        var uuid = e.getLog().getTarget().getId();
        var name = Bukkit.getOfflinePlayer(uuid).getName();

        scheduleService.runAsync(dragonEconomy, () -> {

            var user = repository.findById(uuid).orElseGet(() -> {
                var u = new EconomyUser();
                u.setId(uuid);
                u.setBalance(0.0);
                return u;
            });

            if (user.getName() != null && user.getName().equals(name)){
                return;
            }

            user.setName(name);
            repository.save(user);

        }).thenRunSync(v -> dragonEconomy.getLogger().info("username updated for " + name));
    }

}
