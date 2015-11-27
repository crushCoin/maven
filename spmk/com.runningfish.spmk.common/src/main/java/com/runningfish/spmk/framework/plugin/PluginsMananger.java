package com.runningfish.spmk.framework.plugin;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 框架插件管理容器
 * @author fuping
 */
@Scope
@Component
public class PluginsMananger {
    
    private Logger log = LoggerFactory.getLogger(getClass());
    
    /**
     * 插件缓存容器
     */
    public static Map<String, String> pluginsMap = new HashMap<String,String>();
    
    public String getPluginInfo(String pluginContextName){
        
        
        return null;
    }
    
    /**
     * 向插件管理缓存中添加插件
     * @param contextPath 插件上下文
     * @param filePath 插件路径
     */
    public synchronized void addPlugin(String contextPath, String filePath){
        if(contextPath != null && filePath != null){
            log.debug("install plugin successful, plugin context=[{}]", contextPath, filePath);
            pluginsMap.put(contextPath, filePath);
        }else
            log.error("install plugin faile, plugin context=[{}]", contextPath, filePath);
    }

}

