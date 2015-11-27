package com.runningfish.spmk.common.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ExceptionHandler;

import com.runningfish.spmk.common.util.JsonUtils;


public class BaseController
{
    private ThreadLocal<Locale> locale = new ThreadLocal<Locale>();
    
    public Locale getLocale()
    {
        return this.locale.get();
    }
    
    public void setLocale(Locale locale)
    {
        this.locale.set(locale);
    }
    
    @ExceptionHandler
    public String exception(HttpServletRequest request,
        HttpServletResponse response, Exception e) throws Exception
    {
        String error = null;
        
        //如果是ajax请求
        if (request.getHeader("X-Requested-With") != null
            && request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1)
        {
            response.setStatus(200);
            response.setContentType("application/json;charset=utf-8");
            try
            {
                ExceptionResponse exceptionResponse =
                    new ExceptionResponse(false, "system exception",
                        e.getMessage());
                String jsonErrorMsg = JsonUtils.toJson(exceptionResponse);
                PrintWriter writer = response.getWriter();
                writer.write(jsonErrorMsg);
                writer.flush();
            }
            catch (IOException e1)
            {
                e1.printStackTrace();
            }
            return null;
        }
        else
        {
            //如果是普通请求
            //	        request.setAttribute("exceptionMessage", e.getMessage());  
            //	 
            //	        // 根据不同的异常类型可以返回不同界面
            //	        if(e instanceof SQLException) 
            //	            return "testerror";   
            //	        else
            //	            return "error";  
            //error = "error";
        }
        return error;
    }
    
}
