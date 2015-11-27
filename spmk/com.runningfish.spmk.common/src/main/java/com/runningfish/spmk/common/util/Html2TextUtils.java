package com.runningfish.spmk.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @(#)Html2Text.java 1.0 2015-3-23
 * Copyright:   Copyright 2000 - 2015 Isoftstone Tech. Co. Ltd. All Rights Reserved.
 * Date:        2015年3月23日 上午10:57:42
 * Author:      xiaoxiong 69520
 * Version:     EPSP_CAPV1.D1.0.0.0
 * Description: Initialize
 */
public class Html2TextUtils
{
    /**
     * 返回纯文本字符串
     * @param html 含html标签的字符串
     * @return
     */
    public static String html2Text(String html)
    {
        if (html == null || "".equals(html.trim()))
        {
            return "";
        }
        
        // 含html标签的字符串 
        String htmlStr = html;
        String textStr = "";
        
        //过滤脚本文件
        Pattern p_script;
        Matcher m_script;
        
        //过滤样式文件
        Pattern p_style;
        Matcher m_style;
        
        //过滤html标签
        Pattern p_start_html;
        Matcher m_start_html;
        Pattern p_end_html;
        Matcher m_end_html;
        
        //过滤特殊字符
        Pattern p_other_html;
        Matcher m_other_html;
        try
        {
            // 定义script的正则表达式{或<script[^>]*?>[//s//S]*?<///script> 
            String regEx_script =
                "<[//s]*?script[^>]*?>[//s//S]*?<[//s]*?///[//s]*?script[//s]*?>";
            
            // 定义style的正则表达式{或<style[^>]*?>[//s//S]*?<///style> 
            String regEx_style =
                "<[//s]*?style[^>]*?>[//s//S]*?<[//s]*?///[//s]*?style[//s]*?>";
            
            // 定义HTML标签的正则表达式 
            String regEx_start_html = "<[^>]+>";
            String regEx_end_html = "<[^>]+";
            
            //过滤空格的正则表达式
            String regEx_space_html1 = "&nbsp;";
            String regEx_space_html2 = " ";
            
            // 过滤script标签 
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll("");
            
            // 过滤style标签  
            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll("");
            
            // 过滤html标签
            p_start_html =
                Pattern.compile(regEx_start_html, Pattern.CASE_INSENSITIVE);
            m_start_html = p_start_html.matcher(htmlStr);
            htmlStr = m_start_html.replaceAll("");
            
            // 过滤html开始标签 
            p_end_html =
                Pattern.compile(regEx_end_html, Pattern.CASE_INSENSITIVE);
            m_end_html = p_end_html.matcher(htmlStr);
            htmlStr = m_end_html.replaceAll("");
            
            // 过滤html结束标签 
            p_other_html =
                Pattern.compile(regEx_space_html1, Pattern.CASE_INSENSITIVE);
            m_other_html = p_other_html.matcher(htmlStr);
            htmlStr = m_other_html.replaceAll("");
            
            // 过滤html结束标签 
            p_other_html =
                Pattern.compile(regEx_space_html2, Pattern.CASE_INSENSITIVE);
            m_other_html = p_other_html.matcher(htmlStr);
            htmlStr = m_other_html.replaceAll("");
            
            textStr = htmlStr;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        // 返回文本字符串    
        return textStr.trim();
    }
}
