package org.dragonitemc.dragoneconomy.command.dragwrld;

import com.ericlam.mc.eld.annotations.CommandArg;
import com.ericlam.mc.eld.annotations.Commander;
import com.ericlam.mc.eld.bukkit.CommandNode;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.dragonitemc.dragoneconomy.api.AsyncEconomyService;
import org.dragonitemc.dragoneconomy.config.DragonEconomyMessage;

import javax.inject.Inject;


@Commander(
        name = "transfer",
        description = "金錢轉移到另一個玩家"
)
public class DragwrldTransferCommand implements CommandNode {

    @Inject
    private AsyncEconomyService economyService;

    @Inject
    private DragonEconomyMessage message;

    @CommandArg(order = 1, labels = "玩家名稱")
    private OfflinePlayer target;

    @CommandArg(order = 2, labels = "金錢")
    private double amount;

    @Override
    public void execute(CommandSender sender) {
        if (!(sender instanceof Player self)) {
            sender.sendMessage(message.getLang().get("no-console"));
            return;
        }

        sender.sendMessage(message.getLang().get("transferring-balance", amount, self.getName(), target.getName()));
        economyService.transfer(self.getUniqueId(), target.getUniqueId(), amount)
                .thenRunSync(result -> sender.sendMessage(message.getResultMessage(result)))
                .joinWithCatch(ex -> sender.sendMessage(message.getErrorMessage(ex)));

    }

}
