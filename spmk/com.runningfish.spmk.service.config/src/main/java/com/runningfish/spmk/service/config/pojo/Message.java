package com.runningfish.spmk.service.config.pojo;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Long msgId; //消息编号
    
    private Long recId; //接收人编号
    
    private String msgContent; //消息内容
    
    private Long recProjectId; //接收项目编号：企业审核、数据填报、问题反馈编号
    
    private String linkUrl; //链接地址
    
    private String appUrl; //app链接地址
    
    private Integer msgStatus; //消息状态 1：未读  2：已读
    
    private Integer msgType; //消息类型 1：企业审核  2：数据填报  3：问题反馈 4：资讯审核 5：企业月报 6：企业季报 7：协会审核8:备案分配
    
    private Integer recType; //接收人类型 接收人类型，1：企业用户、2：系统用户
    
    private Date readTime; //阅读时间
    
    private Date recTime; //接收时间
    
    private Date updateTime; //修改时间
    
    private Integer isdel; //是否删除，0：否、1：是
    
    private String companyMsg;//企业纯文本消息
    
    public String getCompanyMsg()
    {
        return companyMsg;
    }
    
    public void setCompanyMsg(String companyMsg)
    {
        this.companyMsg = companyMsg;
    }
    
    public Long getMsgId()
    {
        return msgId;
    }
    
    public void setMsgId(Long msgId)
    {
        this.msgId = msgId;
    }
    
    public Long getRecId()
    {
        return recId;
    }
    
    public void setRecId(Long recId)
    {
        this.recId = recId;
    }
    
    public String getMsgContent()
    {
        return msgContent;
    }
    
    public void setMsgContent(String msgContent)
    {
        this.msgContent = msgContent;
    }
    
    public Long getRecProjectId()
    {
        return recProjectId;
    }
    
    public void setRecProjectId(Long recProjectId)
    {
        this.recProjectId = recProjectId;
    }
    
    public String getLinkUrl()
    {
        return linkUrl;
    }
    
    public void setLinkUrl(String linkUrl)
    {
        this.linkUrl = linkUrl;
    }
    
    public String getAppUrl()
    {
        return appUrl;
    }
    
    public void setAppUrl(String appUrl)
    {
        this.appUrl = appUrl;
    }
    
    public Integer getMsgStatus()
    {
        return msgStatus;
    }
    
    public void setMsgStatus(Integer msgStatus)
    {
        this.msgStatus = msgStatus;
    }
    
    public Integer getMsgType()
    {
        return msgType;
    }
    
    public void setMsgType(Integer msgType)
    {
        this.msgType = msgType;
    }
    
    public Integer getRecType()
    {
        return recType;
    }
    
    public void setRecType(Integer recType)
    {
        this.recType = recType;
    }
    
    public Date getReadTime()
    {
        if (readTime != null)
        {
            return (Date) readTime.clone();
        }
        return null;
    }
    
    public void setReadTime(Date readTime)
    {
        if (readTime != null)
        {
            this.readTime = (Date) readTime.clone();
        }
        else
        {
            this.readTime = null;
        }
    }
    
    public Date getRecTime()
    {
        if (recTime != null)
        {
            return (Date) recTime.clone();
        }
        return null;
    }
    
    public void setRecTime(Date recTime)
    {
        if (recTime != null)
        {
            this.recTime = (Date) recTime.clone();
        }
        else
        {
            this.recTime = null;
        }
    }
    
    public Date getUpdateTime()
    {
        if (updateTime != null)
        {
            return (Date) updateTime.clone();
        }
        return null;
    }
    
    public void setUpdateTime(Date updateTime)
    {
        if (updateTime != null)
        {
            this.updateTime = (Date) updateTime.clone();
        }
        else
        {
            this.updateTime = null;
        }
    }
    
    public Integer getIsdel()
    {
        return isdel;
    }
    
    public void setIsdel(Integer isdel)
    {
        this.isdel = isdel;
    }
    
    @Override
    public String toString()
    {
        return "Message [msgId=" + msgId + ", recId=" + recId + ", msgContent="
            + msgContent + ", recProjectId=" + recProjectId + ", linkUrl="
            + linkUrl + ", appUrl=" + appUrl + ", msgStatus=" + msgStatus
            + ", msgType=" + msgType + ", recType=" + recType + ", readTime="
            + readTime + ", recTime=" + recTime + ", updateTime=" + updateTime
            + ", isdel=" + isdel + "]";
    }
}
