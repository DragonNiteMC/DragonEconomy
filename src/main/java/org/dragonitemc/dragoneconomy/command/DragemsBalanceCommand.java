package org.dragonitemc.dragoneconomy.command;

import com.ericlam.mc.eld.annotations.CommandArg;
import com.ericlam.mc.eld.annotations.Commander;
import com.ericlam.mc.eld.components.CommandNode;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.dragonitemc.dragoneconomy.api.AsyncEconomyService;
import org.dragonitemc.dragoneconomy.api.EconomyService;
import org.dragonitemc.dragoneconomy.config.DragonEconomyMessage;

import javax.inject.Inject;
import java.util.List;

@Commander(
        name = "balance",
        description = "查詢餘額"
)
public class DragemsBalanceCommand implements CommandNode {

    @Inject
    private DragonEconomyMessage message;

    @Inject
    private AsyncEconomyService economyService;

    @CommandArg(order = 1, optional = true, labels = "玩家名稱")
    private OfflinePlayer player;

    @Override
    public void execute(CommandSender sender) {
        if (player == null) {
            if (sender instanceof ConsoleCommandSender) {
                sender.sendMessage(message.getLang().get("no-player-arg-in-console"));
                return;
            }
            player = (OfflinePlayer) sender;
        }


        sender.sendMessage(message.getLang().get("fetching-balance", player.getName()));
        economyService.getBalance(player.getUniqueId())
                .thenRunSync(balance -> sender.sendMessage(message.getLang().get("balance", List.of(player.getName(), balance).toArray())))
                .joinWithCatch(ex -> sender.sendMessage(message.getErrorMessage(ex)));;

    }
}
