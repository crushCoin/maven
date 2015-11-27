package com.runningfish.spmk.service.config.internal;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.runningfish.spmk.service.config.service.IArgumentManager;

/**
 * @(#)ArgumentManager.java  2015-10-26
 * @author:svnCrush
 * @Description：redis管理实现类
 * Date:2015-10-26
 * Version:SPMK_D1.0
 */
@Service
public class ArgumentManager implements IArgumentManager
{
	/**
     * 日志
     */
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    /**
     * 内容类型Mapper映射文件命名空间名称
     */
    private static final String MAPPER_NAME_SPACE =
        "com.runningfish.spmk.service.config.";
    
    /**
     * SqlSession对象
     */
    @Resource
    private SqlSession sqlSession;
	
	/**
	 * 方法描述：获取redis端口号和主机地址
	 * @param argumentsName redis参数名
	 */
    @Override
	public String getArgumentsValue(String argumentsName)
	{
		//if(StringUtils.isNotBlank(argumentsName))
		if(null != argumentsName){
			return sqlSession.selectOne(MAPPER_NAME_SPACE+"getArgumentsValue", argumentsName);
		}
		else
		{
			logger.error("获取redis端口号和主机地址,参数[{}] is null",argumentsName);
			return null;
		}
	}

}
