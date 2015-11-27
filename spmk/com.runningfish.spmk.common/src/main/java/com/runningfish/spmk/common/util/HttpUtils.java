/**
 * @(#)HttpUtils.java 1.0 2015-1-22
 * @Copyright:  Copyright 2000 - 2015 Isoftstone Tech. Co. Ltd. All Rights Reserved.
 * @Description: 
 * 
 * Modification History:
 * Date:        2015-1-22
 * Author:      dengxinjun 42715
 * Version:     EPSP_CAPV1.D1.0.0.0
 * Description: (Initialize)
 * Reviewer:    
 * Review Date: 
 */
package com.runningfish.spmk.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HTTP工具类
 * Copyright:   Copyright 2000 - 2015 Isoftstone Tech. Co. Ltd. All Rights Reserved.
 * Date:        2015-1-22 下午3:07:26
 * Author:      dengxinjun 42715
 * Version:     EPSP_CAPV1.D1.0.0.0
 * Description: Initialize
 */
public class HttpUtils
{
    private static final Logger logger =
        LoggerFactory.getLogger(HttpUtils.class);
    
    /** 连接超时时间：毫秒 */
    private static final int CONNECT_TIMEOUT = 5000;
    
    /** 读取超时时间：毫秒 */
    private static final int READ_TIMEOUT = 5000;
    
    /**
     * GET方法请求
     * @param requestUrl 请求地址 
     * @param params 参数
     * @return
     * @throws Exception
     */
    public static String getMethod(String requestUrl) throws Exception
    {
        logger.info("getMethod begin.[requesturl=" + requestUrl + "]");
        
        String respStr = null;
        URL url = null;
        HttpURLConnection httpURLConnection = null;
        try
        {
            if (StringUtils.isBlank(requestUrl))
            {
                logger.error("请求地址不能为空");
                throw new RuntimeException("请求地址不能为空");
            }
            url = new URL(requestUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            //            httpURLConnection.setRequestProperty("Content-type",
            //                "text/html; charset=UTF-8");
            
            httpURLConnection.setConnectTimeout(CONNECT_TIMEOUT);
            httpURLConnection.setReadTimeout(READ_TIMEOUT);
            httpURLConnection.connect();
            
            logger.info("GET远程服务请求状态码： responseCode="
                + httpURLConnection.getResponseCode());
            
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                respStr = parseData(httpURLConnection.getInputStream());
            }
            else
            {
                logger.error("GET远程服务请求失败，responseCode="
                    + httpURLConnection.getResponseCode());
                throw new RuntimeException("GET远程服务请求失败，responseCode="
                    + httpURLConnection.getResponseCode());
            }
        }
        catch (Exception e)
        {
            logger.error("GET远程服务请求出错啦: " + e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
        finally
        {
            if (httpURLConnection != null)
            {
                httpURLConnection.disconnect();
            }
        }
        return respStr;
    }
    
    /**
     * POST方法请求
     * @param requestUrl 请求地址
     * @param params 参数
     * @return
     * @throws Exception
     */
    public static String postMethod(String requestUrl, String params)
        throws Exception
    {
        logger.info("postMethod begin.[requesturl=" + requestUrl + ", params={"
            + params + "}]");
        
        String respStr = null;
        URL url = null;
        HttpURLConnection httpURLConnection = null;
        try
        {
            if (StringUtils.isBlank(requestUrl))
            {
                logger.error("请求地址不能为空");
                throw new RuntimeException("请求地址不能为空");
            }
            url = new URL(requestUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            
            httpURLConnection.setRequestProperty("Content-type",
                "application/x-www-form-urlencoded; charset=UTF-8");
            httpURLConnection.setConnectTimeout(CONNECT_TIMEOUT);
            httpURLConnection.setReadTimeout(READ_TIMEOUT);
            
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            
            if (StringUtils.isNotBlank(params))
            {
                httpURLConnection.getOutputStream()
                    .write(params.getBytes("UTF-8"));
            }
            httpURLConnection.getOutputStream().flush();
            httpURLConnection.getOutputStream().close();
            
            logger.info("POST远程服务请求状态码： responseCode="
                + httpURLConnection.getResponseCode());
            
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                respStr = parseData(httpURLConnection.getInputStream());
            }
            else
            {
                logger.error("POST远程服务请求失败，responseCode="
                    + httpURLConnection.getResponseCode());
                throw new RuntimeException("POST远程服务请求失败，responseCode="
                    + httpURLConnection.getResponseCode());
            }
        }
        catch (Exception e)
        {
            logger.error("POST远程服务请求出错啦: " + e.getMessage(), e);
            e.printStackTrace();
            throw e;
        }
        finally
        {
            if (httpURLConnection != null)
            {
                httpURLConnection.disconnect();
            }
        }
        return respStr;
    }
    
    /**
     * 解析数据
     * @param is
     * @return
     * @throws IOException
     */
    private static String parseData(InputStream is) throws IOException
    {
        StringBuffer dataStr = new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String currentLine = null;
        
        while ((currentLine = reader.readLine()) != null)
        {
            if (currentLine.length() > 0)
            {
                dataStr.append(currentLine.trim());
            }
        }
        reader.close();
        
        return dataStr.toString();
    }
    
    public static void main(String[] args) throws Exception
    {
        String params =
            "page=1&rows=1&org_id=12&name_="
                + URLEncoder.encode("问题处理科室", "UTF-8");
        String url =
            "http://localhost:8089/sc/mvc/structure/api/structure/all.json";
        
        String respStr = getMethod(url + "?" + params);
        System.out.println(respStr);
        
        Pattern pattern = Pattern.compile("\"id\":(.+?)");
        Matcher matcher = pattern.matcher(respStr);
        
        String val = null;
        if (matcher.find())
        {
            val = matcher.group(1);
        }
        System.out.println(val);
        
        params =
            "org_id=12&status_=0&page=1&rows=" + Integer.MAX_VALUE
                + "&structureID=" + val;
        url = "http://localhost:8089/sc/mvc/user/api/user/all.json";
        // 发送请求
        respStr = HttpUtils.postMethod(url, params);
        System.out.println(respStr);
    }
}
