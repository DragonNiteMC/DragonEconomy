package org.dragonitemc.dragoneconomy.manager;

import org.bukkit.OfflinePlayer;
import org.dragonitemc.dragoneconomy.api.EconomyService;
import org.dragonitemc.dragoneconomy.api.UpdateResult;
import org.dragonitemc.dragoneconomy.db.EconomyUser;
import org.dragonitemc.dragoneconomy.db.TransactionLog;
import org.dragonitemc.dragoneconomy.repository.EconomyUserRepository;
import org.dragonitemc.dragoneconomy.repository.TransactionLogRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

public class DragonEconomyManager implements EconomyService {

    @Inject
    private TransactionLogRepository logRepository;

    @Inject
    private EconomyUserRepository economyRepository;

    @Override
    public boolean hasAccount(OfflinePlayer player) {
        return economyRepository.existsById(player.getUniqueId());
    }

    @Override
    public double getBalance(OfflinePlayer player) {
        return economyRepository.findById(player.getUniqueId()).map(EconomyUser::getBalance).orElse(0.0);
    }

    @Override
    public UpdateResult withdrawPlayer(OfflinePlayer player, double value) {
        return withdrawPlayer(player, value, "console");
    }

    @Transactional
    @Override
    public UpdateResult withdrawPlayer(OfflinePlayer player, double value, String operator) {
        var opt = economyRepository.findById(player.getUniqueId());
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
        log.setAction(TransactionLog.Action.WITHDRAW);
        logRepository.save(log);

        return UpdateResult.SUCCESS;
    }

    @Override
    public UpdateResult depositPlayer(OfflinePlayer player, double value) {
        return depositPlayer(player, value, "console");
    }


    @Transactional
    @Override
    public UpdateResult depositPlayer(OfflinePlayer player, double value, String operator) {
        var opt = economyRepository.findById(player.getUniqueId());
        if (opt.isEmpty()) {
            return UpdateResult.ACCOUNT_NOT_EXIST;
        }
        var user = opt.get();
        var balance = user.getBalance();
        user.setBalance(balance + value);
        economyRepository.save(user);

        TransactionLog log = new TransactionLog();
        log.setOperator(operator);
        log.setTarget(user);
        log.setAmount(value);
        log.setAction(TransactionLog.Action.DEPOSIT);
        logRepository.save(log);

        return UpdateResult.SUCCESS;
    }

    @Override
    public UpdateResult setPlayer(OfflinePlayer player, double value) {
        return setPlayer(player, value, "console");
    }

    @Transactional
    @Override
    public UpdateResult setPlayer(OfflinePlayer player, double value, String operator) {
        var opt = economyRepository.findById(player.getUniqueId());
        if (opt.isEmpty()) {
            return UpdateResult.ACCOUNT_NOT_EXIST;
        }
        var user = opt.get();
        user.setBalance(value);
        economyRepository.save(user);

        TransactionLog log = new TransactionLog();
        log.setOperator(operator);
        log.setTarget(user);
        log.setAmount(value);
        log.setAction(TransactionLog.Action.SET);
        logRepository.save(log);

        return UpdateResult.SUCCESS;
    }


    @Transactional
    @Override
    public UpdateResult transfer(OfflinePlayer from, OfflinePlayer to, double amount) {
        var fromOpt = economyRepository.findById(from.getUniqueId());
        var toOpt = economyRepository.findById(to.getUniqueId());
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
        log.setAction(TransactionLog.Action.TRANSFER);
        logRepository.save(log);

        return UpdateResult.SUCCESS;
    }

}
