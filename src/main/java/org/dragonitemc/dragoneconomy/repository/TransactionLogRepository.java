package org.dragonitemc.dragoneconomy.repository;

import org.dragonitemc.dragoneconomy.db.TransactionLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionLogRepository extends JpaRepository<TransactionLog, Integer> {
}