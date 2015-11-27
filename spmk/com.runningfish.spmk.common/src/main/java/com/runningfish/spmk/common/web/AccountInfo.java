/**
 * @(#)UserInfo.java 1.0 2015-1-15
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

import java.io.Serializable;
import java.util.Map;

@SuppressWarnings("serial")
public class AccountInfo implements Serializable
{
    private Long accountId;
    
    private String accountName;
    
    private String name;
    
    private Long userId;
    
    private Long organizationId;
    
    private String organizationName;
    
    private Map attributes;
    
    private int accountType;
    
    private Long structureId;
    
    private boolean isHangLead;
    
    private Integer companyId;//企业id
    
    private Integer companyStatus;//行业协会状态
    
    private Integer guildId;//行业协会id
    
    private Integer guildStatus;//行业协会状态
    
    public Integer getGuildId()
    {
        return guildId;
    }

    public void setGuildId(Integer guildId)
    {
        this.guildId = guildId;
    }

    public Integer getGuildStatus()
    {
        return guildStatus;
    }

    public void setGuildStatus(Integer guildStatus)
    {
        this.guildStatus = guildStatus;
    }

    public Integer getCompanyId()
    {
        return companyId;
    }
    
    public void setCompanyId(Integer companyId)
    {
        this.companyId = companyId;
    }
    
    public Integer getCompanyStatus()
    {
        return companyStatus;
    }

    public void setCompanyStatus(Integer companyStatus)
    {
        this.companyStatus = companyStatus;
    }

    public Long getAccountId()
    {
        return accountId;
    }
    
    public void setAccountId(Long accountId)
    {
        this.accountId = accountId;
    }
    
    public String getAccountName()
    {
        return accountName;
    }
    
    public void setAccountName(String accountName)
    {
        this.accountName = accountName;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public Long getUserId()
    {
        return userId;
    }
    
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }
    
    public Long getOrganizationId()
    {
        return organizationId;
    }
    
    public void setOrganizationId(Long organizationId)
    {
        this.organizationId = organizationId;
    }
    
    public String getOrganizationName()
    {
        return organizationName;
    }
    
    public void setOrganizationName(String organizationName)
    {
        this.organizationName = organizationName;
    }
    
    public Map getAttributes()
    {
        return attributes;
    }
    
    public void setAttributes(Map attributes)
    {
        this.attributes = attributes;
    }
    
    public int getAccountType()
    {
        return accountType;
    }
    
    public void setAccountType(int accountType)
    {
        this.accountType = accountType;
    }
    
    public Long getStructureId()
    {
        return structureId;
    }
    
    public void setStructureId(Long structureId)
    {
        this.structureId = structureId;
    }
    
    public boolean isHangLead()
    {
        return isHangLead;
    }
    
    public void setHangLead(boolean isHangLead)
    {
        this.isHangLead = isHangLead;
    }
    
}
