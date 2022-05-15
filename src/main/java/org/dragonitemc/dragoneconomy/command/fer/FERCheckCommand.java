package org.dragonitemc.dragoneconomy.command.fer;

import com.ericlam.mc.eld.annotations.Commander;
import com.ericlam.mc.eld.components.CommandNode;
import org.bukkit.command.CommandSender;
import org.dragonitemc.dragoneconomy.api.FERService;

import javax.inject.Inject;

@Commander(
        name = "check",
        description = "查詢浮動匯率"
)
public class FERCheckCommand implements CommandNode {

    @Inject
    private FERService ferService;


    @Override
    public void execute(CommandSender commandSender) {
        commandSender.sendMessage("目前的浮動匯率是: " + ferService.getExchangeRate() + " Gems : 1 WRLD");
    }
}
