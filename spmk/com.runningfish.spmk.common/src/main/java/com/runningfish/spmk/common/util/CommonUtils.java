/**
 * @(#)CommonUtils.java 1.0 2015-1-7
 * @Copyright:  Copyright 2000 - 2015 Isoftstone Tech. Co. Ltd. All Rights Reserved.
 * @Description: 
 * 
 * Modification History:
 * Date:        2015-1-7
 * Author:      dengxinjun 42715
 * Version:     EPSP_CAPV1.D1.0.0.0
 * Description: (Initialize)
 * Reviewer:    
 * Review Date: 
 */
package com.runningfish.spmk.common.util;

import java.util.UUID;


public class CommonUtils
{
    /**
     * 获取随机UUID（不带中划线）
     * @param prefix UUID前缀，可为空
     * @return
     */
    public static String getUUID(String prefix)
    {
        prefix = prefix == null ? "" : prefix;
        return prefix + UUID.randomUUID().toString().replaceAll("-", "");
    }
    
    /**
     * 判断数字是否在指定范围内
     * @param current 当前数字
     * @param min 范围内：最小值
     * @param max 范围内：最大值
     * @return true 是、false 否
     */
    public static boolean rangeInDefined(int current, int min, int max)
    {
        return Math.max(min, current) == Math.min(current, max);
    }
    
    public static void main(String[] args)
    {
       // System.out.println(getUUID("Q_"));
        System.out.println(rangeInDefined(5, 1, 10));
    }
}
