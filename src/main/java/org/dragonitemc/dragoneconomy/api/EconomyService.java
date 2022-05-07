package org.dragonitemc.dragoneconomy.api;

import java.util.UUID;


// Guess what ? copy!
public interface EconomyService {

    boolean hasAccount(UUID player);

    double getBalance(UUID player);

    UpdateResult withdrawPlayer(UUID player, double value);

    UpdateResult withdrawPlayer(UUID player, double value, String operator);

    UpdateResult depositPlayer(UUID player, double value);

    UpdateResult depositPlayer(UUID player, double value, String operator);

    UpdateResult setPlayer(UUID player, double value);

    UpdateResult setPlayer(UUID player, double value, String operator);

    UpdateResult transfer(UUID from, UUID to, double amount);


}
