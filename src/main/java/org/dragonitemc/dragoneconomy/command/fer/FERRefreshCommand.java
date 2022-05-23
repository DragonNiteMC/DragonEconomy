package org.dragonitemc.dragoneconomy.command.fer;

import com.ericlam.mc.eld.annotations.Commander;
import com.ericlam.mc.eld.bukkit.CommandNode;
import org.bukkit.command.CommandSender;
import org.dragonitemc.dragoneconomy.api.FERService;

import javax.inject.Inject;

@Commander(
        name = "refresh",
        description = "刷新浮動匯率"
)
public class FERRefreshCommand implements CommandNode {

    @Inject
    private FERService ferService;

    @Override
    public void execute(CommandSender commandSender) {
        var success = ferService.refreshExchangeRate();
        commandSender.sendMessage(success ? "刷新成功" : "刷新失敗");
    }
}
