package com.runningfish.spmk.service.config.pojo;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/***
 * @(#)RedisArgument.java  2015-10-26
 * @author:svnCrush
 * @Description：redis配置实体类
 * Date:2015-10-26
 * Version:SPMK_D1.0
 */
public class RedisArgument implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * redis端口、主机地址参数名
	 */
	private String argumentsName;
	
	/**
	 * redis端口、主机地址参数值
	 */
	private String argumentsValue;
	
	/**
	 * 状态值
	 */
	private int statue;
	
	/**
	 * 参数描述
	 */
	private String description;
	
	/**
	 * 创建时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private Date createTime;
	
	/**
	 * 创建用户
	 */
	private String createUser;
	
	/**
	 * 最后修改时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private Date lastModifiedTime;
	
	/**
	 * 最后修改用户
	 */
	private String lastModifiedUser;

	public String getArgumentsName()
	{
		return argumentsName;
	}

	public void setArgumentsName(String argumentsName)
	{
		this.argumentsName = argumentsName;
	}

	public String getArgumentsValue()
	{
		return argumentsValue;
	}

	public void setArgumentsValue(String argumentsValue)
	{
		this.argumentsValue = argumentsValue;
	}

	public int getStatue()
	{
		return statue;
	}

	public void setStatue(int statue)
	{
		this.statue = statue;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public String getCreateUser()
	{
		return createUser;
	}

	public void setCreateUser(String createUser)
	{
		this.createUser = createUser;
	}

	public Date getLastModifiedTime()
	{
		return lastModifiedTime;
	}

	public void setLastModifiedTime(Date lastModifiedTime)
	{
		this.lastModifiedTime = lastModifiedTime;
	}

	public String getLastModifiedUser()
	{
		return lastModifiedUser;
	}

	public void setLastModifiedUser(String lastModifiedUser)
	{
		this.lastModifiedUser = lastModifiedUser;
	}
	
}
