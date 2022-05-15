package org.dragonitemc.dragoneconomy;

import chu77.eldependenci.sql.SQLInstallation;
import com.ericlam.mc.eld.ELDBukkitPlugin;
import com.ericlam.mc.eld.ManagerProvider;
import com.ericlam.mc.eld.ServiceCollection;
import com.ericlam.mc.eld.annotations.ELDPlugin;
import com.nftworlds.wallet.api.WalletAPI;
import org.dragonitemc.dragoneconomy.api.AsyncEconomyService;
import org.dragonitemc.dragoneconomy.api.EconomyService;
import org.dragonitemc.dragoneconomy.api.FERService;
import org.dragonitemc.dragoneconomy.api.NFTokenService;
import org.dragonitemc.dragoneconomy.config.DragonEconomyMessage;
import org.dragonitemc.dragoneconomy.db.EconomyUser;
import org.dragonitemc.dragoneconomy.db.TransactionLog;
import org.dragonitemc.dragoneconomy.hook.DragonShopHook;
import org.dragonitemc.dragoneconomy.hook.DragonEconomyPlaceholder;
import org.dragonitemc.dragoneconomy.hook.PlaceholderHook;
import org.dragonitemc.dragoneconomy.manager.AsyncEconomyManager;
import org.dragonitemc.dragoneconomy.manager.DragonEconomyManager;
import org.dragonitemc.dragoneconomy.manager.FERManager;
import org.dragonitemc.dragoneconomy.manager.NFTokenManager;
import org.dragonitemc.dragoneconomy.repository.EconomyUserRepository;
import org.dragonitemc.dragoneconomy.repository.TransactionLogRepository;

@ELDPlugin(
        lifeCycle = DragonEconomyLifeCycle.class,
        registry = DragonEconomyRegistry.class
)
public class DragonEconomy extends ELDBukkitPlugin {

    @Override
    protected void bindServices(ServiceCollection collection) {

        collection.addConfiguration(DragonEconomyMessage.class);

        collection.bindService(EconomyService.class, DragonEconomyManager.class);
        collection.bindService(AsyncEconomyService.class, AsyncEconomyManager.class);
        collection.bindService(NFTokenService.class, NFTokenManager.class);
        collection.bindService(FERService.class, FERManager.class);

        collection.addSingleton(WalletAPI.class);

        collection.addSingleton(PlaceholderHook.class);
        collection.addSingleton(DragonShopHook.class);

        SQLInstallation sqlInstallation = collection.getInstallation(SQLInstallation.class);
        sqlInstallation.bindEntities(EconomyUser.class, TransactionLog.class);
        sqlInstallation.bindJpaRepository(EconomyUserRepository.class);
        sqlInstallation.bindJpaRepository(TransactionLogRepository.class);

    }


    @Override
    protected void manageProvider(ManagerProvider provider) {

    }
}
