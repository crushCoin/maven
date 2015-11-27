package com.runningfish.spmk.framework.plugin;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 基于jedis操作redis数据库的处理类
 * 
 * @author hcliua
 */
public class RedisHandler {

	private static final Logger logger = LoggerFactory.getLogger(RedisHandler.class);
	
	/**
	 * redis pool池配置
	 */
	private static JedisPoolConfig config = new JedisPoolConfig();

	/**
	 * redis pool池
	 */
	private static JedisPool pool;
	
	private static RedisHandler redisHandler;
	
	public static String _host = "127.0.0.1";
	public static int _port = 6379;
	public static boolean isAuth = false;
	public static String password = "";


	/**
	 * 无参构造函数 config默认 host,port,isAuth,password
	 */
	public  RedisHandler(String host, int port) {
		config.setMaxActive(50000);
        config.setMaxIdle(10000);
        config.setMaxWait(2000);
		pool = new JedisPool(config, host, port);
	}
	
	/**
	 * 初始化redis server对象 
	 * @param host
	 * @param port
	 */
	public static void initRedisHandler(String host, int port){		
		if(redisHandler == null)
		{
			redisHandler=new RedisHandler(host, port);
		}
	}
	
	public static RedisHandler getRedisHandler() throws Exception
	{
		if(redisHandler == null)
		{
			throw new Exception("Uninitialized RedisHandler");
		}
		return redisHandler;
	}
	
	/**
	 * 从池里获取一个redis客服端
	 */
	public Jedis getJedis() {
		Jedis jedis = pool.getResource();
		if(!jedis.isConnected()) {
			logger.debug("redis reconnect.");
			jedis.connect();
		}
		if (isAuth) {
			jedis.auth(password);
		}
		return jedis;
	}

	/**
	 * 设置对象
	 */
	public void setObject(String key, Object obj) throws Exception {
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			jedis.set(key, SerializableHandler.objectToString(obj));
		} catch (Exception e) {
			logger.error("setObject failed. key=" + key, e);
			pool.returnBrokenResource(jedis);
			throw e;
		} finally {
			pool.returnResource(jedis);
		}
	}

	/**
	 * 设置对象（带过期时间，单位：秒）
	 */
	public void setObject(String key, Object obj, int time) throws Exception {
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			jedis.set(key, SerializableHandler.objectToString(obj));
			jedis.expire(key, time);//过期时间 
		} catch (Exception e) {
			logger.error("setObject failed. key=" + key, e);
			pool.returnBrokenResource(jedis);
			throw e;
		} finally {
			pool.returnResource(jedis);
		}
	}
	
	/**
	 * 获取对象
	 */
	public Object getObject(String key) throws Exception  {
		logger.info(new Date().getTime()+"  getObject  key="+key);
		Jedis jedis = null;
		Object obj = null;
		try {
			jedis = this.getJedis();
			String value = jedis.get(key);
			obj = SerializableHandler.stringToObject(value);
			
		} catch (Exception e) {
			logger.error("getObject failed. key=" + key, e);
			pool.returnBrokenResource(jedis);
			throw e;
		} finally {
			pool.returnResource(jedis);
		}
		return obj;
	}
	
	/**
	 * 删除对象
	 */
	public void delObject(String key)throws Exception {
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			jedis.del(key);
		} catch (Exception e) {
			logger.error("delObject failed. key=" + key, e);
			pool.returnBrokenResource(jedis);
			throw e;
		} finally {
			pool.returnResource(jedis);
		}
	}
	
	/**
	 * 删除对象
	 */
	public void delObject(Set<String> keys)throws Exception {
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			if(keys!=null && keys.size()>0){
				Iterator<String> iterator = keys.iterator();
				String arr = null;
				while(iterator.hasNext()){
					arr = iterator.next();
					jedis.del(arr);
				}
			}
		} catch (Exception e) {
			logger.error("delObject failed. ", e);
			pool.returnBrokenResource(jedis);
			throw e;
		} finally {
			pool.returnResource(jedis);
		}
	}
	
	public Set<String> getKeys(String key) throws Exception{
		Jedis jedis = null;
		Set<String> keySet = null;
		try {
			jedis = this.getJedis();
			keySet = jedis.keys(key);
		} catch (Exception e) {
			logger.error("getKeys failed. ", e);
			pool.returnBrokenResource(jedis);
			throw e;
		} finally {
			pool.returnResource(jedis);
		}
		return keySet;
	}
	
	/**
	 * 清空当前的redis 库
	 */
	public void flushDB() {
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			jedis.flushDB();
		} catch (Exception e) {
			logger.error("getKeys failed. ", e);
			pool.returnBrokenResource(jedis);
		} finally {
			pool.returnResource(jedis);
		}
	}
	
	/**
	 * 返回当前redis库所存储数据的大小
	 */
	public Long dbSize() {
		Long dbSize = 0L;
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			dbSize = jedis.dbSize();
		} catch (Exception e) {
			logger.error("getKeys failed. ", e);
			pool.returnBrokenResource(jedis);
		} finally {
			pool.returnResource(jedis);
		}
        return dbSize;
	}
}
