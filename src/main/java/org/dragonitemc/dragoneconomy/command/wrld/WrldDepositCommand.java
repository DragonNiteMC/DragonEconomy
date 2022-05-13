package org.dragonitemc.dragoneconomy.command.wrld;

import com.ericlam.mc.eld.annotations.CommandArg;
import com.ericlam.mc.eld.annotations.Commander;
import com.ericlam.mc.eld.components.CommandNode;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.dragonitemc.dragoneconomy.api.NFTokenService;
import org.dragonitemc.dragoneconomy.api.UpdateResult;
import org.dragonitemc.dragoneconomy.config.DragonEconomyMessage;

import javax.inject.Inject;

@Commander(
        name = "deposit",
        description = "轉移金錢",
        permission = "dragoneconomy.wrld.admin"
)
public class WrldDepositCommand implements CommandNode {

    @Inject
    private NFTokenService tokenService;

    @Inject
    private DragonEconomyMessage message;


    @CommandArg(order = 1, labels = "玩家")
    private OfflinePlayer player;

    @CommandArg(order = 2, labels = "金錢")
    private double amount;

    @Override
    public void execute(CommandSender sender) {
        try {
            tokenService.depositToken(player, amount, String.format("管理員 %s 的指令轉移", sender.getName()));
            sender.sendMessage(message.getResultMessage(UpdateResult.SUCCESS));
        } catch (Exception ex) {
            sender.sendMessage(message.getErrorMessage(ex));
        }

    }
}
