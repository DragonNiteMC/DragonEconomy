package org.dragonitemc.dragoneconomy.command.wrld;

import com.ericlam.mc.eld.annotations.CommandArg;
import com.ericlam.mc.eld.annotations.Commander;
import com.ericlam.mc.eld.components.CommandNode;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.dragonitemc.dragoneconomy.api.NFTokenService;
import org.dragonitemc.dragoneconomy.api.UpdateResult;
import org.dragonitemc.dragoneconomy.config.DragonEconomyMessage;

import javax.inject.Inject;

@Commander(
        name = "transfer",
        description = "轉移 wrld 到其他玩家"
)
public class WrldTransferCommand implements CommandNode {

    @Inject
    private NFTokenService tokenService;

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

        if (!target.isOnline()){
            sender.sendMessage(message.getLang().get("not-online", target.getName()));
            return;
        }

        sender.sendMessage(message.getLang().get("transferring-balance", amount, self.getName(), target.getName()));
        tokenService.transferToken(self, target.getPlayer(), amount, String.format("%s 由指令發起的轉移", self.getName()), r -> {
            sender.sendMessage(message.getResultMessage(UpdateResult.SUCCESS));
        });

    }
}
