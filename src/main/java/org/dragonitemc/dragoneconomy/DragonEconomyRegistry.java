package org.dragonitemc.dragoneconomy;

import com.ericlam.mc.eld.bukkit.CommandNode;
import com.ericlam.mc.eld.bukkit.ComponentsRegistry;
import com.ericlam.mc.eld.registration.CommandRegistry;
import com.ericlam.mc.eld.registration.ListenerRegistry;
import org.bukkit.event.Listener;
import org.dragonitemc.dragoneconomy.command.dragwrld.*;
import org.dragonitemc.dragoneconomy.command.fer.*;
import org.dragonitemc.dragoneconomy.command.wrld.*;
import org.dragonitemc.dragoneconomy.manager.NFTokenManager;

import java.util.List;

public class DragonEconomyRegistry implements ComponentsRegistry {
    @Override
    public void registerCommand(CommandRegistry<CommandNode> registry) {

        registry.command(DragwrldCommand.class, sub -> {

            sub.command(DragwrldBalanceCommand.class);

            sub.command(DragwrldTransferCommand.class);

            sub.command(DragwrldDepositCommand.class);

            sub.command(DragwrldWithdrawCommand.class);

            sub.command(DragwrldSetCommand.class);

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
