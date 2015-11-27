/**
 * @(#)UploadResponseBody.java 1.0 2015-1-15
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
package com.runningfish.spmk.upload.bean;

import java.io.Serializable;

/**
 * 上传返回消息对象
 * Copyright:   Copyright 2000 - 2015 Isoftstone Tech. Co. Ltd. All Rights Reserved.
 * Date:        2015-1-15 下午1:21:06
 * Author:      dengxinjun 42715
 * Version:     EPSP_CAPV1.D1.0.0.0
 * Description: Initialize
 */
public class UploadResponseBody implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /*** 返回状态：true成功、false失败 */
    private Boolean status;
    
    /*** 返回消息 */
    private String message;
    
    /*** 文件ID（CMS系统存储ID） */
    private String fileId;
    
    /*** 文件路径 */
    private String filePath;
    
    /*** 文件原名称 */
    private String originalFileName;
    
    /*** 文件大小（单位：字节） */
    private Long fileSize;

    public Boolean getStatus()
    {
        return status;
    }

    public void setStatus(Boolean status)
    {
        this.status = status;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getFileId()
    {
        return fileId;
    }

    public void setFileId(String fileId)
    {
        this.fileId = fileId;
    }

    public String getFilePath()
    {
        return filePath;
    }

    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }

    public String getOriginalFileName()
    {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName)
    {
        this.originalFileName = originalFileName;
    }

    public Long getFileSize()
    {
        return fileSize;
    }

    public void setFileSize(Long fileSize)
    {
        this.fileSize = fileSize;
    }
    
    @Override
    public String toString()
    {
        return "UploadResponseBody [status=" + status + ", message=" + message
            + ", fileId=" + fileId + ", filePath=" + filePath
            + ", originalFileName=" + originalFileName + ", fileSize="
            + fileSize + "]";
    }
}
