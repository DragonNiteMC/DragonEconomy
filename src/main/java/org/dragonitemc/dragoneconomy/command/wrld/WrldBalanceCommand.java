package org.dragonitemc.dragoneconomy.command.wrld;

import com.ericlam.mc.eld.annotations.CommandArg;
import com.ericlam.mc.eld.annotations.Commander;
import com.ericlam.mc.eld.components.CommandNode;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.dragonitemc.dragoneconomy.api.NFTokenService;
import org.dragonitemc.dragoneconomy.config.DragonEconomyMessage;

import javax.inject.Inject;

@Commander(
        name = "balance",
        description = "檢查玩家的 $wrld",
        permission = "dragoneconomy.wrld.balance"
)
public class WrldBalanceCommand implements CommandNode {

    @Inject
    private NFTokenService nfTokenService;

    @Inject
    private DragonEconomyMessage message;


    @CommandArg(order = 1, optional = true)
    private OfflinePlayer player;

    @Override
    public void execute(CommandSender commandSender) {

        if (player == null) {

            if (!(commandSender instanceof Player self)) {
                commandSender.sendMessage(message.getLang().get("no-player-arg-in-console"));
                return;
            } else {
                player = self;
            }

        }

        var bal = nfTokenService.getTokenPrice(player);

        commandSender.sendMessage(message.getBalanceDisplay(player.getName(), "wrld", bal));
    }

}
