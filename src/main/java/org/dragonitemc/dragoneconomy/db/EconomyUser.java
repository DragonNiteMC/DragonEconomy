package org.dragonitemc.dragoneconomy.db;


import org.hibernate.annotations.GeneratorType;
import org.hibernate.annotations.Type;

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
    @Type(type = "uuid-char")
    private UUID id;



    @Column
    private Double balance;

    @Column(name = "name", length = 70)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
