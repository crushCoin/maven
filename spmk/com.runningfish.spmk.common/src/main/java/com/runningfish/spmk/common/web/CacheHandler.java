package com.runningfish.spmk.common.web;

import java.util.Properties;
import java.util.Set;

import com.runningfish.spmk.framework.plugin.PropertiesHandler;
import com.runningfish.spmk.framework.plugin.RedisHandler;


/**
 * 缓存操作
 * @author issuser
 *
 */
public class CacheHandler {
	
	/**
	 * 类型 0：系统变量使用Properties存储，1:redis缓存
	 */
	private static int type= 0;
	
	private static String systemNumber;
	
	private static CacheHandler cacheHandler = new CacheHandler();
	
	public static CacheHandler getCacheHanlder()
	{
		return cacheHandler;
	}
	
	public static void setType(int t)
	{
	    cacheHandler.type = t;
	}

	public static void setSystemNumber(String systemNumber) {
	    cacheHandler.systemNumber = systemNumber;
	}

	/**
	 * 新增properties缓存
	 * @param properties
	 * @throws Exception
	 */
	public void setCache(Properties properties)throws Exception{
		if(properties != null && type == 0)
		{
			PropertiesHandler.getPropertiesHolder().putAll(properties);
		}
	}
	
	/**
	 * 设置缓存
	 * @param key
	 * @param value
	 * @throws Exception
	 */
	public void setCache(String key, Object value) throws Exception
	{
		if(type==0){
			if(systemNumber != null && !"".equals(systemNumber)){
				PropertiesHandler.getPropertiesHolder().setProperties(systemNumber+key, value);
			}else
			{
				PropertiesHandler.getPropertiesHolder().setProperties(key, value);
			}
		}else{
			if(systemNumber != null && !"".equals(systemNumber)){
				RedisHandler.getRedisHandler().setObject(systemNumber+key, value);
			}else
			{
				RedisHandler.getRedisHandler().setObject(key, value);
			}
		}
	}
	
	/**
	 * 设置缓存
	 * @param key
	 * @param value
	 * @throws Exception
	 */
	public void setCache(String key, Object value, int time) throws Exception
	{
		if(type==0){
			if(systemNumber != null && !"".equals(systemNumber))
			{
				PropertiesHandler.getPropertiesHolder().setProperties(systemNumber+key, value);
			}else
			{
				PropertiesHandler.getPropertiesHolder().setProperties(key, value);
			}
		}else{
			if(systemNumber != null && !"".equals(systemNumber))
			{
				RedisHandler.getRedisHandler().setObject(systemNumber+key, value,time);
			}else
			{
				RedisHandler.getRedisHandler().setObject(key, value,time);
			}
		}
	}
	
	/**
	 * 获取缓存
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public Object getCache(String key) throws Exception
	{
		if(type == 0)
		{
			if(systemNumber != null && !"".equals(systemNumber)){
				return PropertiesHandler.getPropertiesHolder().getProperties(systemNumber+key);
			}else
			{
				return PropertiesHandler.getPropertiesHolder().getProperties(key);
			}
		}else
		{
			if(systemNumber != null && !"".equals(systemNumber))
			{
				return RedisHandler.getRedisHandler().getObject(systemNumber+key);
			}else
			{
				return RedisHandler.getRedisHandler().getObject(key);
			}
		}
	}
	
	/**
	 * 删除缓存
	 * @param key
	 * @throws Exception
	 */
	public void delCache(String key)throws Exception
	{
		if(type == 0)
		{
			if(systemNumber != null && !"".equals(systemNumber)){
				PropertiesHandler.getPropertiesHolder().delProperties(systemNumber+key);
			}else
			{
				PropertiesHandler.getPropertiesHolder().delProperties(key);
			}
		}else
		{
			if(systemNumber != null && !"".equals(systemNumber))
			{
				RedisHandler.getRedisHandler().delObject(systemNumber+key);
			}else
			{
				RedisHandler.getRedisHandler().delObject(key);
			}
		}
	}
	/**
	 * 删除缓存
	 * @param keys
	 * @throws Exception
	 */
	public void delObject(Set<String> keys) throws Exception{
		if(type == 0){
			
		}else{
			if(systemNumber != null && !"".equals(systemNumber))
			{
				RedisHandler.getRedisHandler().delObject(systemNumber+keys);
			}else
			{
				RedisHandler.getRedisHandler().delObject(keys);
			}
		}
	}
	
	public Set<String> getKeys(String key) throws Exception{
		if(type == 0){
			return null;
		}else{
			if(systemNumber != null && !"".equals(systemNumber))
			{
				return RedisHandler.getRedisHandler().getKeys(systemNumber+key);
			}else
			{
				return RedisHandler.getRedisHandler().getKeys(key);
			}
		}
	} 
	
	public void flushDB() throws Exception{
		if(type == 0){
			
		}else{
			RedisHandler.getRedisHandler().flushDB();
		}
	}
	
	public Long dbSize() throws Exception{
		if(type == 0){
			return 0L;
		}else{
			return RedisHandler.getRedisHandler().dbSize();
		}
	}
}

