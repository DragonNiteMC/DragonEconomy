package org.dragonitemc.dragoneconomy.repository;

import org.dragonitemc.dragoneconomy.db.EconomyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EconomyUserRepository extends JpaRepository<EconomyUser, UUID> {
    
}
