package com.runningfish.spmk.common;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

public class SysLogAdvice implements MethodInterceptor {
	private static final Logger LOG = LoggerFactory.getLogger(SysLogAdvice.class);
	private static final String BEGIN = "[METHOD BEGIN]";
	private static final String OVER = "[METHOD OVER]";
	private static final String ARG_INPUT = "[INPUT ARGS]";
	private static final String ARG_OUTPUT = "[OUTPUT ARGS]";
	private static final String RETURN = "[RETURN]";
	private static final String SEPRATOR = ":";
	
	@Override	
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		LOG.info(BEGIN + methodInvocation.getMethod());
		Object[] args = methodInvocation.getArguments();
		if(args!=null) {
			for(int i=0; i<args.length; i++) {
				LOG.info(ARG_INPUT + "["+i+"]" + SEPRATOR + args[i]);
			}
		}
		
		Object result = methodInvocation.proceed();
		if(result != null && result instanceof ModelAndView) {
			ModelAndView modelAndView = (ModelAndView)result;
			//如果是MappingJacksonJsonView，打印map的值
			if(modelAndView.getView()!=null && modelAndView.getView() instanceof MappingJacksonJsonView) {
				MappingJacksonJsonView jsonView = (MappingJacksonJsonView)modelAndView.getView();
				LOG.info(ARG_OUTPUT + SEPRATOR + jsonView.getAttributesMap());
			}
		}
		
		//如果有返回值类型不为void，打印返回值
		if(void.class  != methodInvocation.getMethod().getReturnType() ) {
			LOG.info(RETURN + SEPRATOR + (result==null?"null":result));
		}
		
		LOG.info(OVER + methodInvocation.getMethod());
		return result;
	}

}
