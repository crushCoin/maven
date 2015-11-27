/**
 * @(#)MessageConstant.java 1.0 2015-1-19
 * @Copyright:  Copyright 2000 - 2015 Isoftstone Tech. Co. Ltd. All Rights Reserved.
 * @Description: 
 * 
 * Modification History:
 * Date:        2015-1-19
 * Author:      dengxinjun 42715
 * Version:     EPSP_CAPV1.D1.0.0.0
 * Description: (Initialize)
 * Reviewer:    
 * Review Date: 
 */
package com.runningfish.spmk.service.config.pojo;

/**
 * 消息常量
 * Copyright:   Copyright 2000 - 2015 Isoftstone Tech. Co. Ltd. All Rights Reserved.
 * Date:        2015-1-19 下午12:31:49
 * Author:      dengxinjun 42715
 * Version:     EPSP_CAPV1.D1.0.0.0
 * Description: Initialize
 */
public final class MessageConstant
{
    /** 消息状态：未读 */
    public static final int MSG_STATUS_UNREAD = 1;
    
    /** 消息状态：已读 */
    public static final int MSG_STATUS_READ = 2;
    
    /** 消息类型：企业审核 */
    public static final int MSG_TYPE_company = 1;
    
    /** 消息类型：数据填报  */
    public static final int MSG_TYPE_DATA = 2;
    
    /** 消息类型：问题反馈 */
    public static final int MSG_TYPE_question = 3;
    
    /** 消息类型：企业纯文本消息 */
    public static final int MSG_TYPE_text = 11;
    
    /** 消息类型：备案管理 */
    public static final int MSG_TYPE_RECORD = 8;
    
    /** 接收人类型：企业用户 */
    public static final int RECEIVE_TYPE_COMPANY = 1;
    
    /** 接收方类型：系统用户 */
    public static final int RECEIVE_TYPE_SYSTEM = 2;
    
    /** 接收方类型：行业协会用户 */
    public static final int RECEIVE_TYPE_GUILD = 3;
}
