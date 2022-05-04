package org.dragonitemc.dragoneconomy;

import chu77.eldependenci.sql.SQLInstallation;
import com.ericlam.mc.eld.ELDBukkitPlugin;
import com.ericlam.mc.eld.ManagerProvider;
import com.ericlam.mc.eld.ServiceCollection;
import com.nftworlds.wallet.api.WalletAPI;
import org.dragonitemc.dragoneconomy.api.AsyncEconomyService;
import org.dragonitemc.dragoneconomy.api.EconomyService;
import org.dragonitemc.dragoneconomy.api.NFTokenService;
import org.dragonitemc.dragoneconomy.db.EconomyUser;
import org.dragonitemc.dragoneconomy.db.TransactionLog;
import org.dragonitemc.dragoneconomy.manager.AsyncEconomyManager;
import org.dragonitemc.dragoneconomy.manager.DragonEconomyManager;
import org.dragonitemc.dragoneconomy.manager.NFTokenManager;
import org.dragonitemc.dragoneconomy.repository.EconomyUserRepository;
import org.dragonitemc.dragoneconomy.repository.TransactionLogRepository;

public class DragonEconomy extends ELDBukkitPlugin {

    @Override
    protected void bindServices(ServiceCollection collection) {
        collection.bindService(EconomyService.class, DragonEconomyManager.class);
        collection.bindService(AsyncEconomyService.class, AsyncEconomyManager.class);
        collection.bindService(NFTokenService.class, NFTokenManager.class);
        collection.addSingleton(WalletAPI.class);

        SQLInstallation sqlInstallation = collection.getInstallation(SQLInstallation.class);
        sqlInstallation.bindEntities(EconomyUser.class, TransactionLog.class);
        sqlInstallation.bindJpaRepository(EconomyUserRepository.class, TransactionLogRepository.class);
    }


    @Override
    protected void manageProvider(ManagerProvider provider) {

    }
}
