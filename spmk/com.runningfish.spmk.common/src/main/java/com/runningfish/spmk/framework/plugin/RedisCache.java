package com.runningfish.spmk.framework.plugin;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedisCache implements ICache {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void setCache(String key, Object object) {
        try {
            logger.debug("set the object to redis cache by key :" + key);
            //CacheHandler.getCacheHanlder().setCache(key, object);
            RedisHandler.getRedisHandler().setObject(key, object);
        } catch (Exception ex) {
            logger.error("set the redis cache error", ex);
        }
    }

    @Override
    public void setCache(String key, Object value, int time) {
        try {
            logger.debug("set the object to redis cache by key :" + key);
            //CacheHandler.getCacheHanlder().setCache(key, value, time);
            RedisHandler.getRedisHandler().setObject(key, value, time);
        } catch (Exception ex) {
            logger.error("set the redis cache error", ex);
        }
    }

    @Override
    public Object getCache(String key) {
        Object object = null;
        try {
            logger.debug("get the object to redis cache by key :" + key);
            //object = CacheHandler.getCacheHanlder().getCache(key);
            object = RedisHandler.getRedisHandler().getObject(key);
        } catch (Exception ex) {
            logger.error("get the redis cache error", ex);
        }
        return object;
    }

    @Override
    public void delCache(String key) {
        try {
            logger.debug("delete the object to redis cache by key :" + key);
            //CacheHandler.getCacheHanlder().delCache(key);
            RedisHandler.getRedisHandler().delObject(key);
        } catch (Exception ex) {
            logger.error("delete the redis cache error", ex);
        }
    }

    @Override
    public void delCache(Set<String> keys) {
        try {
            RedisHandler.getRedisHandler().delObject(keys);
        } catch (Exception ex) {
            logger.error("delete the redis cache error", ex);
        }

    }

    @Override
    public void flushDB() {
        try {
            logger.debug("flush redis cache");
            //CacheHandler.getCacheHanlder().flushDB();
            RedisHandler.getRedisHandler().flushDB();
        } catch (Exception ex) {
            logger.error("flush redis cache error", ex);
        }
    }

    @Override
    public Long dbSize() {
        try {
            logger.debug("redis cache dbsize");
            //Long dbsize = CacheHandler.getCacheHanlder().dbSize();
            Long dbsize = RedisHandler.getRedisHandler().dbSize();
            return dbsize;
        } catch (Exception ex) {
            logger.error("redis cache dbsize error", ex);
        }
        return 0L;
    }

    @Override
    public Set<String> getKeys(String key) {
        try {
            logger.debug("redis cache keys collection");
            //Set<String> keys = CacheHandler.getCacheHanlder().getKeys(key);
            Set<String> keys = RedisHandler.getRedisHandler().getKeys(key);
            return keys;
        } catch (Exception ex) {
            logger.error("redis cache keys collection error", ex);
        }
        return null;
    }

    @Override
    public String getKey(Object... strings) {
        if (strings == null || strings.length == 0) {
            return null;
        }
        String keys = "";
        for (int i = 0; i < strings.length; i++) {
            keys += strings[i];
        }
        return keys;
    }
}

