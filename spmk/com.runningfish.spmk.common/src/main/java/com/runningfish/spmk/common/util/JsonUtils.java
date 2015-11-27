package com.runningfish.spmk.common.util;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * Json工具类
 * Copyright:   Copyright 2000 - 2014 Isoftstone Tech. Co. Ltd. All Rights Reserved.
 * Date:        2014-12-18 下午4:26:25
 * Author:      dengxinjun 42715
 * Version:     EPSP_CAPV1.D1.0.0.0
 * Description: Initialize
 */
public final class JsonUtils
{
    private static final Logger logger =
        LoggerFactory.getLogger(JsonUtils.class);
    
    private static ObjectMapper objectMapper = new ObjectMapper();
    
    static
    {
        // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        objectMapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    
    public static String toJson(Object value)
    {
        try
        {
            return objectMapper.writeValueAsString(value);
        }
        catch (Exception localException)
        {
            localException.printStackTrace();
            logger.error(localException.getMessage(), localException);
        }
        return null;
    }
    
    public static void toJson(HttpServletResponse response, String contentType,
        Object value)
    {
        Assert.notNull(response);
        Assert.hasText(contentType);
        try
        {
            response.setContentType(contentType);
            objectMapper.writeValue(response.getWriter(), value);
        }
        catch (Exception localException)
        {
            localException.printStackTrace();
            logger.error(localException.getMessage(), localException);
        }
    }
    
    public static void toJson(HttpServletResponse response, Object value)
    {
        Assert.notNull(response);
        try
        {
            // 设置编码格式
            response.setContentType("text/html; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            
            objectMapper.writeValue(response.getWriter(), value);
        }
        catch (Exception localException)
        {
            localException.printStackTrace();
            logger.error(localException.getMessage(), localException);
        }
    }
    
    public static <T> T toObject(String json, Class<T> valueType)
    {
        Assert.hasText(json);
        Assert.notNull(valueType);
        try
        {
            return objectMapper.readValue(json, valueType);
        }
        catch (Exception localException)
        {
            localException.printStackTrace();
            logger.error(localException.getMessage(), localException);
        }
        return null;
    }
    
    public static <T> T toObject(String json, TypeReference<?> typeReference)
    {
        Assert.hasText(json);
        Assert.notNull(typeReference);
        try
        {
            return objectMapper.readValue(json, typeReference);
        }
        catch (Exception localException)
        {
            localException.printStackTrace();
            logger.error(localException.getMessage(), localException);
        }
        return null;
    }
    
    public static <T> T toObject(String json, JavaType javaType)
    {
        Assert.hasText(json);
        Assert.notNull(javaType);
        try
        {
            return objectMapper.readValue(json, javaType);
        }
        catch (Exception localException)
        {
            localException.printStackTrace();
            logger.error(localException.getMessage(), localException);
        }
        return null;
    }
}
