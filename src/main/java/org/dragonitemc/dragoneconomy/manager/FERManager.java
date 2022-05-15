package org.dragonitemc.dragoneconomy.manager;

import com.dragonite.mc.dnmc.core.main.DragoniteMC;
import org.dragonitemc.dragoneconomy.api.FERService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;

import java.util.Random;

public class FERManager implements FERService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FERService.class);
    private static final String FER_KEY = "dragon_economy.fer";

    private float exchangeRate = 0.99f;
    private final Random random = new Random();

    public FERManager(){
        try (Jedis jedis = DragoniteMC.getAPI().getRedisDataSource().getJedis()){
            if (jedis.exists(FER_KEY)){
                this.exchangeRate = Float.parseFloat(jedis.get(FER_KEY));
            }else{
                this.refreshExchangeRate();
            }
        }catch (NumberFormatException e){
            LOGGER.warn("Cannot parse exchange rate from redis: " + e.getMessage());
            LOGGER.warn("using 0.99f as default");
        }
    }

    @Override
    public boolean setExchangeRate(float rate) {
        this.validateExchangeRate(rate);
        return this.saveToRedis(rate);
    }

    @Override
    public float getExchangeRate() {
        return exchangeRate;
    }

    @Override
    public boolean refreshExchangeRate() {
        return this.saveToRedis(randomFloat(0.01f, 0.99f));
    }

    private boolean saveToRedis(float exchangeRate){
        try(Jedis jedis = DragoniteMC.getAPI().getRedisDataSource().getJedis()){
            jedis.set(FER_KEY, String.valueOf(exchangeRate));
            this.exchangeRate = exchangeRate;
            return true;
        }catch (JedisException e){
            // if error, dont set the exchange rate
            LOGGER.warn("Cannot set exchange rate to redis: " + e.getMessage(), e);
        }
        return false;
    }

    private float randomFloat(float min, float max){
        return random.nextFloat() * (max - min) + min;
    }

    private void validateExchangeRate(float exchangeRate) {
        if (exchangeRate > 1f){
            throw new IllegalStateException("Exchange rate cannot be greater than 1");
        }
        if (exchangeRate <= 0f){
            throw new IllegalStateException("Exchange rate cannot be less than or equal to 0");
        }
    }
}
