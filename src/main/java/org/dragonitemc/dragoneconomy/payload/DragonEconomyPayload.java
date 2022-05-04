package org.dragonitemc.dragoneconomy.payload;

import java.util.UUID;

public class DragonEconomyPayload<T> {

    private final UUID id;
    private final T payload;

    public DragonEconomyPayload(T payload) {
        this.id = UUID.randomUUID();
        this.payload = payload;
    }

    public UUID getId() {
        return id;
    }

    public T getPayload() {
        return payload;
    }
}
