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
        name = "withdraw",
        description = "提取 wrld",
        permission = "dragoneconomy.wrld.admin"
)
public class WrldWithdrawCommand implements CommandNode {
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
        if (!player.isOnline()){
            sender.sendMessage(message.getLang().get("not-online", player.getName()));
            return;
        }
        tokenService.withdrawToken(player.getPlayer(), amount, null, String.format("管理員 %s 的指令轉移", sender.getName()),
                        v -> sender.sendMessage(message.getResultMessage(UpdateResult.SUCCESS)));

    }
}
