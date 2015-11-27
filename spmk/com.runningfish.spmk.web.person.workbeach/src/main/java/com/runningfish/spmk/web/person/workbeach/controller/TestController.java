package com.runningfish.spmk.web.person.workbeach.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.runningfish.spmk.common.I18nConstant;
import com.runningfish.spmk.common.ResponseStatus;
import com.runningfish.spmk.common.util.JsonUtils;
import com.runningfish.spmk.common.web.BaseController;
import com.runningfish.spmk.framework.plugin.I18nUtils;
import com.runningfish.spmk.framework.plugin.SpringContextUtil;
import com.runningfish.spmk.test.TestException;

@Controller
public class TestController extends BaseController
{
	private final Logger loger = LoggerFactory.getLogger(getClass());
	
	@RequestMapping
	public String test(HttpServletRequest request,HttpServletResponse response,String arg) throws TestException
	{
		loger.debug("测试开始。。。");
		String[] abc = {"11","22","33"};
		List<String> list = Arrays.asList(abc);
		SpringContextUtil obj = (SpringContextUtil)SpringContextUtil.getObject("springContextUtil");
		String str = obj.getMessage(ResponseStatus.STATUS_FAIL, new Object[]{I18nConstant.SHIRO_LOGIN_ACCOUNTORPASSWORDERROR}, request.getLocale());
		System.out.println(str);
		throw new TestException(ResponseStatus.STATUS_FAIL,
				I18nUtils.getMessage(I18nConstant.SHIRO_LOGIN_ACCOUNTORPASSWORDERROR));
		//JsonUtils.toJson(response, list);
		//loger.debug("测试结束。。。");
	}
	
}
