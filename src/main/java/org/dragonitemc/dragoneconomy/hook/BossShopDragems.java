package org.dragonitemc.dragoneconomy.hook;

import org.black_ixx.bossshop.pointsystem.BSPointsPlugin;
import org.bukkit.OfflinePlayer;
import org.dragonitemc.dragoneconomy.api.EconomyService;

import javax.inject.Inject;

public final class BossShopDragems extends BSPointsPlugin {

    @Inject
    private EconomyService economyService;

    public BossShopDragems() {
        super("gems");
    }

    @Override
    public double getPoints(OfflinePlayer offlinePlayer) {
        return economyService.getBalance(offlinePlayer.getUniqueId());
    }

    @Override
    public double setPoints(OfflinePlayer offlinePlayer, double v) {
        economyService.setPlayer(offlinePlayer.getUniqueId(), v);
        return getPoints(offlinePlayer);
    }

    @Override
    public double takePoints(OfflinePlayer offlinePlayer, double v) {
        economyService.withdrawPlayer(offlinePlayer.getUniqueId(), v);
        return getPoints(offlinePlayer);
    }

    @Override
    public double givePoints(OfflinePlayer offlinePlayer, double v) {
        economyService.depositPlayer(offlinePlayer.getUniqueId(), v);
        return getPoints(offlinePlayer);
    }

    @Override
    public boolean usesDoubleValues() {
        return true;
    }
}
