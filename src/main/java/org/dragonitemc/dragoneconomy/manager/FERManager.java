package org.dragonitemc.dragoneconomy.manager;

import com.dragonite.mc.dnmc.core.main.DragoniteMC;
import com.dragonite.mc.dnmc.core.managers.RedisDataSource;
import org.dragonitemc.dragoneconomy.api.FERService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;

import java.util.Random;

public class FERManager implements FERService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FERService.class);
    private static final String FER_KEY = "dragon_economy.fer";
    private static final String MIN_FER_KEY = "dragon_economy.fer.min";
    private static final String MAX_FER_KEY = "dragon_economy.fer.max";
    private static final String CONTROL_FER_KEY = "dragon_economy.fer.control";

    private static final Random RANDOM = new Random();
    private static final float DEFAULT_MIN = 0.0001f;
    private static final float DEFAULT_MAX = 0.9999f;

    private static final float DEFAULT_FER = 0.001f;

    private static final float DEFAULT_CONTROL = 0.1f;
    private final RedisDataSource redisDataSource;

    public FERManager(){
        this.redisDataSource = DragoniteMC.getAPI().getRedisDataSource();
    }

    @Override
    public boolean setExchangeRate(float rate) {
        this.validateExchangeRate(rate);
        return this.saveToRedis(rate);
    }

    @Override
    public boolean setMinLimit(float min) {
        if (min < 0f){
            LOGGER.warn("Cannot set min to {}, ignored", min);
            return false;
        }
        try (Jedis jedis = redisDataSource.getJedis()){
            jedis.set(MIN_FER_KEY, String.valueOf(min));
            return true;
        }catch (JedisException e){
            LOGGER.warn("Cannot set min to redis: " + e.getMessage(), e);
        }
        return false;
    }

    @Override
    public boolean setMaxLimit(float max) {
        if (max > 1f){
            LOGGER.warn("Cannot set max to {}, ignored", max);
            return false;
        }
        try (Jedis jedis = redisDataSource.getJedis()){
            jedis.set(MAX_FER_KEY, String.valueOf(max));
            return true;
        }catch (JedisException e){
            LOGGER.warn("Cannot set max to redis: " + e.getMessage(), e);
        }
        return false;
    }

    @Override
    public boolean setControl(float control) {
        if (control > 1f || control < 0f){
            LOGGER.warn("Cannot set control to {}, ignored", control);
            return false;
        }
        try (Jedis jedis = redisDataSource.getJedis()){
            jedis.set(CONTROL_FER_KEY, String.valueOf(control));
            return true;
        }catch (JedisException e){
            LOGGER.warn("Cannot set control to redis: " + e.getMessage(), e);
        }
        return false;
    }

    @Override
    public float getExchangeRate() {
        try (Jedis jedis = redisDataSource.getJedis()){
            if (jedis.exists(FER_KEY)){
                return Float.parseFloat(jedis.get(FER_KEY));
            }else{
                LOGGER.warn("cannot find exchange rate in redis");
                jedis.set(FER_KEY, String.valueOf(DEFAULT_FER));
            }
        }catch (NumberFormatException e){
            LOGGER.warn("Cannot parse exchange rate from redis: " + e.getMessage());
        }catch (JedisException e){
            LOGGER.warn("redis error: " + e.getMessage(), e);
        }
        LOGGER.warn("using default value");
        return DEFAULT_FER;
    }

    @Override
    public boolean refreshExchangeRate() {
        try (Jedis jedis = redisDataSource.getJedis()){
            float min, max, control, currentRate, newRate;

            try {
                if (jedis.exists(MIN_FER_KEY)){
                    min = Float.parseFloat(jedis.get(MIN_FER_KEY));
                } else {
                    LOGGER.warn("Cannot find min in redis, using default");
                    min = DEFAULT_MIN;
                    jedis.set(MIN_FER_KEY, String.valueOf(min));
                }
            }catch (NumberFormatException e){
                LOGGER.warn("Cannot parse min from redis: " + e.getMessage());
                min = DEFAULT_MIN;
            }

            try {
                if (jedis.exists(MAX_FER_KEY)){
                    max = Float.parseFloat(jedis.get(MAX_FER_KEY));
                } else {
                    LOGGER.warn("Cannot find max in redis, using default");
                    max = DEFAULT_MAX;
                    jedis.set(MAX_FER_KEY, String.valueOf(max));
                }
            }catch (NumberFormatException e){
                LOGGER.warn("Cannot parse max from redis: " + e.getMessage());
                max = DEFAULT_MAX;
            }

            try {
                if (jedis.exists(CONTROL_FER_KEY)){
                    control = Float.parseFloat(jedis.get(CONTROL_FER_KEY));
                }else{
                    LOGGER.warn("Cannot find control in redis, using default");
                    control = DEFAULT_CONTROL;
                    jedis.set(CONTROL_FER_KEY, String.valueOf(control));
                }
            }catch (NumberFormatException e){
                LOGGER.warn("Cannot parse control from redis: " + e.getMessage());
                control = DEFAULT_CONTROL;
            }

            if (jedis.exists(FER_KEY)){
                currentRate = Float.parseFloat(jedis.get(FER_KEY));
            } else {
                LOGGER.warn("Cannot find current rate in redis, using 0.001f and return");
                currentRate = 0.001f;
                jedis.set(FER_KEY, String.valueOf(currentRate));
                return true;
            }

            newRate = randomFloat(min, max);

            if (RANDOM.nextBoolean()){
                newRate = currentRate * (1 + (newRate * control));
            } else {
                newRate = currentRate * (1 - (newRate * control));
            }

            newRate = Math.min(max, Math.max(min, newRate));

            jedis.set(FER_KEY, String.valueOf(newRate));
            LOGGER.info("Successfully updated exchange rate to " + newRate);
            return true;
        }catch (JedisException e){
            LOGGER.warn("redis error: " + e.getMessage(), e);
        }
        return false;
    }

    private boolean saveToRedis(float exchangeRate){
        try(Jedis jedis = DragoniteMC.getAPI().getRedisDataSource().getJedis()){
            jedis.set(FER_KEY, String.valueOf(exchangeRate));
            return true;
        }catch (JedisException e){
            LOGGER.warn("Cannot set exchange rate to redis: " + e.getMessage(), e);
        }
        return false;
    }

    private float randomFloat(float min, float max){
        return RANDOM.nextFloat() * (max - min) + min;
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
