/**
 * @(#)UserInfoCacheHandler.java 1.0 2015-1-15
 * @Copyright:  Copyright 2000 - 2015 Isoftstone Tech. Co. Ltd. All Rights Reserved.
 * @Description: 
 * 
 * Modification History:
 * Date:        2015-1-15
 * Author:      wangtao 28873
 * Version:     SC_CAPV1.D1.0.0.0
 * Description: (Initialize)
 * Reviewer:    
 * Review Date: 
 */
package com.runningfish.spmk.common.web;

import org.springframework.beans.BeanUtils;

public class UserInfoCacheHandler
{
    
    private static UserInfoCacheHandler cacheHandler;
    
    public UserInfoCacheHandler()
    {
        
    }

    public static UserInfoCacheHandler getCacheHanlder()
    {
        if(cacheHandler == null)
            cacheHandler = new UserInfoCacheHandler();
        return cacheHandler;
    }
    
    public AccountInfo getUserInfo(String key){
        try
        {
            //UserSubjectInfo obj = (UserSubjectInfo) CacheHandler.getCacheHanlder().getCache("_USER_SUBJECTINFO_ACCOUNT:"+key);
            //UserSubjectInfo obj = (UserSubjectInfo)RedisHandler.getRedisHandler().getObject("_USER_SUBJECTINFO_ACCOUNT:"+key);
            AccountInfo userInfo = new AccountInfo();
            //copy
           // if(null!=obj){
            //    BeanUtils.copyProperties(obj, userInfo);
           // }
            return userInfo;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}

