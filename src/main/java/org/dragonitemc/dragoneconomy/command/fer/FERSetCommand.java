package org.dragonitemc.dragoneconomy.command.fer;

import com.ericlam.mc.eld.annotations.CommandArg;
import com.ericlam.mc.eld.annotations.Commander;
import com.ericlam.mc.eld.bukkit.CommandNode;
import org.bukkit.command.CommandSender;
import org.dragonitemc.dragoneconomy.api.FERService;

import javax.inject.Inject;

@Commander(
        name = "set",
        description = "手動設置浮動匯率"
)
public class FERSetCommand implements CommandNode {


    @CommandArg(order = 1, labels = "匯率")
    private float exchangeRate;

    @Inject
    private FERService ferService;

    @Override
    public void execute(CommandSender commandSender) {
        if (exchangeRate > 1f){
            commandSender.sendMessage("匯率不能大於1");
            return;
        }
        if (exchangeRate <= 0f){
            commandSender.sendMessage("匯率不能小於等於0");
            return;
        }
        var success = ferService.setExchangeRate(exchangeRate);
        commandSender.sendMessage(success ? "設置成功" : "設置失敗");
    }
}
