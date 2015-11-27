package com.runningfish.spmk.framework.plugin;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基于Properties操作参数
 * @author hcliua
 *
 */
public class PropertiesHandler {
	private static final Logger logger = LoggerFactory.getLogger(PropertiesHandler.class);
	
	private static Properties properties = new Properties();
	
	private static PropertiesHandler pHolder;
	
	/**
	 * 初始化properties缓存
	 * @return
	 */
	public static PropertiesHandler getPropertiesHolder()
	{
		if(pHolder==null)
		{
			pHolder=new PropertiesHandler();
		}
		return pHolder;
	}
	
	/**
	 * 设置properties缓存
	 * @param key
	 * @param value
	 * @throws Exception
	 */
	public void setProperties(String key,Object value) throws Exception
	{
		try {
			properties.setProperty(key, SerializableHandler.objectToString(value));
		} catch (Exception e) {
			logger.error("setObject failed. key=" + key, e);
		}
	}
	
	/**
	 * 获取properties缓存
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public Object getProperties(String key)throws Exception
	{
		String val=properties.getProperty(key);
		if(null != val)
		{
			return SerializableHandler.stringToObject(val);
		}else{
			logger.debug("arguments "+key+ "  not find..");
			return null;
		}
		
	}
	
	/**
	 * 删除properties缓存
	 * @param key
	 * @throws Exception
	 */
	public void delProperties(String key)throws Exception
	{
		if(null != properties.get(key))
		{
			properties.remove(key);
		}else
		{
			logger.debug("delete failed arguments "+key+  "not find..   ");
		}
	}
	
	/**
	 * putAll properties
	 * @param p
	 * @throws Exception
	 */
	public void putAll(Properties p)throws Exception
	{
		if(null != p)
		{
			properties.putAll(p);
		}
	}
}

