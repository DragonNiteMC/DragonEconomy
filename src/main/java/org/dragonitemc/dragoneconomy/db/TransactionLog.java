package org.dragonitemc.dragoneconomy.db;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "DragonEncomy_TransactionLog")
public class TransactionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column
    private String operator;

    @Column
    private LocalDateTime time;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "player_id", referencedColumnName = "uuid")
    private EconomyUser user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "target_id", referencedColumnName = "uuid", nullable = false)
    private EconomyUser target;

    @Column
    private double amount;

    @Column
    private Action action;


    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public EconomyUser getUser() {
        return user;
    }

    public void setUser(EconomyUser user) {
        this.user = user;
    }

    public EconomyUser getTarget() {
        return target;
    }

    public void setTarget(EconomyUser target) {
        this.target = target;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public enum Action {
        DEPOSIT, WITHDRAW, TRANSFER, SET
    }

}