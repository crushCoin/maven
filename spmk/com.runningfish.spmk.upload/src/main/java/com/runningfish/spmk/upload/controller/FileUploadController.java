package com.runningfish.spmk.upload.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.runningfish.spmk.common.util.CommonUtils;
import com.runningfish.spmk.common.util.JsonUtils;
import com.runningfish.spmk.common.web.BaseController;
import com.runningfish.spmk.upload.bean.UploadConstant;
import com.runningfish.spmk.upload.bean.UploadParams;
import com.runningfish.spmk.upload.bean.UploadResponseBody;

/**
 * 文件上传控制器
 * Copyright:   Copyright 2000 - 2015 Isoftstone Tech. Co. Ltd. All Rights Reserved.
 * Date:        2015-1-15 下午1:02:14
 * Author:      dengxinjun 42715
 * Version:     EPSP_CAPV1.D1.0.0.0
 * Description: Initialize
 */
@Controller
public class FileUploadController extends BaseController
{
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    //@Resource
    //private Storager storageService;
    
    
    /*** 允许上传的文件扩展名 */
    private static final Map<String, String> extMap =
        new HashMap<String, String>();
    static
    {
        extMap.put("image", "gif,jpg,jpeg,png,bmp");
        extMap.put("flash", "swf,flv");
        extMap.put("media", "mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
        extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");
    }
    
    /**
     * 文件下载
     * @param fp 文件下载路径（绝对路径）
     * @param fn 文件下载名称（带后缀）
     */
    @SuppressWarnings({"rawtypes", "unchecked" })
    @RequestMapping
    public ResponseEntity<?> download(String fp, String fn,
        HttpServletRequest request)
    {
        logger.info("进入文件下载[fp=" + fp + ", fn=" + fn + "]");
        /*try
        {
            // 文件路径转码
            if (!StringUtils.isBlank(fp))
            {
                fp = new String(fp.getBytes("ISO-8859-1"), "UTF-8");
            }
            // 文件名称转码
            if (!StringUtils.isBlank(fn))
            {
                fn = new String(fn.getBytes("ISO-8859-1"), "UTF-8");
            }
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
            logger.error("文件下载出错啦：" + e.getMessage(), e);
        }*/
        // 响应实体
        ResponseEntity<?> responseEntity = null;
        // 输入流
        InputStream is = null;
        try
        {
            // 如果下载路径为空，则直接返回
            if (StringUtils.isBlank(fp))
            {
                logger.info("fp argument is empty");
                responseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            else
            {
                // 构造URL连接
                URL url = new URL(fp);
                URLConnection conn = url.openConnection();
                // 设置连接
                conn.setConnectTimeout(3000);
                conn.setReadTimeout(5000);
                // 获得输入流
                is = conn.getInputStream();
                
                // 如果文件名为空，则取当前日期时间命名（格式：yyyyMMddHHmmss）
                if (StringUtils.isBlank(fn))
                {
                    // 从文件路径中获得文件扩展名
                    String fileExtName = getFileExt(fp);
                    SimpleDateFormat sdf =
                        new SimpleDateFormat("yyyyMMddHHmmss");
                    fn = sdf.format(new Date()) + "." + fileExtName;
                }
                // 文件名转码
                fn = new String(fn.getBytes("gb2312"), "ISO-8859-1");
                
                // 设置Http头
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.setContentDispositionFormData("attachment", fn);
                
                // 以字节输出
                responseEntity =
                    new ResponseEntity<byte[]>(
                        FileCopyUtils.copyToByteArray(is), headers,
                        HttpStatus.OK);
            }
        }
        catch (FileNotFoundException e)
        {
            responseEntity =
                new ResponseEntity<String>("File Not Found",
                    HttpStatus.NOT_FOUND);
            e.printStackTrace();
            logger.error("文件下载出错啦：" + e.getMessage(), e);
        }
        catch (Exception e)
        {
            responseEntity =
                new ResponseEntity("Internal Server Error",
                    HttpStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
            logger.error("文件下载出错啦：" + e.getMessage(), e);
        }
        finally
        {
            if (is != null)
            {
                try
                {
                    is.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                    logger.error("文件下载出错啦：" + e.getMessage(), e);
                }
            }
        }
        return responseEntity;
    }
    
    /**
     * 文件上传
     * @param uploadParams 文件上传参数
     * @param request
     * @param response
     */
    @RequestMapping
    public ResponseEntity<String> fileUpload(UploadParams uploadParams,
        HttpServletRequest request, HttpServletResponse response)
    {
        // 上传完成返回消息对象
        UploadResponseBody uploadBody = new UploadResponseBody();
        try
        {
            // 获得上传文件
            MultipartFile uploadFile = uploadParams.getFile();
            // 检查是否上传了文件
            if (uploadFile == null)
            {
                // 设置返回消息
                uploadBody.setStatus(false);
                uploadBody.setMessage("请选择文件");
                
                logger.error("文件上传失败：" + uploadBody.toString());
                return setResponse(uploadBody, HttpStatus.OK);
            }
            // 检查文件大小
            if (uploadParams.getMaxSize() > 0)
            {
                if (uploadFile.getSize() > uploadParams.getMaxSize())
                {
                    // 设置返回消息
                    uploadBody.setStatus(false);
                    uploadBody.setMessage("上传文件大小超过限制");
                    
                    logger.error("文件上传失败：" + uploadBody.toString());
                    return setResponse(uploadBody, HttpStatus.OK);
                }
            }
            // 保存文件
            // uploadBody = this.saveFile(uploadFile, request);
            
            // 同步文件
            uploadBody =
                this.syncFile(uploadFile.getInputStream(),
                    uploadFile.getOriginalFilename());
            
            uploadBody.setFileSize(uploadFile.getSize());
            
            logger.info("文件上传成功：" + uploadBody.toString());
            return setResponse(uploadBody, HttpStatus.OK);
        }
        catch (Exception e)
        {
            // 设置返回消息
            uploadBody.setStatus(false);
            uploadBody.setMessage("文件上传失败");
            
            e.printStackTrace();
            logger.error("文件上传出错啦：" + e.getMessage(), e);
            
            return setResponse(uploadBody, HttpStatus.OK);
        }
    }
    
    /**
     * Kindeditor上传方法
     * @param imgFile 上传文件名称
     * @param dir 上传类型（image、flash、media、file）
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping
    public void editorUpload(@RequestParam("imgFile")
    MultipartFile imgFile, String dir, HttpServletRequest request,
        HttpServletResponse response)
    {
        // 输出消息Map
        Map<String, Object> msg = null;
        try
        {
            if (imgFile == null)
            {
                msg = getError("请选择文件");
                logger.error("文件上传失败：" + JsonUtils.toJson(msg));
                
                JsonUtils.toJson(response, msg);
                return;
            }
            // 如果上传类型为空，则默认为图片
            if (dir == null)
            {
                dir = "image";
            }
            // 检查扩展名
            String fileExtName =
                getFileExt(imgFile.getOriginalFilename()).toLowerCase();
            if (!Arrays.<String> asList(extMap.get(dir).split(","))
                .contains(fileExtName))
            {
                msg =
                    getError(MessageFormat.format("上传文件扩展名是不允许的扩展名，只允许{0}格式。",
                        extMap.get(dir)));
                logger.error("文件上传失败：" + JsonUtils.toJson(msg));
                
                JsonUtils.toJson(response, msg);
                return;
            }
            // 同步文件
            UploadResponseBody uploadBody =
                this.syncFile(imgFile.getInputStream(),
                    imgFile.getOriginalFilename());
            // 设置返回消息
            msg = new HashMap<String, Object>();
            msg.put("error", 0);
            msg.put("url", uploadBody.getFilePath());
            
            logger.info("文件上传成功：" + JsonUtils.toJson(msg));
            
            JsonUtils.toJson(response, msg);
        }
        catch (Exception e)
        {
            logger.error("文件上传出错啦：" + e.getMessage(), e);
            JsonUtils.toJson(response, getError("文件上传失败"));
        }
    }
    
    /**
     * 获得错误消息Map，用于Kindeditor上传
     * @param message 消息
     * @return
     */
    private Map<String, Object> getError(String message)
    {
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("error", 1);
        msg.put("message", message);
        return msg;
    }
    
    /**
     * 设置返回结果
     * @param uploadResponseBody
     * @param status
     * @return
     */
    private ResponseEntity<String> setResponse(
        UploadResponseBody uploadResponseBody, HttpStatus status)
    {
        String json = JsonUtils.toJson(uploadResponseBody);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "text/html; charset=UTF-8");
        
        return new ResponseEntity<String>(json, headers, status);
    }
    
    /**
     * 获取系统配置项值
     * @param key 配置Key
     * @return
     */
    private Object getSysConfigVal(String key)
    {
        //Object obj = sysConfigManager.getValue(key);
       // if (obj == null)
        //{
            logger.info("FileUploadController获取系统配置项为空：Key=" + key);
        //}
        return null;
    }
    
    /**
     * 同步文件到文件服务器
     * @param input 输入流
     * @param filename 文件名（输入流读取的文件名）
     * @return
     * @throws Exception
     */
    public UploadResponseBody syncFile(InputStream input, String filename)
        throws Exception
    {
        // 获取DFS主机地址
        //String host = (String) getSysConfigVal(Constant.DFS_HOST);
        // 保存资源
        //String path = storageService.saveFile(input, filename);
        // 组装资源全路径
        StringBuffer pathBuffer = new StringBuffer();
       // pathBuffer.append(host).append(path);
        
        // 设置返回消息
        UploadResponseBody body = new UploadResponseBody();
        body.setStatus(true);
        body.setMessage("上传成功");
        body.setFileId(CommonUtils.getUUID(null));
        body.setFilePath(pathBuffer.toString());
        body.setOriginalFileName(filename);
        
        return body;
    }
    
    /**
     * 保存文件，本地服务器路径
     * @param uploadFile 上传文件
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unused")
    private UploadResponseBody saveFile(MultipartFile uploadFile,
        HttpServletRequest request) throws Exception
    {
        File file = null;
        UploadResponseBody body = new UploadResponseBody();
        try
        {
            // 文件上传路径
            String uploadPath =
                request.getSession()
                    .getServletContext()
                    .getRealPath(UploadConstant.UPLOAD_ROOT_PATH);
            // 文件URL
            String fileUrl =
                request.getContextPath() + UploadConstant.UPLOAD_ROOT_PATH;
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String ymd = sdf.format(new Date());
            uploadPath += "/" + ymd;
            fileUrl += "/" + ymd;
            
            // 文件扩展名
            String extName = this.getFileExt(uploadFile.getOriginalFilename());
            // 新文件名称
            String uuid = CommonUtils.getUUID(null);
            String newFileName = uuid + "." + extName;
            
            // 检查上传目录
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists())
            {
                if (!uploadDir.mkdirs())
                {
                    throw new Exception("上传目录创建失败");
                }
            }
            // 检查目录写权限
            if (!uploadDir.canWrite())
            {
                throw new Exception("上传目录没有写权限");
            }
            // 文件上传
            file = new File(uploadPath, newFileName);
            uploadFile.transferTo(file);
            
            body.setStatus(true);
            body.setMessage("上传成功");
            body.setFileId(uuid);
            body.setFilePath(fileUrl + "/" + newFileName);
            body.setOriginalFileName(uploadFile.getOriginalFilename());
            body.setFileSize(uploadFile.getSize());
        }
        catch (Exception e)
        {
            // 删除上传文件
            deleteFile(file);
            throw e;
        }
        return body;
    }
    
    /**
     * 获得文件扩展名
     * @param fileName 文件名称
     * @return
     * @throws Exception
     */
    private String getFileExt(String fileName)
    {
        // 获得文件扩展名
        String fileExt = null;
        if (fileName != null && fileName.lastIndexOf(".") != -1)
        {
            fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        return fileExt;
    }
    
    /**
     * 删除文件
     * @param file 需删除的文件
     * @return true 成功、false 失败
     */
    private boolean deleteFile(File file)
    {
        boolean isSuccess = false;
        if (file != null && file.exists())
        {
            isSuccess = file.delete();
        }
        return isSuccess;
    }
}
