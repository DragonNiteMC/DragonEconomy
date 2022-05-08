package org.dragonitemc.dragoneconomy;

import com.ericlam.mc.eld.services.ScheduleService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
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
    public void onPlayerJoin(PlayerJoinEvent e) {
        scheduleService.runAsync(dragonEconomy, () -> {

            var user = repository.findById(e.getPlayer().getUniqueId()).orElseGet(() -> {
                var u = new EconomyUser();
                u.setId(e.getPlayer().getUniqueId());
                u.setName(e.getPlayer().getName());
                u.setBalance(0.0);
                return u;
            });

            // update the player name
            user.setName(e.getPlayer().getName());

            repository.save(user);

        }).thenRunSync(v -> dragonEconomy.getLogger().info("username updated for " + e.getPlayer().getName()));
    }

}
