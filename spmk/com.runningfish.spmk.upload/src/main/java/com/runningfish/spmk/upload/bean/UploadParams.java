/**
 * @(#)UploadParams.java 1.0 2015-1-15
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

import org.springframework.web.multipart.MultipartFile;

/**
 * 上传参数
 * Copyright:   Copyright 2000 - 2015 Isoftstone Tech. Co. Ltd. All Rights Reserved.
 * Date:        2015-1-15 下午1:18:02
 * Author:      dengxinjun 42715
 * Version:     EPSP_CAPV1.D1.0.0.0
 * Description: Initialize
 */
public class UploadParams
{
    /**
     * 上传文件
     */
    private MultipartFile file;
    
    /**
     * 最大文件大小
     */
    private long maxSize;

    public MultipartFile getFile()
    {
        return file;
    }

    public void setFile(MultipartFile file)
    {
        this.file = file;
    }

    public long getMaxSize()
    {
        return maxSize;
    }

    public void setMaxSize(long maxSize)
    {
        this.maxSize = maxSize;
    }
    
    @Override
    public String toString()
    {
        return "UploadParams [file=" + file + ", maxSize=" + maxSize + "]";
    }
}
