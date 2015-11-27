package com.runningfish.spmk.common;

/**
 * 对外接口服务层，返回状态码
 */
public class ResponseStatus {

      //客户端返回码(100-199)
      public final static String CLIENT_STATUS_EXCEPTION = "100";//客户端其它异常      
      public final static String CLIENT_STATUS_CLIENTPROTOCOL_EXCEPTION = "101";//客户端请求异常
      public final static String CLIENT_STATUS_IO_EXCEPTION = "102";//客户端文件流异常
    
	  //服务端基本状态返回码  (200-300)
	  public final static String STATUS_OK = "200";//成功
	  public final static String STATUS_FAIL = "201";//失败
      public final static String STATUS_SYSTEM_ERROR = "202"; //服务端系统异常错误
      public final static String STATUS_UNKNOWN_ERROR = "203";//服务系统未知错误 	  
      public final static String STATUS_PARAMS_NULL = "204"; //输入参数为空
      public final static String STATUS_DATAFORMAT_EXCEPTION="205";//数据格式化异常
      public final static String STATUS_RESULT_NULL = "206"; //返回结果值为空
	  
	  //账号管理返回码(301-400)
      public final static String ACCOUNT_STATUS_NULLPARAMS = "301"; //账号或密码为空
      public final static String ACCOUNT_STATUS_UNKNOWN = "302"; //帐号不存在
      public final static String ACCOUNT_STATUS_NOTACTIVATED = "303";//帐号未激活
      public final static String ACCOUNT_STATUS_FREEZEEXCED = "304"; //帐号已冻结    
      public final static String ACCOUNT_STATUS_PASS = "305"; //密码错误        
      public final static String ACCOUNT_STATUS_AUTHENTICATION_ERROR = "306";//用户名密码错误         
      public final static String ACCOUNT_STATUS_UNIQCHECK_FAIL= "307";//账号已经存在
      public final static String ACCOUNT_STATUS_OLDPASS_ERROR = "308"; //旧密码错误    
      public final static String ACCOUNT_STATUS_EMAIL_NULL = "309"; //邮箱不能为空     
      public final static String ACCOUNT_STATUS_EMAIL_EXIST = "310"; //邮箱已经被注册          
      public final static String ACCOUNT_STATUS_EMAIL_SUCCESS = "311"; //邮件发送成功
      public final static String ACCOUNT_STATUS_EMAIL_FAILURE = "312"; //邮件发送失败
      public final static String ACCOUNT_STATUS_PASSRESET_RESETCODE_ERROR = "313"; //重置码错误
      public final static String ACCOUNT_STATUS_PASSRESET_LIMITHOURS_ERROR = "314"; //密码重置时限错误
      public final static String ACCOUNT_STATUS_PASSRESET_LIMITHOURS_OVER = "315"; //密码重置时限超时
      public final static String ACCOUNT_STATUS_PARAMS_PASS_CHINESE= "316"; //密码参数有中文字符
      public final static String ACCOUNT_STATUS_VALIDATECODE_NULL = "317"; //验证码为空
      public final static String ACCOUNT_STATUS_VALIDATECODE_INCONFORMITY = "318";//验证码不一致
      public final static String ACCOUNT_STATUS_MOBIPHONE_NULL = "319";//手机号码为空
      public final static String ACCOUNT_STATUS_MOBIPHONE_FORMAT_ERROR = "320";//手机号码格式错误
      public final static String ACCOUNT_STATUS_MOBIPHONE_EXIST = "321";//手机号码已注册
	  
	  //角色管理返回码(401-500)
      public final static String ROLE_STATUS_UNKNOWN = "401"; //角色不存在
       
	  
}
