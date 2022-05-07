package org.dragonitemc.dragoneconomy.manager;

import org.dragonitemc.dragoneconomy.api.EconomyService;
import org.dragonitemc.dragoneconomy.api.TransactionLogEvent;
import org.dragonitemc.dragoneconomy.api.UpdateResult;
import org.dragonitemc.dragoneconomy.db.EconomyUser;
import org.dragonitemc.dragoneconomy.db.TransactionLog;
import org.dragonitemc.dragoneconomy.repository.EconomyUserRepository;
import org.dragonitemc.dragoneconomy.repository.TransactionLogRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class DragonEconomyManager implements EconomyService {

    @Inject
    private TransactionLogRepository logRepository;

    @Inject
    private EconomyUserRepository economyRepository;

    @Override
    public boolean hasAccount(UUID player) {
        return economyRepository.existsById(player);
    }

    @Override
    public double getBalance(UUID player) {
        return economyRepository.findById(player).map(EconomyUser::getBalance).orElse(0.0);
    }

    @Override
    public UpdateResult withdrawPlayer(UUID player, double value) {
        return withdrawPlayer(player, value, "console");
    }

    @Override
    public UpdateResult withdrawPlayer(UUID player, double value, String operator) {
        var opt = economyRepository.findById(player);
        if (opt.isEmpty()) {
            return UpdateResult.ACCOUNT_NOT_EXIST;
        }
        var user = opt.get();
        var balance = user.getBalance();
        if (balance < value) {
            return UpdateResult.BALANCE_INSUFFICIENT;
        }
        user.setBalance(balance - value);
        economyRepository.save(user);

        TransactionLog log = new TransactionLog();
        log.setOperator(operator);
        log.setTarget(user);
        log.setAmount(value);
        log.setTime(LocalDateTime.now());
        log.setAction(TransactionLog.Action.WITHDRAW);
        logRepository.save(log);
        new TransactionLogEvent(log).callEvent();

        return UpdateResult.SUCCESS;
    }

    @Override
    public UpdateResult depositPlayer(UUID player, double value) {
        return depositPlayer(player, value, "console");
    }


    // deposit will create account if user not exist.
    @Override
    public UpdateResult depositPlayer(UUID player, double value, String operator) {
        var user = economyRepository.findById(player).orElseGet(() -> {
            var eco = new EconomyUser();
            eco.setBalance(0.0);
            eco.setUuid(player);
            return eco;
        });
        var balance = user.getBalance();
        user.setBalance(balance + value);
        economyRepository.save(user);

        TransactionLog log = new TransactionLog();
        log.setOperator(operator);
        log.setTarget(user);
        log.setAmount(value);
        log.setTime(LocalDateTime.now());
        log.setAction(TransactionLog.Action.DEPOSIT);
        logRepository.save(log);
        new TransactionLogEvent(log).callEvent();

        return UpdateResult.SUCCESS;
    }

    @Override
    public UpdateResult setPlayer(UUID player, double value) {
        return setPlayer(player, value, "console");
    }

    // set player will create account if user not exist.
    @Override
    public UpdateResult setPlayer(UUID player, double value, String operator) {
        var user = economyRepository.findById(player).orElseGet(() -> {
            var eco = new EconomyUser();
            eco.setBalance(0.0);
            eco.setUuid(player);
            return eco;
        });
        user.setBalance(value);
        economyRepository.save(user);

        TransactionLog log = new TransactionLog();
        log.setOperator(operator);
        log.setTarget(user);
        log.setAmount(value);
        log.setTime(LocalDateTime.now());
        log.setAction(TransactionLog.Action.SET);
        logRepository.save(log);
        new TransactionLogEvent(log).callEvent();

        return UpdateResult.SUCCESS;
    }


    @Override
    public UpdateResult transfer(UUID from, UUID to, double amount) {
        var fromOpt = economyRepository.findById(from);
        var toOpt = economyRepository.findById(to);
        if (fromOpt.isEmpty() || toOpt.isEmpty()) {
            return UpdateResult.ACCOUNT_NOT_EXIST;
        }
        var fromUser = fromOpt.get();
        var toUser = toOpt.get();
        var fromBalance = fromUser.getBalance();
        var toBalance = toUser.getBalance();
        if (fromBalance < amount) {
            return UpdateResult.BALANCE_INSUFFICIENT;
        }
        fromUser.setBalance(fromBalance - amount);
        toUser.setBalance(toBalance + amount);
        economyRepository.saveAll(List.of(fromUser, toUser));

        TransactionLog log = new TransactionLog();
        log.setOperator("console");
        log.setUser(fromUser);
        log.setTarget(toUser);
        log.setAmount(amount);
        log.setTime(LocalDateTime.now());
        log.setAction(TransactionLog.Action.TRANSFER);
        logRepository.save(log);
        new TransactionLogEvent(log).callEvent();

        return UpdateResult.SUCCESS;
    }

}
