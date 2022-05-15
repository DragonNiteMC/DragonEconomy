package org.dragonitemc.dragoneconomy;

import com.ericlam.mc.eld.registrations.CommandRegistry;
import com.ericlam.mc.eld.registrations.ComponentsRegistry;
import com.ericlam.mc.eld.registrations.ListenerRegistry;
import org.dragonitemc.dragoneconomy.command.dragems.*;
import org.dragonitemc.dragoneconomy.command.fer.*;
import org.dragonitemc.dragoneconomy.command.wrld.*;
import org.dragonitemc.dragoneconomy.manager.NFTokenManager;

import java.util.List;

public class DragonEconomyRegistry implements ComponentsRegistry {
    @Override
    public void registerCommand(CommandRegistry registry) {

        registry.command(DragemsCommand.class, sub -> {

            sub.command(DragemsBalanceCommand.class);

            sub.command(DragemsTransferCommand.class);

            sub.command(DragemsDepositCommand.class);

            sub.command(DragemsWithdrawCommand.class);

            sub.command(DragemsSetCommand.class);

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
    public void registerListeners(ListenerRegistry registry) {
        registry.listeners(List.of(NFTokenManager.class));
        registry.listeners(List.of(DragonEconomyListener.class));
    }
}
