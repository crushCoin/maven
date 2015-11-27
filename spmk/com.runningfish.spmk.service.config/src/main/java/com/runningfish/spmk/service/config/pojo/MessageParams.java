/**
 * @(#)MessageParams.java 1.0 2015-1-15
 * @Copyright:  Copyright 2000 - 2015 Isoftstone Tech. Co. Ltd. All Rights Reserved.
 * @Description: 
 * 
 * Modification History:
 * Date:        2015-1-15
 * Author:      dengxinjun 42715
 * Version:     EPSP_CAPV1.D1.0.0.0
 * Description: (Initialize)
 * Reviewer:    
 * Review Date: 
 */
package com.runningfish.spmk.service.config.pojo;

import com.runningfish.spmk.common.Pagination;


/**
 * 消息查询参数
 * Copyright:   Copyright 2000 - 2015 Isoftstone Tech. Co. Ltd. All Rights Reserved.
 * Date:        2015-1-15 下午8:55:46
 * Author:      dengxinjun 42715
 * Version:     EPSP_CAPV1.D1.0.0.0
 * Description: Initialize
 */
public class MessageParams extends Pagination
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Long recId; //接收人编号
    
    private Integer msgStatus; //消息状态 1：未读  2：已读
    
    private Integer msgType; //消息类型 1：企业审核  2：数据填报  3：问题反馈
    
    private Integer recType; //接收人类型 接收人类型，1：企业用户、2：系统用户 3:行业协会用户
    
    private String msgContent; //消息内容
    
    public Long getRecId()
    {
        return recId;
    }
    
    public void setRecId(Long recId)
    {
        this.recId = recId;
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
    
    public String getMsgContent()
    {
        return msgContent;
    }
    
    public void setMsgContent(String msgContent)
    {
        this.msgContent = msgContent;
    }
    
    @Override
    public String toString()
    {
        return "MessageParams [recId=" + recId + ", msgStatus=" + msgStatus
            + ", msgType=" + msgType + ", recType=" + recType + ", msgContent="
            + msgContent + "]";
    }
}
