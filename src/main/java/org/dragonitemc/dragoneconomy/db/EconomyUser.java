package org.dragonitemc.dragoneconomy.db;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Table(name = "DragonEconomy_Users")
@Entity
public class EconomyUser {

    @Id
    @Column(columnDefinition = "varchar(40)")
    private UUID id;

    @Column
    private Double balance;


    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID uuid) {
        this.id = uuid;
    }

}
