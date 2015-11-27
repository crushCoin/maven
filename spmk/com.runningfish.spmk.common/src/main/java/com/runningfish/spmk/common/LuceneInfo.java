package com.runningfish.spmk.common;

import java.io.Serializable;
import java.util.Date;

/**
 * @(#)LucenceInfo.java 1.0 2015-3-21
 * Copyright:   Copyright 2000 - 2015 Isoftstone Tech. Co. Ltd. All Rights Reserved.
 * Date:        2015年3月21日 上午10:29:02
 * Author:      xiaoxiong 69520
 * Version:     EPSP_CAPV1.D1.0.0.0
 * Description: Initialize
 */
public class LuceneInfo implements Serializable
{
    private static final long serialVersionUID = 2092873772755276906L;
    
    private String id;
    
    private Integer moduleId;//搜素类型Id  1.资讯 2.服务

    private Integer subModuleId;//资讯类型id
    
    private Integer grade;//企业等级
    
    private String title; //标题
    
    private String content;  //内容
    
    private String url;  //链接地址
    
    private Date date;   //发布时间
    
    /**
     * 构造方法
     * @param id
     * @param moduleId 
     */
    public LuceneInfo(String id, Integer moduleId)
    {
        super();
        this.id = id;
        this.moduleId = moduleId; //搜素类型Id  1.资讯 2.服务
    }

    public LuceneInfo(String id, Integer moduleId, Integer subModuleId,Integer grade)
    {
//        super();
        this.id = id;
        this.moduleId = moduleId;
        this.subModuleId = subModuleId;
        this.grade = grade;
    }

    public String getId()
    {
        return id;
    }

    public Integer getGrade()
    {
        return grade;
    }

    public void setGrade(Integer grade)
    {
        this.grade = grade;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public Date getDate()
    {
        if (date!=null)
        {
            return (Date) date.clone();            
        }
        else {
            return null;
        }
    }

    public void setDate(Date date)
    {
        if (date!=null)
        {
            this.date = (Date) date.clone();
        }
        else {
            this.date = null;
        }
    }

    public Integer getModuleId()
    {
        return moduleId;
    }

    public Integer getSubModuleId()
    {
        return subModuleId;
    }
    
    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public void setModuleId(Integer moduleId)
    {
        this.moduleId = moduleId;
    }

    public void setSubModuleId(Integer subModuleId)
    {
        this.subModuleId = subModuleId;
    }
    
}

