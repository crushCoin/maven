package com.runningfish.spmk.web.person.workbeach.controller;

import javax.annotation.Resource;

import com.runningfish.spmk.framework.plugin.RedisHandler;
import com.runningfish.spmk.service.config.service.IArgumentManager;

public class InitRedis 
{
	@Resource
	private IArgumentManager argumentManager;
	
	public void init()
    {
    	String host = argumentManager.getArgumentsValue("redis_host");
    	int port = Integer.valueOf(argumentManager.getArgumentsValue("redis_port")).intValue();
		RedisHandler.initRedisHandler(host, port);
    }
}
