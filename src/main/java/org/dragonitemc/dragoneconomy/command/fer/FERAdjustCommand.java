package org.dragonitemc.dragoneconomy.command.fer;

import com.ericlam.mc.eld.annotations.CommandArg;
import com.ericlam.mc.eld.annotations.Commander;
import com.ericlam.mc.eld.components.CommandNode;
import org.bukkit.command.CommandSender;
import org.dragonitemc.dragoneconomy.api.FERService;

import javax.inject.Inject;
import java.util.Locale;

@Commander(
        name = "adjust",
        description = "調整隨機浮動的參數",
        permission = "dragoneconomy.fer.adjust"
)
public class FERAdjustCommand implements CommandNode {


    @CommandArg(order = 1)
    private String adjustType;

    @CommandArg(order = 2)
    private float adjustValue;


    @Inject
    private FERService ferService;

    @Override
    public void execute(CommandSender commandSender) {
        boolean success = false;
        switch (adjustType.toLowerCase(Locale.ROOT)) {
            case "min" -> success = ferService.setMinLimit(adjustValue);
            case "max" -> success = ferService.setMaxLimit(adjustValue);
            case "control" -> success = ferService.setControl(adjustValue);
            default -> {
                commandSender.sendMessage("unknown adjustType, available: min, max, control");
                return;
            }
        }

        commandSender.sendMessage(success ? "&a設置成功" : "&c設置失敗");
    }
}
