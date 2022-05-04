package org.dragonitemc.dragoneconomy.api;

import org.bukkit.OfflinePlayer;


// Guess what ? copy!
public interface EconomyService {

    boolean hasAccount(OfflinePlayer player);

    double getBalance(OfflinePlayer player);

    UpdateResult withdrawPlayer(OfflinePlayer player, double value);

    UpdateResult withdrawPlayer(OfflinePlayer player, double value, String operator);

    UpdateResult depositPlayer(OfflinePlayer player, double value);

    UpdateResult depositPlayer(OfflinePlayer player, double value, String operator);

    UpdateResult setPlayer(OfflinePlayer player, double value);

    UpdateResult setPlayer(OfflinePlayer player, double value, String operator);

    UpdateResult transfer(OfflinePlayer from, OfflinePlayer to, double amount);


}
