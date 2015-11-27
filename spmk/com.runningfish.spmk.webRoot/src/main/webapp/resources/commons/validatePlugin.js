/*
 * jQuery validation 验证类型扩展
 *
 * 扩展的验证类型：用户名，邮政编码，大陆身份证号码，大陆手机号码,电话号码
 * 
 */
 // 邮政编码验证    
jQuery.validator.addMethod("isZipCode", function(value, element) {    
  var zip = /^[0-9]{6}$/;    
  return this.optional(element) || (zip.test(value));    
}, "请输入正确邮编");

//手机号码或电话号码验证
jQuery.validator.addMethod("isMobiOrTel", function(value, element) {    
  var length = value.length;
  var tel = /^(\d{3}-\d{8}|\d{4}-\d{7}|\d{3}-\d{8}-\d{4}|\d{4}-\d{7}-\d{4}|\d{11,15})$/;
  return this.optional(element) || (length == 11 && /^((\(\d{2,3}\))|(\d{3}\-))?1\d{10}$/.test(value)) || (tel.test(value));    
}, "请输入正确联系电话");

// 保留两位小数点
jQuery.validator.addMethod("preciseTow", function(value, element) {    
	  var reg = /^\d+(\.\d{1,2})?$/;  
	  
	  return this.optional(element) || (reg.test(value));    
	}, "请输入正确的数字");


//保留两位小数点 允许输入负数
jQuery.validator.addMethod("lossTow", function(value, element) {    
	  var reg = /^[-]?\d+(\.\d{1,2})?$/;  
	  
	  return this.optional(element) || (reg.test(value));    
	}, "请输入正确的数字");

// 英文字母验证
jQuery.validator.addMethod("isEn", function(value, element) {    
	var str=/[\u4E00-\u9FA5]|[\uFE30-\uFFA0]/gi;
	var flag = true;
	if(str.test(value)){
		flag = false;
	}
	  return this.optional(element) || (flag);    
	}, "请输入英文字母");


// 汉字验证
jQuery.validator.addMethod("isCn", function(value, element) {    
	  var str =/^[0-9\u4e00-\u9faf]+$/;    
	  return this.optional(element) || (str.test(value));    
	}, "请输入汉字");


// 数字验证
jQuery.validator.addMethod("isNum", function(value, element) {    
	  var str =/^\d+$/;    
	  return this.optional(element) || (str.test(value));    
	},"请输入数字");
	


// 身份证号码验证
jQuery.validator.addMethod("isIdCardNo", function(value, element) { 
  var idCard = /^(\d{6})()?(\d{4})(\d{2})(\d{2})(\d{3})(\w)$/;   
  return this.optional(element) || (idCard.test(value));    
}, "请输入正确的身份证号码!"); 


// 手机号码验证
jQuery.validator.addMethod("isMobile", function(value, element) {    
  var length = value.length;    
  return this.optional(element) || (length == 11 && /^((\(\d{2,3}\))|(\d{3}\-))?1\d{10}$/.test(value));    
}, "请输入正确联系电话");


// 电话号码验证
jQuery.validator.addMethod("isTel", function(value, element) {    
  var tel = /^(\d{0,20}-\d{0,20}|\d{0,20}-\d{0,20}-\d{0,20}|\d{1,20})$/;    
  return this.optional(element) || (tel.test(value));    
}, "请输入正确联系电话")

//电话号码和传真号码验证
jQuery.validator.addMethod("isFax", function(value, element) {    
  //var reg = /((\+)?[0-9]{1,7}-)?((([0-9]{3}-)?\d{8}|([0-9]{4}-)?(\d{7,10}))(-[0-9]{1,4})?)$/;
  var reg = /^(\d{3,4})?-?\d{7,9}(-\d{1,4})?$/; //电话号码的正则表达式 支持分机号
  return this.optional(element) || (reg.test(value));    
}, "请输入正确的电话或者传真号码")


// 用户名字符验证
jQuery.validator.addMethod("userName", function(value, element) {   
	var name = /^[a-zA-Z0-9]{1}([a-zA-Z0-9]|[._]){0,30}$/;
	return this.optional(element) || (name.test(value));    
}, "用户名只能包括英文字母、数字和下划线");   

jQuery.validator.addMethod("cname", function(value, element) {   
	var tname = /^[a-zA-Z0-9]{1}([a-zA-Z0-9]|[._]){0,2000}$/;
	return this.optional(element) || (tname.test(value));    
}, "联系人只能包括中文字、英文字母、数字和下划线");  

//不能以下划线开头结尾的用户名验证
jQuery.validator.addMethod("notLineName", function(value, element) {   
	var tname = /^(?!_)(?!.*?_$)[a-zA-Z0-9_]+$/;
	return this.optional(element) || (tname.test(value));    
}, "用户名只能包括英文字母、英文字母、数字和下划线,但不能以下划线结尾或开头");  

// IP地址验证
jQuery.validator.addMethod("ip", function(value, element) {    
  return this.optional(element) || /^(([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))\.)(([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))\.){2}([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))$/.test(value);    
}, "请填写正确的IP地址！");

// 投诉建议标题验证
jQuery.validator.addMethod("suggesttitle", function(value, element) { 
	var suggest = /^(\w+)|([\u0391-\uFFE5]+)$/;
	return this.optional(element) || (suggest.test(value)); 
}, "只允许汉字、英文字母、数字");

//请输入有效的字符（不能还有非法字符）
jQuery.validator.addMethod("isillegalchar", function(value, element) {    
	var suggest = /^[^@\/\'\\\"#$%&\^\*\(\)\[\]\{\}\<\>\~\`\·\（\）]+$/;
	return this.optional(element) || (suggest.test(value));    
}, "请输入正确的字符");

//验证企业名不能包含特殊字符 除中英文括弧
jQuery.validator.addMethod("isCompanyName", function(value, element) {    
	var suggest = /^[^`~!@#$%^&*_+——=<>?:;“”"{},.，、。\|\/;'[\]]+$/;
	return this.optional(element) || (suggest.test(value));    
}, "请输入正确的企业名称");
//验证企业机构码只能 由英文数字组成
jQuery.validator.addMethod("isCompanyCode", function(value, element) {    
	var suggest = /^[A-Za-z0-9]+$/;
	return this.optional(element) || (suggest.test(value));    
}, "请输入正确的企业机构码");

jQuery.validator.addMethod("isEmail", function(value, element) {    
	var suggest = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
	return this.optional(element) || (suggest.test(value));    
}, "请输入正确的邮箱");




function isIdCardNo(num){ 
　// if (isNaN(num)) {alert("输入的不是数字！"); return false;}
　　 var len = num.length, re; 
　　 if (len == 15) 
　　 re = new RegExp(/^(\d{6})()?(\d{2})(\d{2})(\d{2})(\d{2})(\w)$/); 
　　 else if (len == 18) 
　　 re = new RegExp(/^(\d{6})()?(\d{4})(\d{2})(\d{2})(\d{3})(\w)$/); 
　　 else {alert("输入的数字位数不对！"); return false;} 
　　 var a = num.match(re); 
　　 if (a != null) 
　　 { 
　　 if (len==15) 
　　 { 
　　 var D = new Date("19"+a[3]+"/"+a[4]+"/"+a[5]); 
　　 var B = D.getYear()==a[3]&&(D.getMonth()+1)==a[4]&&D.getDate()==a[5]; 
　　 } 
　　 else 
　　 { 
　　 var D = new Date(a[3]+"/"+a[4]+"/"+a[5]); 
　　 var B = D.getFullYear()==a[3]&&(D.getMonth()+1)==a[4]&&D.getDate()==a[5]; 
　　 } 
　　 if (!B) {alert("输入的身份证号 "+ a[0] +" 里出生日期不对！"); return false;} 
　　 } 
　　  if(!re.test(num)){alert("身份证最后一位只能是数字和字母!");return false;}
　　  
　　 return true; 
} 

