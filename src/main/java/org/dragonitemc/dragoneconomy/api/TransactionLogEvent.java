package org.dragonitemc.dragoneconomy.api;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.dragonitemc.dragoneconomy.db.TransactionLog;
import org.jetbrains.annotations.NotNull;

public class TransactionLogEvent extends Event {

    private final static HandlerList handlers = new HandlerList();


    private final TransactionLog log;

    public TransactionLogEvent(TransactionLog log) {
        super(true);
        this.log = log;
    }


    public TransactionLog getLog() {
        return log;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

}
