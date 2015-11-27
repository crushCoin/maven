package com.runningfish.spmk.framework.plugin;

import java.lang.reflect.Method;
import java.util.Locale;

import org.apache.shiro.session.Session;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.ReflectionUtils;

public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext arg0)
            throws BeansException {
        applicationContext = arg0;
		/*try {
			Class<?> request = Class.forName("javax.servlet.http.HttpServletRequest");
			Method sessionMethod = ReflectionUtils.findMethod(request, "getSession");
			Session session = (Session)ReflectionUtils.invokeJdbcMethod(sessionMethod, request.newInstance());
			session.setAttribute("name", "tom");
		} catch (Throwable e) {
			e.printStackTrace();
		}*/
    }

    public static Object getObject(String id) {
        Object object = null;
        try {
            //如果获取对象不存在，则返回null
            object = applicationContext.getBean(id);
        } catch (Exception e) {
            return null;
        }
        return object;
    }

    public String getMessage(String code, Object[] args) {
        Locale locale = ThreadLocalContextHandler.getContextHolder().getThreadContext().getLocale();
        return applicationContext.getMessage(code, args, locale);
    }
    
    public String getMessage(String code, Object[] args,Locale locale) {
        return applicationContext.getMessage(code, args, locale);
    }    
}

