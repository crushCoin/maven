package com.runningfish.spmk.framework.plugin;

import java.util.Set;

public interface ICache {

    /**
     * 保存对象到Redis缓存
     *
     * @param key
     * @param object
     */
    public void setCache(String key, Object object);

    /**
     * 保存对象到Redis缓存
     *
     * @param key
     * @param value
     * @param time
     */
    public void setCache(String key, Object value, int time);

    /**
     * 从Redis获取缓存
     *
     * @param key
     */
    public Object getCache(String key);

    /**
     * 根据key值删除Redis缓存
     *
     * @param key
     */
    public void delCache(String key);

    /**
     * 根据key集合删除Redis缓存
     *
     * @param keys
     */
    public void delCache(Set<String> keys);

    /**
     * 清空整个 Redis 服务器的数据
     */
    public void flushDB();

    /**
     * 返回当前redis库所存储数据的大小
     *
     * @return
     */
    public Long dbSize();

    /**
     * 获取当前redis key集合
     *
     * @param key
     * @return
     */
    public Set<String> getKeys(String key);

    /**
     * 拼装key
     *
     * @param strings
     * @return
     */
    public String getKey(Object... strings);
}

