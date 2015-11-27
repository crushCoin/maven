package com.runningfish.spmk.framework.plugin;

import java.util.Locale;

/**
 * 
 * ThreadContext.java
 * 线程用户上下文  用户、地址等信息
 * (一句话功能简述)
 * 
 * (功能详细描述)
 * 
 * @author    hcliu
 * @version   [版本号, YYYY-MM-DD]
 * @see       [相关类/方法]
 * @since     [产品/模块版本]
 */
public class ThreadContext {
	private String User;
	private String host;
	private String content;
	
	/**
	 * 用户所属机构
	 */
	private String orgId;
	/**
	 * 语言环境
	 */
	private Locale locale;
	
	public String getUser() {
		return User;
	}
	public void setUser(String user) {
		User = user;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
    public String getOrgId() {
        return orgId;
    }
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
	public Locale getLocale() {
		return locale;
	}
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
}
