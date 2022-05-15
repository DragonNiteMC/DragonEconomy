package org.dragonitemc.dragoneconomy.api;

// Floating Exchange Rates
public interface FERService {

    boolean setExchangeRate(float rate);

    boolean setMinLimit(float min);
    boolean setMaxLimit(float max);

    boolean setControl(float control);

    float getExchangeRate();

    boolean refreshExchangeRate();

}
