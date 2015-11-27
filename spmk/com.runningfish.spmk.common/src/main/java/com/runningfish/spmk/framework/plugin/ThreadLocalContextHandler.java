package com.runningfish.spmk.framework.plugin;

import java.lang.reflect.Method;

import org.springframework.util.ReflectionUtils;

public class ThreadLocalContextHandler {
    private ThreadLocal<ThreadContext> threadContext = new ThreadLocal<ThreadContext>();

    private static ThreadLocalContextHandler local ;
    
    public static ThreadLocalContextHandler getContextHolder()
    {
    	if(null == local)
    	{
    		local=new ThreadLocalContextHandler();
    	}
    	return local;
    }
    
    public void setThreadContext(ThreadContext context)
    {
    	threadContext.set(context);
    }

    public ThreadContext getThreadContext()
    {
    	return threadContext.get();
    }
    
    public void init() {
        ThreadContext ct = threadContext.get();
        if(null == ct){
            ct = new ThreadContext();
            this.setThreadContext(ct);
        }
        
        try {
            Class<?> securityUtilClass = Class.forName("org.apache.shiro.SecurityUtils");
            Method subjectMethod = ReflectionUtils.findMethod(securityUtilClass, "getSubject");
            Object subjectObject = ReflectionUtils.invokeJdbcMethod(subjectMethod, securityUtilClass);
            
            Method sessionMethod = ReflectionUtils.findMethod(subjectObject.getClass().getSuperclass(), "getSession");
            Object sessionObject = ReflectionUtils.invokeJdbcMethod(sessionMethod, subjectObject);
            
            if(null == sessionObject){
                return;
            }
            
            Method getSessionMethod = ReflectionUtils.findMethod(sessionObject.getClass().getSuperclass(), "getAttribute", Object.class);
            Object defaultAccountSubject = getSessionMethod.invoke(sessionObject, "_Security_USER_KEY");

            if(null == defaultAccountSubject){
                return;
            }

            Method accountObjectMethod = ReflectionUtils.findMethod(defaultAccountSubject.getClass(), "getSubjectInfo");
            Object accountObject = accountObjectMethod.invoke(defaultAccountSubject);
            
            Method userMethod = ReflectionUtils.findMethod(accountObject.getClass(), "getAccountName");
            ct.setUser((String)userMethod.invoke(accountObject));
            Method orgIdMethod = ReflectionUtils.findMethod(accountObject.getClass(), "getOrganizationId");
            ct.setOrgId(String.valueOf(orgIdMethod.invoke(accountObject)));
        } catch (Throwable e) {
        	e.printStackTrace();
        }
       
        
        ct.setContent("contentMsg");
    }
}
