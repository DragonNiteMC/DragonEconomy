package org.dragonitemc.dragoneconomy.command;

import com.ericlam.mc.eld.annotations.CommandArg;
import com.ericlam.mc.eld.annotations.Commander;
import com.ericlam.mc.eld.components.CommandNode;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.dragonitemc.dragoneconomy.api.AsyncEconomyService;
import org.dragonitemc.dragoneconomy.config.DragonEconomyMessage;

import javax.inject.Inject;

@Commander(
        name = "withdraw",
        description = "向玩家提取金錢",
        permission = "dragoneconomy.admin"
)
public class DragemsWithdrawCommand implements CommandNode {

    @Inject
    private DragonEconomyMessage message;

    @Inject
    private AsyncEconomyService economyService;

    @CommandArg(order = 1, labels = "玩家")
    private OfflinePlayer player;

    @CommandArg(order = 2, labels = "金錢")
    private double amount;

    @Override
    public void execute(CommandSender sender) {
        economyService.withdrawPlayer(player, amount, sender.getName())
                .thenRunSync(result -> sender.sendMessage(message.getResultMessage(result)))
                .joinWithCatch(ex -> sender.sendMessage(message.getErrorMessage(ex)));
    }
}
