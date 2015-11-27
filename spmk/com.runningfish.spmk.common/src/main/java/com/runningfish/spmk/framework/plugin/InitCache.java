package com.runningfish.spmk.framework.plugin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import com.runningfish.spmk.common.web.CacheHandler;

/**
 * 初始化cache类型
 * @author issuser
 *
 */
@ComponentScan
public class InitCache{

	/***
	 * cache类型，1:redis 0:properties
	 */
	private int cacheType;
	
	@Autowired
	private SystemENV systemEnv;

	public void setCacheType(int cacheType) {
		this.cacheType = cacheType;
		CacheHandler.getCacheHanlder().setType(cacheType);
		CacheHandler.getCacheHanlder().setSystemNumber(systemEnv.getSystemNumber());
	}
	
}
