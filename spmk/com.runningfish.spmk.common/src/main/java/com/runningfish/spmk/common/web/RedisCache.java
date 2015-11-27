package com.runningfish.spmk.common.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.runningfish.spmk.framework.plugin.ICache;

public class RedisCache<K, V> implements Cache<K, V> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String SHIRO_REDIS_CACHE = "shiro_redis_cache:";
    private ICache cache;

    public RedisCache(ICache cache) {
        if (cache == null) {
            throw new IllegalArgumentException("Cache argument cannot be null.");
        }
        this.cache = cache;
    }

    /**
     * 获得byte[]型的key
     *
     * @param key
     * @return
     */
    private String getByteKey(K key) {
        String preKey = this.SHIRO_REDIS_CACHE + key;
        return preKey;
    }

    @Override
    public V get(K key) throws CacheException {
        logger.debug("根据key从Redis中获取对象 key [" + key + "]");
        try {
            if (key == null) {
                return null;
            } else {
                @SuppressWarnings("unchecked")
                //V value = (V) CacheHandler.getCacheHanlder().getCache(getByteKey(key));
                V value = (V) cache.getCache(cache.getKey(key));
                return value;
            }
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    @Override
    public V put(K key, V value) throws CacheException {
        logger.debug("根据key从存储 key [" + key + "]");
        try {
            //CacheHandler.getCacheHanlder().setCache(getByteKey(key), value);
            cache.setCache(cache.getKey(key), value);
            return value;
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    @Override
    public V remove(K key) throws CacheException {
        logger.debug("从redis中删除 key [" + key + "]");
        try {
            V previous = get(key);
            //CacheHandler.getCacheHanlder().delCache(getByteKey(key));
            cache.delCache(cache.getKey(key));
            return previous;
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    @Override
    public void clear() throws CacheException {
        logger.debug("从redis中删除所有元素");
        try {
            //CacheHandler.getCacheHanlder().flushDB();
            cache.flushDB();
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    @Override
    public int size() {
        int dbSize = 0;
        try {
            //dbSize =  CacheHandler.getCacheHanlder().dbSize().intValue();
            dbSize = cache.dbSize().intValue();
        } catch (Throwable t) {
            throw new CacheException(t);
        }
        return dbSize;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Set<K> keys() {
        try {
            //Set<String> keys = CacheHandler.getCacheHanlder().getKeys(this.SHIRO_REDIS_CACHE + "*");
            Set<String> keys = cache.getKeys(cache.getKey(this.SHIRO_REDIS_CACHE, "*"));
            if (CollectionUtils.isEmpty(keys)) {
                return Collections.emptySet();
            } else {
                Set<K> newKeys = new HashSet<K>();
                for (String key : keys) {
                    newKeys.add((K) key);
                }
                return newKeys;
            }
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

    @Override
    public Collection<V> values() {
        try {
            //Set<String> keys = CacheHandler.getCacheHanlder().getKeys(this.SHIRO_REDIS_CACHE + "*");
            Set<String> keys = cache.getKeys(cache.getKey(this.SHIRO_REDIS_CACHE, "*"));
            if (!CollectionUtils.isEmpty(keys)) {
                List<V> values = new ArrayList<V>(keys.size());
                for (String key : keys) {
                    @SuppressWarnings("unchecked")
                    V value = get((K) key);
                    if (value != null) {
                        values.add(value);
                    }
                }
                return Collections.unmodifiableList(values);
            } else {
                return Collections.emptyList();
            }
        } catch (Throwable t) {
            throw new CacheException(t);
        }
    }

}

