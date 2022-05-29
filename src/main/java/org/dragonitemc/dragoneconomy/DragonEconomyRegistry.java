package org.dragonitemc.dragoneconomy;

import com.ericlam.mc.eld.bukkit.CommandNode;
import com.ericlam.mc.eld.bukkit.ComponentsRegistry;
import com.ericlam.mc.eld.registration.CommandRegistry;
import com.ericlam.mc.eld.registration.ListenerRegistry;
import org.bukkit.event.Listener;
import org.dragonitemc.dragoneconomy.command.eco.DecoCommand;
import org.dragonitemc.dragoneconomy.command.eco.ReloadCommand;
import org.dragonitemc.dragoneconomy.command.localwrld.*;
import org.dragonitemc.dragoneconomy.command.fer.*;
import org.dragonitemc.dragoneconomy.command.wrld.*;
import org.dragonitemc.dragoneconomy.manager.NFTokenManager;

import java.util.List;

public class DragonEconomyRegistry implements ComponentsRegistry {
    @Override
    public void registerCommand(CommandRegistry<CommandNode> registry) {

        registry.command(DecoCommand.class, sub -> {

            sub.command(ReloadCommand.class);

        });

        registry.command(LocalWrldCommand.class, sub -> {

            sub.command(LocalWrldBalanceCommand.class);

            sub.command(LocalWrldTransferCommand.class);

            sub.command(LocalWrldDepositCommand.class);

            sub.command(LocalWrldWithdrawCommand.class);

            sub.command(LocalWrldSetCommand.class);

        });

        registry.command(WrldCommand.class, sub -> {

            sub.command(WrldDepositCommand.class);

            sub.command(WrldWithdrawCommand.class);

            sub.command(WrldTransferCommand.class);

            sub.command(WrldBalanceCommand.class);

        });


        registry.command(FERCommand.class, sub ->{

            sub.command(FERCheckCommand.class);

            sub.command(FERRefreshCommand.class);

            sub.command(FERSetCommand.class);

            sub.command(FERAdjustCommand.class);

        });

    }

    @Override
    public void registerListeners(ListenerRegistry<Listener> registry) {
        registry.listeners(List.of(NFTokenManager.class));
        registry.listeners(List.of(DragonEconomyListener.class));
    }
}
