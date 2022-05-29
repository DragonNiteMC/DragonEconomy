package org.dragonitemc.dragoneconomy.command.eco;

import com.ericlam.mc.eld.annotations.Commander;
import com.ericlam.mc.eld.bukkit.CommandNode;
import org.bukkit.command.CommandSender;

@Commander(
        name = "deco",
        description = "DragonEconomy 指令",
        permission = "dragoneconomy.admin"
)
public class DecoCommand implements CommandNode {

    @Override
    public void execute(CommandSender sender) {

    }
}
