package org.dragonitemc.dragoneconomy.command.eco;

import com.ericlam.mc.eld.annotations.Commander;
import com.ericlam.mc.eld.bukkit.CommandNode;
import org.bukkit.command.CommandSender;
import org.dragonitemc.dragoneconomy.config.DragonEconomyMessage;

import javax.inject.Inject;

@Commander(
        name = "deco",
        description = "Reload the plugin",
        permission = "dragoneconomy.admin"
)
public class ReloadCommand implements CommandNode {

    @Inject
    private DragonEconomyMessage message;

    @Override
    public void execute(CommandSender sender) {
        message.getController().reload();
        sender.sendMessage("§aReload success!");
    }
}
