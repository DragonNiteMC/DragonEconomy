package org.dragonitemc.dragoneconomy.command.fer;

import com.ericlam.mc.eld.annotations.Commander;
import com.ericlam.mc.eld.components.CommandNode;
import org.bukkit.command.CommandSender;

@Commander(
        name = "fer",
        description = "浮動匯率指令",
        permission = "dragoneconomy.fer"
)
public class FERCommand implements CommandNode {
    @Override
    public void execute(CommandSender commandSender) {

    }
}
