package com.runningfish.spmk.service.config.service;

/**
 * @(#)IArgumentManager.java  2015-10-26
 * @author:svnCrush
 * @Description：redis管理接口
 * Date:2015-10-26
 * Version:SPMK_D1.0
 */
public interface IArgumentManager
{
    /**
     * 根据参数名获取redis参数值
     * @param argumentsName
     * @return
     */
	public String getArgumentsValue(String argumentsName);
}
