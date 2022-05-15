package org.dragonitemc.dragoneconomy.api;

// Floating Exchange Rates
public interface FERService {

    boolean setExchangeRate(float rate);

    float getExchangeRate();

    boolean refreshExchangeRate();

}
