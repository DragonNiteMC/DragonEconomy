package org.dragonitemc.dragoneconomy;

import com.ericlam.mc.eld.misc.DebugLogger;
import com.ericlam.mc.eld.services.LoggingService;
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

    private final DebugLogger logger;


    @Inject
    public DragonEconomyListener(LoggingService loggingService){
        this.logger = loggingService.getLogger(DragonEconomyListener.class);
    }

    @EventHandler
    public void onTransactionLog(TransactionLogEvent e) {

        var log = e.getLog();
        var uuid = log.getTarget().getId();
        var name = Bukkit.getOfflinePlayer(uuid).getName();

        logger.infoF("[Log] %s %s %.2f gems to %s at %s",
                log.getUser() == null ? log.getOperator() : log.getUser().getName(),
                log.getAction().toString().toLowerCase(),
                log.getAmount(),
                log.getTarget().getName(),
                log.getTime().toString()
        );

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
            dragonEconomy.getLogger().info("username updated for " + name);

        }).joinWithCatch(ex -> {
            dragonEconomy.getLogger().warning(ex.getMessage());
            ex.printStackTrace();
        });
    }

}
