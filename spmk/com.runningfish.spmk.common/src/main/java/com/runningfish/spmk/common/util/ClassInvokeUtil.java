/**
 * @(#)ClassInvokeUtil.java 1.0 2015-3-23
 * @Copyright:  Copyright 2000 - 2015 Isoftstone Tech. Co. Ltd. All Rights Reserved.
 * @Description: 
 * 
 * Modification History:
 * Date:        2015-3-23 下午5:06:09
 * Author:      wangtao 28873
 * Version:     EPSPV1.D1.0.0.0
 * Description: (Initialize)
 * Reviewer:    
 * Review Date: 
 */
package com.runningfish.spmk.common.util;

import java.lang.reflect.Method;

/**
 * 
 * Copyright:   Copyright 2000 - 2015 Isoftstone Tech. Co. Ltd. All Rights Reserved.
 * Date:        2015-3-23 下午5:06:09
 * Author:      wangtao 28873
 * Version:     EPSP_V1.D1.0.0.0
 * Description: Initialize 
 */
public class ClassInvokeUtil {
	/**
     * @param obj
     *            操作的对象
     * @param att
     *            操作的属性
     * */
    public static Object getter(Object obj, String att) {
    	Object str = null;
        try {
            att = att.substring(0, 1).toUpperCase()+att.substring(1, att.length());
            Method method = obj.getClass().getMethod("get" + att);
            str =  method.invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
		return str;
    }
 
    /**
     * @param obj
     *            操作的对象
     * @param att
     *            操作的属性
     * @param value
     *            设置的值
     * @param type
     *            参数的属性
     * */
    public static void setter(Object obj, String att, Object value, Class<?> type) {
        try {
            att = att.substring(0, 1).toUpperCase()+att.substring(1, att.length());
            Method method = obj.getClass().getMethod("set" + att, type);
            method.invoke(obj, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args)
    {
        String s = "ssssname";
        s  = s.substring(0, 1).toUpperCase()+s.substring(1, s.length());
        System.out.println(s);
    }
    
}
