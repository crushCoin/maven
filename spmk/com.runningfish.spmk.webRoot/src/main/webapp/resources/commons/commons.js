//百度统计  www.imdg.gov.cn 访问统计
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "//hm.baidu.com/hm.js?6831047f9b041de13997b4495127d0cf";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();

/**
 * 设置全局AJAX默认选项
 */
$.ajaxSetup({
	 cache: false,
　　　　// 请求成功后触发
　　　　success: function (data) { 
		// alert('success invoke!' + data + '<br/>');
	 },
　　　　// 请求失败遇到异常触发
　　　　error: function (xhr, status, e) { 
		// alert('error invoke! status:' + status+'<br/>');
		if(status=='parsererror'){
			// alert('error invoke! status:' + status+'<br/>');
			window.parent.location = '/epsp/outSession.html';
		}
	 },
　　　　// 完成请求后触发。即在success或error触发后触发
　　　　complete: function (xhr, status) { 
		// alert('complete invoke! status:' + status+'<br/>');
	 }
});

/**
 * 获取请求参数
 */
(function($) {
    $.getUrlParam = function(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null)
            return decodeURIComponent(r[2]);
        return null;
    }
})(jQuery);

/**
 * 设置菜单高亮
 * 
 * @param objId
 *            菜单ID
 */
function setMenuHighlight(objId) {
	var $menuList = $('#menuList', top.document.body),
		$menu = $('a[objid=' + objId + ']', $menuList);
	if (!$menu.hasClass('menu_click')) {
		$('a', $menuList).removeClass('menu_click');
		$menu.addClass('menu_click');
	}
}

/**
 * 获取当天日期
 * 
 * @returns {String}
 */
function getTodayDate() {
	var d = new Date();
	var year = d.getFullYear();
	var month = d.getMonth() + 1;
	var date = d.getDate();
	var day = d.getDay();
	var curDateTime = year + "-";
	if (month > 9)
		curDateTime = curDateTime + month;
	else
		curDateTime = curDateTime + "0" + month;
	if (date > 9)
		curDateTime = curDateTime + "-" + date;
	else
		curDateTime = curDateTime + "-" + "0" + date;
	return curDateTime;
}
/**
 * 获取三十天之前对日期
 * 
 * @returns {String}
 */
function getPreviousDate() {
	var today = new Date();
	var todayLong = today.getTime();
	var previousMonthLong = todayLong - 2592000000;
	var previousMonth = new Date(previousMonthLong);

	var year = previousMonth.getFullYear();
	var month = previousMonth.getMonth() + 1;
	var date = previousMonth.getDate();
	var curDateTime = year + "-";
	if (month > 9)
		curDateTime = curDateTime + month;
	else
		curDateTime = curDateTime + "0" + month;
	if (date > 9)
		curDateTime = curDateTime + "-" + date;
	else
		curDateTime = curDateTime + "-" + "0" + date;
	return curDateTime;
}
/**
 * 取当前的日期
 */
function curDateTime() {
	var d = new Date();
	var year = d.getFullYear();
	var month = d.getMonth() + 1;
	var date = d.getDate();
	var day = d.getDay();
	var curDateTime = year + "-";
	if (month > 9)
		curDateTime = curDateTime + month;
	else
		curDateTime = curDateTime + "0" + month;
	if (date > 9)
		curDateTime = curDateTime + "-" + date;
	else
		curDateTime = curDateTime + "-" + "0" + date;

	return curDateTime;

}

// 求2个日期相差天数
function daysBetween(DateOne, DateTwo) {
	var OneMonth = DateOne.substring(5, DateOne.lastIndexOf('-'));
	var OneDay = DateOne
			.substring(DateOne.length, DateOne.lastIndexOf('-') + 1);
	var OneYear = DateOne.substring(0, DateOne.indexOf('-'));
	var TwoMonth = DateTwo.substring(5, DateTwo.lastIndexOf('-'));
	var TwoDay = DateTwo
			.substring(DateTwo.length, DateTwo.lastIndexOf('-') + 1);
	var TwoYear = DateTwo.substring(0, DateTwo.indexOf('-'));
	var cha = ((Date.parse(OneMonth + '/' + OneDay + '/' + OneYear) - Date
			.parse(TwoMonth + '/' + TwoDay + '/' + TwoYear)) / 86400000);
	return Math.abs(cha);
}


function TimesBetween(startDate,endDate){

	var date=StrToDate(endDate).getTime()-StrToDate(startDate).getTime()  // 时间差的毫秒数
	var days=date/(24*3600*1000)
	return days ;
}

/*
 * 字符串和date 转换
 */
 function StrToDate  (c_date) {
    var tempArray = c_date.split("-");
    if (tempArray.length != 3) {
        alert("你输入的日期格式不正确,正确的格式:2000-05-01 02:54:12");
        return 0;
    }
    var dateArr = c_date.split(" ");
    var date = null;
    if (dateArr.length == 2) {
        var yymmdd = dateArr[0].split("-");
        var hhmmss = dateArr[1].split(":");
        date = new Date(yymmdd[0], yymmdd[1] - 1, yymmdd[2], hhmmss[0], hhmmss[1], hhmmss[2]);
    } else {
        date = new Date(tempArray[0], tempArray[1] - 1, tempArray[2], 00, 00, 01);
    }
    return date;
}
 
 /**
	 * 格式化时间
	 * 
	 * @param time
	 *            长整型时间
	 * @param fmt
	 *            时间格式，默认：yyyy-MM-dd
	 * @returns
	 */
 function formatTime(time, fmt) {
	if (time == null) {
		return;
	}
	var fmt = fmt ? fmt : 'yyyy-MM-dd';
	var time = new Date(time);
	var z = {
			M: time.getMonth() + 1, 
			d: time.getDate(), 
			h: time.getHours(),
			m: time.getMinutes(),
			s: time.getSeconds()
		};
	fmt = fmt.replace(/(M+|d+|h+|m+|s+)/g, function(v) {
			return ((v.length > 1 ? "0" : "") + eval('z.' + v.slice(-1))).slice(-2);
		});
	return fmt.replace(/(y+)/g, function(v) {
			return time.getFullYear().toString().slice(-v.length);
		});
}

 /**
	 * 获取当前时间，年月日、星期几
	 * 
	 */
 function getNowDate(){
 	var myDate = new Date();
 	var year = myDate.getFullYear();
 	var month = myDate.getMonth()+1;
 	var day = myDate.getDate();
 	var week = new Array("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六")[myDate.getDay()];
 	$('#yearMonth').html(year+"-"+month);
 	$('#day').html(day); 
 	$('#week').html(week); 
 }
 
 /**
	 * 判断天气图片
	 * 
	 */
 function judgingWeather(weatherName) {
	var weatherImg;
 	if(weatherName == '晴'){
 		weatherImg = 'qing';
 	}
	else if(weatherName == '阴'){
 		weatherImg = 'yin';
 	}
 	else if(weatherName == '多云'){
 		weatherImg = 'duoyun';
 	}
 	else if(weatherName == '雾'){
 		weatherImg = 'wu';
 	}
 	else if(weatherName == '霾'){
 		weatherImg = 'mai';
 	}
 	else if(weatherName == '冰雹'){
 		weatherImg = 'bingbao';
 	}
 	else if(weatherName == '雷阵雨'){
 		weatherImg = 'leizhenyu';
 	}
 	else if(weatherName == '阵雨'){
 		weatherImg = 'zhenyu';
 	}
 	else if(weatherName == '小雨'){
 		weatherImg = 'xiaoyu';
 	}
 	else if(weatherName == '中雨'){
 		weatherImg = 'zhongyu';
 	}
 	else if(weatherName == '大雨'){
 		weatherImg = 'dayu';
 	}
 	else if(weatherName == '暴雨'){
 		weatherImg = 'baoyu';
 	}
 	else if(weatherName == '阵雪'){
 		weatherImg = 'zhenxue';
 	}
 	else if(weatherName == '小雪'){
 		weatherImg = 'xiaoxue';
 	}
 	else if(weatherName == '中雪'){
 		weatherImg = 'zhongxue';
 	}
 	else if(weatherName == '大雪'){
 		weatherImg = 'daxue';
 	}
 	else if(weatherName == '雨夹雪'){
 		weatherImg = 'yujiaxue';
 	}
 	else{
 		weatherImg = 'weizhi';
 	}

 	return weatherImg;
 }
 
/**
 * 给 文本域添加 maxLength 属性
 * 
 * @param o
 * @param maxLength
 * @returns {Boolean}
 */
// 给文本域添加 maxLength
// -------------------------------------------------------------------------------------------
// start
 function onmyinput(o,maxLength)
 {
  if(o.value.length>= maxLength)
  {
   if(o.value.length> maxLength)
    o.value = o.value.substring(0,maxLength);
    return false;
  }
  return true;
 }
 function mygetclipdata()
 {
  if(!document.all)
  {
   netscape.security.PrivilegeManager.enablePrivilege('UniversalXPConnect');
   var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);
   var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);
   trans.addDataFlavor('text/unicode');
   var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);
   clip.getData(trans,clip.kGlobalClipboard);
   var str=new Object();
   var strLength=new Object();
   trans.getTransferData("text/unicode",str,strLength); 
  
   if (str)
   str=str.value.QueryInterface(Components.interfaces.nsISupportsString);
   var pastetext;
   if (str)
   pastetext=str.data.substring(0,strLength.value / 2);
   return pastetext;
  }
  else
  {
   return window.clipboardData.getData("Text");
  }
 }
 function mysetclipdata(o)
 {
  if(!document.all)
  {
   netscape.security.PrivilegeManager.enablePrivilege('UniversalXPConnect');
   var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);
   var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);
   trans.addDataFlavor("text/unicode");
   var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);
   str.data=o;
   trans.setTransferData("text/unicode",str,o.length*2);
   var clipid=Components.interfaces.nsIClipboard;
   clip.setData(trans,null,clipid.kGlobalClipboard);
  }
  else
  {
   window.clipboardData.setData("Text",o);
  }
 }
 function onmypaste(o, maxLength)
 {	 
  var nMaxLen=o.getAttribute? parseInt(maxLength):"";
  if(!document.all)
  {
	  return false;
  }
  else
  {
   if(document.selection.createRange().text.length>0)
   {
	   
    var ovalueandclipboarddata = o.value +window.clipboardData.getData("Text");
    if(o.getAttribute && ovalueandclipboarddata.length-document.selection.createRange().text.length>nMaxLen)
    {
     if(window.clipboardData.getData("Text").substring(0,document.selection.createRange().text.length+nMaxLen-o.value.length)!="")
      window.clipboardData.setData("Text",window.clipboardData.getData("Text").substring(0,document.selection.createRange().text.length+nMaxLen-o.value.length).replace(/(\s*$)/g, ""));
     else
      return false;
    }
   }
   else
   {
    var ovalueandclipboarddata = o.value +window.clipboardData.getData("Text");
    if(o.getAttribute && ovalueandclipboarddata.length>nMaxLen)
    {
     if(ovalueandclipboarddata.substring(0,nMaxLen-o.value.length)!=""){
         window.clipboardData.setData("Text",ovalueandclipboarddata.substring(0,nMaxLen-o.value.length).replace(/(\s*$)/g, ""));// 去除尾部空格;
      }
     else{
        return false;
      }
    }
   }
   return true;
  }

 }
 function onmykeypress(o, maxLength)
 {
  if(!document.all)
  {
   var nMaxLen=o.getAttribute? parseInt(maxLength):"";

   if(onmykeypress.caller.arguments[0].ctrlKey == true)
   {
    if(onmykeypress.caller.arguments[0].which==118)
    {

     if(o.selectionStart<o.selectionEnd)
     {
      var ovalueandclipboarddata = o.value + mygetclipdata();
      if(o.getAttribute && (ovalueandclipboarddata.length-o.selectionEnd + o.selectionStart>nMaxLen))
      {
       if(mygetclipdata().substring(0,o.selectionEnd - o.selectionStart+nMaxLen-o.value.length)!="")
        mysetclipdata(mygetclipdata().substring(0,o.selectionEnd - o.selectionStart+nMaxLen-o.value.length));
       else
        return false;
      }
     }
     else
     {
      var ovalueandclipboarddata = o.value +mygetclipdata();
      if(o.getAttribute && ovalueandclipboarddata.length>nMaxLen)
      {
       if(ovalueandclipboarddata.substring(0,nMaxLen-o.value.length)!="")
        mysetclipdata(ovalueandclipboarddata.substring(0,nMaxLen-o.value.length));
       else
        return false;
      }
     }
     return true;

    }
   }


   if(onmykeypress.caller.arguments[0].which==0 || onmykeypress.caller.arguments[0].which==8)
    return true;
   if(o.value.length>= maxLength)
   {
    if(o.selectionStart<o.selectionEnd)
     return true;
    if(o.value.length> maxLength)
     o.value = o.value.substring(0, maxLength);
    return false;
   }
   else
    return true;
  
  }
  else
  {
   if(document.selection.createRange().text.length>0)
    return true;
   if(o.value.length>= maxLength)
    return false;
   else
    return true;
  }
 }
 window.onload = init_MaxLength;
 function init_MaxLength () 
 {
	
   var textAreaObj = document.getElementsByTagName("textarea");
   var maxLength;
   for (var i = 0; i < textAreaObj.length; i++) {
       maxLength = textAreaObj[i].getAttribute("maxLength") == null ? 0 : textAreaObj[i].getAttribute("maxLength");
       if (maxLength == 0) continue;
           textAreaObj[i].onpaste = function(){ return onmypaste(this,maxLength)};
           textAreaObj[i].onkeypress = function(){ return onmykeypress(this,maxLength)};
           textAreaObj[i].onpropertychange = function(){ onmyinput(this,maxLength)};
         if(!document.all){
           textAreaObj[i].setAttribute("oninput","return onmyinput(this," + maxLength + ")");
         }
   }
 }
// 给文本域添加 maxLength
// -------------------------------------------------------------------------------------------

 function getSafeString(str){
	 if(str==null || str=='') return "";
	 var nullObj = $("#nullObj");
	 if( !(nullObj.length >0) ){
	     // object not exist
	 	$("body").append('<p id="nullObj" style="display:none;"></p>');
	 	nullObj = $("#nullObj");
	 }
	 var result =  nullObj.text(str).html();
	 nullObj.html('');// reset dom text node
	 return result;
}
// 只能输入数字
function myKeyDown(event) {
	var k = event.keyCode;
	if (event.shiftKey && (k > 47 && k < 58)) {
		event.returnValue = false;
	} else if ((k == 46) || (k == 8) || (k >= 48 && k <= 57) || (k >= 96 && k <= 105)
			|| (k >= 37 && k <= 40)) {
	} else if (k == 13) {
		window.event.keyCode = 9;
	} else {
		event.returnValue = false;
	}
}

// 设置内容iframe高度
function ifrHeight(){
	var iframe = parent.document.getElementById("main_iframe");
	if (undefined == iframe || null == iframe){return;};

	var iDoc = iframe.contentWindow.document;
	ifrHeight.scrollTop = getScrollTop(parent);

	var iframeH = Math.max(iDoc.documentElement.scrollHeight, iDoc.body.scrollHeight);
	iframeH = iframeH > 300 ? iframeH : 300;
	$(iframe).height(iframeH);

	var leftH = $(".main_left", top.document).height();
	iframeH = iframeH > leftH ? iframeH : leftH;
	$(".container", top.document).css("height", iframeH);

	// parent.scrollBy(0, ifrHeight.scrollTop);
	ifrHeight.scrollTop = 0;
	function getScrollTop(w) {
	    return ('pageYOffset' in w) ? w.pageYOffset
	        : w.document.compatMode === "BackCompat"
	        && w.document.body.scrollTop
	        || w.document.documentElement.scrollTop ;
	}
};

/**
 * 列表排序方法
 * 
 * @param thisObj
 *            当前点击排序字段对象
 * @param sortColumn
 *            要排序的字段
 */
function sortChageValue(thisObj,sortColumn){
	var $this = $(thisObj);
	var $span = $this.children('span');
	$this.parents('tr').find('.spanAsc, .spanDesc').not($span).remove();
	
	if ($span.size() == 0) {
		$span = $('<span/>').addClass('spanDesc').appendTo($this);
		$('#order').val('DESC');
	} else {
		if ($span.hasClass('spanDesc')) {
			$('#order').val('ASC');
			$span.removeClass().addClass('spanAsc');
		} else {
			$('#order').val('DESC');
			$span.removeClass().addClass('spanDesc');
		}
	}
	$('#sort').val(sortColumn);
};

/**
 * 判断是否为汉字
 * 
 * @param obj
 * @returns
 */
function fnCheckChineseChar(obj)
{
	var reg = /^[\u0391-\uFFE5]+$/;
	return reg.test(obj);
};

/**
 * 字符截取
 * 
 * @param str
 *            要截取的字符串
 * @param len
 *            截取长度
 * @returns 返回截取值
 */
function fnGetLength(str,len)
{
	var strValue = "";
	var length = 0;
	for(var i=0;i<str.length;i++)
	{
		if(fnCheckChineseChar(str.charAt(i)))
		{
			length++;
		}
		else
		{
			length+=2;
		}
		if(length>=len && str.length>length){
			strValue = str.substring(0,length);
			return strValue+'...';
		}
	}
	return str;
};



/**
 * Confirm
 * 
 * @param content
 *            内容
 * @param title
 *            提示
 * @param callBack
 *            回调函数
 * @param icoCss
 *            图标样式 默认感叹号(问号：question_mark 对号：right_mark)
 * @returns
 * 
 * example： dynamicConfirm("确认审批这条记录？", "提示", function(res){ alert(20); //点击OK
 * res=true 点击 Cancel res=false });
 */
function dynamicConfirm(content, title, callBack, icoCss, okText, cancelText , okCss, cancelCss){	
	var mark = "mark" + Math.random().toString().replace(".",""),
		dialog = $(top.document.createElement("div")).addClass("dialog delete_dialog").attr("mark", mark).attr('type', 'dialog'),
		title = $(top.document.createElement("div")).addClass("dialog_title").html(checkValue(title, "提示")),
		cont = $(top.document.createElement("div")).addClass("delete_content"),
		txt = $(top.document.createElement("div")).addClass(checkValue(icoCss, 'txt')).html(checkValue(content, "")),
		line = $(top.document.createElement("div")).addClass("dialog_line"),
		footer = $(top.document.createElement("div")).addClass("dialog_footer"),
		btnCancel = $(top.document.createElement("button")).addClass(checkValue(cancelCss, "btn-reset NoBtn")).html(checkValue(cancelText, '')),
		btnOk = $(top.document.createElement("button")).addClass(checkValue(okCss, "btn-true YesBtn")).html(checkValue(okText, ''));
	
	btnCancel.click(function(){
		$("div[mark="+ mark +"]", top.document.body).empty().remove();
		callBack(false);
		// 取消锁定键盘
		$(document).off("keydown", offKeydown);
	});
	btnOk.click(function(){
		$("div[mark="+ mark +"]", top.document.body).empty().remove();
		callBack(true);
		// 取消锁定键盘
		$(document).off("keydown", offKeydown);
	});
	
	txt.appendTo(cont);
	btnCancel.appendTo(footer);
	btnOk.appendTo(footer);
	title.appendTo(dialog);
	cont.appendTo(dialog);
	line.appendTo(dialog);
	footer.appendTo(dialog);
	dialog.appendTo($(top.document.body));

	isCoverDiv(mark);
	objectCenter(dialog.show());
	dynamicDivHeight(dialog);

	this.callBack = function(){};
	$(window).resize(function(){
		objectCenter(dialog);
		dynamicCoverHeight($(".overlay", top.document.body));
	});
	// 弹出层不锁定键盘
	dialog.on("keydown", onKeydown);
};

/**
 * dynamicConfirm 高级版本（优化传值）
 * 
 * @param params
 *            参数对象
 * @returns var params = {content:"同步前置码废除信息到国际平台失败！", title:"提示"}; example：
 *          dynamicConfirm(params, function(res){ alert(20); //点击OK res=true 点击
 *          Cancel res=false });
 */
function dynamicHighConfirm(params, callBack){
	var _params = {
			// 展示内容
			content:null,
			// 标题
			title:null,
			// icoCss 图标样式 默认感叹号(问号：question_mark 对号：right_mark)
			icoCss:null,
			// 确定按钮内容，默认：OK
			okText:null,
			// 取消按钮内容，默认：CANCEL
			cancelText:null,
			// 确定按钮演示
			okCss:null,
			// 取消按钮演示
			cancelCss:null,
			// 取消按钮是否显示
			isShowCancel: true,
			mark:"mark" + Math.random().toString().replace(".","")
		};
	
	$.extend(true, _params, params); 
	
	var dialog = $(top.document.createElement("div")).addClass("dialog delete_dialog").attr("mark", _params.mark).attr('type', 'dialog'),
		title = $(top.document.createElement("div")).addClass("dialog_title").html(checkValue(_params.title, "提示")),
		cont = $(top.document.createElement("div")).addClass("delete_content"),
		txt = $(top.document.createElement("div")).addClass(checkValue(_params.icoCss, 'txt')).html(checkValue(_params.content, "")),
		line = $(top.document.createElement("div")).addClass("dialog_line"),
		footer = $(top.document.createElement("div")).addClass("dialog_footer"),
		btnCancel = $(top.document.createElement("button")).addClass(checkValue(_params.cancelCss, "btn-reset NoBtn")).html(checkValue(_params.cancelText, '')),
		btnOk = $(top.document.createElement("button")).addClass(checkValue(_params.okCss, "btn-true YesBtn")).html(checkValue(_params.okText, ''));
		
	btnCancel.click(function(){
		$("div[mark="+ _params.mark +"]", top.document.body).empty().remove();
		callBack(false);
		// 取消锁定键盘
		$(document).off("keydown", offKeydown);
	});
	btnOk.click(function(){
		$("div[mark="+ _params.mark +"]", top.document.body).empty().remove();
		callBack(true);
		// 取消锁定键盘
		$(document).off("keydown", offKeydown);
	});
	
	txt.appendTo(cont);
	if (_params.isShowCancel) {
		btnCancel.appendTo(footer);
	}
	btnOk.appendTo(footer);
	title.appendTo(dialog);
	cont.appendTo(dialog);
	line.appendTo(dialog);
	footer.appendTo(dialog);
	dialog.appendTo($(top.document.body));

	isCoverDiv(_params.mark);
	objectCenter(dialog.show());
	dynamicDivHeight(dialog);

	this.callBack = function(){};
	$(window).resize(function(){
		objectCenter(dialog);
		dynamicCoverHeight($(".overlay", top.document.body));
	});
	// 弹出层不锁定键盘
	dialog.on("keydown", onKeydown);
};


/**
 * 和Alert功能一样
 * 
 * @param content
 *            提示内容
 * @param title
 *            标题
 * @param icoCss
 *            图标样式 默认感叹号(问号：question_mark 对号：right_mark)
 */
function dynamicAlert(content, title, icoCss, okText, okCss){
	var mark = "mark" + Math.random().toString().replace(".",""),
		dialog = $(top.document.createElement("div")).addClass("dialog delete_dialog").attr("mark", mark).attr('type', 'dialog'),
		title = $(top.document.createElement("div")).addClass("dialog_title").html(checkValue(title, "提示")),
		cont = $(top.document.createElement("div")).addClass("delete_content"),
		txt = $(top.document.createElement("div")).addClass(checkValue(icoCss, 'txt')).html(checkValue(content, "")),
		line = $(top.document.createElement("div")).addClass("dialog_line"),
		footer = $(top.document.createElement("div")).addClass("dialog_footer"),
		btnOk = $(top.document.createElement("button")).addClass(checkValue(okCss, "btn-true YesBtn")).html(checkValue(okText, ''));		
		 
	btnOk.click(function(){
		$("div[mark="+ mark +"]", top.document.body).empty().remove();
		// 取消锁定键盘
		$(document).off("keydown", offKeydown);
	});
	
	txt.appendTo(cont);
	btnOk.appendTo(footer);
	title.appendTo(dialog);
	cont.appendTo(dialog);
	line.appendTo(dialog);
	footer.appendTo(dialog);
	dialog.appendTo($(top.document.body));

	isCoverDiv(mark);
	$(window).resize(function(){
		objectCenter(dialog);
		dynamicCoverHeight($(".overlay", top.document.body));
	});
	dynamicDivHeight(dialog);
	objectCenter(dialog.show());
};

/**
 * 描述：自定义内容弹出框 注意：这个方法需要自己调关闭遮盖层方法：closeCoverDiv();（根据自己业务逻辑定）
 * 
 * @param setting
 *            title 标题 okText OK按钮显示的文字 key 主键的ID和name值 txtAread 多文本框
 * @returns {___anonymous3784_4253}返回弹出层本身对象（jquery对象）
 */
function dynamicPopDiv(setting){
	var _setting = {
			id: null,
			title: null,
			formId: null,
			okText: null,
			cancelText: null,
			key: null,
			txtAread: null,
			tip:null,
			dialogCss:null,
			titleCss:null,
			OkCss:null,
			cancelCss:null,
			css:{},
			content:null,
			isShowCancel:true,
			mark:"mark" + Math.random().toString().replace(".","")
	};
	$.extend(true, _setting, setting); 
	var popDiv = {
			dialog : $(top.document.createElement("div")).addClass("dialog " + checkValue(_setting.dialogCss, '')).css(_setting.css)
				.attr('id', checkValue(_setting.id, 'dialog')).attr("mark", _setting.mark).attr('type', 'dialog'),
			title : $(top.document.createElement("div")).addClass("dialog_title " + checkValue(_setting.titleCss, '')).html(_setting.title),
			key : $(top.document.createElement("input")).attr("type", "hidden").attr("name", _setting.key).attr("id", _setting.key),
			btnOk : $(top.document.createElement("button")).addClass(checkValue(_setting.OkCss, "btn-true YesBtn")).html(checkValue(_setting.okText, '')),
			btnCancel : $(top.document.createElement("button")).addClass(checkValue(_setting.cancelCss, "btn-reset NoBtn")).html(checkValue(_setting.cancelText, '')),
			footer : $(top.document.createElement("div")).addClass("dialog_footer"),
			pTipE : $(top.document.createElement("div")).addClass("pTipE"),// 错误提示div
			// line :
			// $(top.document.createElement("div")).addClass("dialog_line"),
			form : $(top.document.createElement("form")).attr('onsubmit', 'return false;').attr('id', checkValue(_setting.formId, 'dialog_form_xxx')),
			element: _setting
	};
	
	popDiv.title.appendTo(popDiv.dialog);
	// 判断有没有textArea元素
	if(null != _setting.txtAread){		
		// 把textArea元素加入到弹出层中
		_setting.txtAread.appendTo(popDiv.form);
		
		// 给textArea设置提示语、focus事件
		_setting.txtAread.html(_setting.tip).focus(function(){

			var val = $(this);
			if(val.val() == _setting.tip){
				// 获取焦点清空textArea并设置字体颜色
				val.val("").css("color","#595959");
			};
			popDiv.pTipE.empty();
		});
	};
	
	if(null != _setting.content){
		_setting.content.appendTo(popDiv.form);
	};
	if(popDiv.key){
		popDiv.key.appendTo(popDiv.form);
	}
	
	popDiv.form.appendTo(popDiv.dialog);
	
	if(_setting.isShowCancel){
		popDiv.btnCancel.appendTo(popDiv.footer);
	};
	popDiv.btnOk.appendTo(popDiv.footer);

	// popDiv.line.appendTo(popDiv.dialog);
	popDiv.footer.appendTo(popDiv.dialog);
	popDiv.dialog.appendTo($(top.document.body));

	objectCenter(popDiv.dialog.show());
	isCoverDiv(_setting.mark);
	dynamicDivHeight(popDiv.dialog);
	
	$(window).resize(function(){
		objectCenter(popDiv.dialog.show());
		dynamicCoverHeight($(".overlay", top.document.body));
	});	
	// 弹出层不锁定键盘
	popDiv.dialog.on("keydown", onKeydown);
	return popDiv;
};

/**
 * 不锁定键盘
 */
function onKeydown(e){
	e.stopPropagation();
}

/**
 * 锁定键盘
 */
function offKeydown(e){
	e.preventDefault();
}

/**
 * 弹出DIV居中
 * 
 * @param obj
 *            Div对象
 */
function objectCenter(obj){
	var windowWidth = top.document.documentElement.clientWidth,
		windowHeight = top.document.documentElement.clientHeight,
		popupHeight = $(obj).height(),
		popupWidth = $(obj).width();
	$(obj).css({
		"position": "absolute",
		"top": (windowHeight-popupHeight)/2 + $(top.document).scrollTop(),
		"left": (windowWidth-popupWidth)/2
	});
};

/**
 * 动态设置遮盖层高度
 */
function dynamicCoverHeight(div){
	// div.css("height", $(top.document.body).height() + "px");
	div.css({display: "block"});
}

/**
 * 
 * 动态设定对象高度
 * 
 * @param div
 */
function dynamicDivHeight(div){
	var windowWidth = top.document.documentElement.clientWidth,
		windowHeight = top.document.documentElement.clientHeight,
		popupHeight = div.height(),
		popupWidth = div.width();
	
	$(top.window).scroll( function() { div.animate({top:($(top.document).scrollTop() + (windowHeight-popupHeight)/2) }, 65)} );
};

/**
 * 判断是否存在遮盖层，存在直接显示，不存在直接生成
 */
function isCoverDiv(mark){
	// 锁定键盘
	$(document).on("keydown", offKeydown);
	if(mark){
		var cover = $(top.document.createElement("div")).addClass("overlay").attr("mark", mark).attr('type', 'overlay').height($(top.document).height()).show();
		cover.appendTo($(top.document.body));
		// 动态设置新弹出框的z_index
		dynamicZindex();
	}else{
		var cover = $(top.document.createElement("div")).addClass("overlay").attr("id", "iscoverdiv_xxx").attr('type', 'overlay').height($(top.document).height()).show();
		closeCoverDiv();
		cover.appendTo($(top.document.body));
	}
};

/**
 * 关闭遮盖层
 */
function closeCoverDiv(obj){
	// 取消锁定键盘
	$(document).off("keydown", offKeydown);
	if(obj){
		obj.empty().remove();
	}else{
		$(".dialog, .overlay", top.document.body).empty().remove();
	}
};

/**
 * 动态设置新弹出框的z_index
 */
function dynamicZindex(){
	var zi = [], d = [];
	$.each($(".dialog"), function(){
		zi.push(parseInt($(this).css("z-index")));
		var x = {};
		x.index =  parseInt($(this).css("z-index"));
		x.mark = $(this).attr("mark");
		d.push(x);
	});
	var min = zi.sort(function(a,b){return a>b?1:-1})[0],
		max = zi.sort(function(a,b){return a<b?1:-1})[0],
		o = {};
	
	$.each(d, function(k, v){
		if(v.index == min){
			o.mark = v.mark;
		}
		if(v.index == max){
			o.index = v.index;
		}
	});

	$.each($("div[mark="+o.mark+"]"), function(){
		var self = $(this);
		switch (self.attr("type")) {
			case "dialog":
				self.css("z-index", o.index+1);
				break;
			case "overlay":
				self.css("z-index", o.index);
				break;
		}
	});
}

/**
 * 检查值是否有效，无效值返回默认值。（无效值：undefined、null、''）
 * 
 * @param value
 *            检查的值
 * @param defalut
 *            默认值
 * @returns
 */
function checkValue(value, defalut){
	return value == undefined || value == null || value == '' ? defalut : value;
}

/**
 * 去掉指定区域下的文本前后空格（text、textarea）
 * 
 * @param areaObj
 *            区域对象
 */
function trimText(areaObj){
	areaObj.find(':text, textarea').each(function(i, v){
		 $(this).val($.trim($(this).val()));
	});
}

/**
 * 生成下拉框
 * 
 * @param obj
 *            对象
 * @param data
 *            数据
 * @param name
 *            name
 * 
 * var params = { //目标对象 obj:$("#selSelTemp"), //select数据 data:impTempData, //
 * select的key, value 对应的key值集合 keys:["templateId", "templateName"],
 * //select的name属性 name:"templateIdS", //是否需要默认值 defaultBoolean:false, //select
 * css css:"impTemWidth", //默认值 defaultValue:$("#templateIdHide").val() };
 */
function createSelect(params, schange) {
	var _params = {
			// 目标对象
			obj:null,
			// select数据
			data:null,
			// select的key, value 对应的key值集合
			keys:null,
			// select的name属性
			name:null,
			// 是否需要默认值
			defaultBoolean:null,
			// select css
			css:null,
			// select class
			classs:null,
			// 默认值
			defaultValue : null
			};
	
	$.extend(true, _params, params); 
	
	if(_params.obj){
		_params.obj.find("select").empty().remove();
	}
	
	var select = $("<select name='" + _params.name + "'/>").addClass(_params.classs);
	
	if(_params.css){
		select.css(_params.css);
	}

	if(_params.defaultBoolean){
		$("<option value='' selected='selected'>请选择</option>").appendTo(select);
	}

	if(!_params.data){
		$("<option value=''>--</option>").appendTo(select);
	}
	if(_params.data)
	$.each(_params.data, function(k, v) {
		var op = $("<option value=" + v[_params.keys[0]] + ">" + v[_params.keys[1]] + "</option>");
		if(_params.defaultValue && v[_params.keys[0]] == _params.defaultValue){op.attr("selected", "selected");}
		op.appendTo(select);
	});
	
	if(_params.obj){
		select.appendTo(_params.obj);
	}
	select.change(function(v){schange(this);});
}

/**
 * 序列化表格元素为JSON
 * 
 * @param formObj
 *            对象，表格元素对象
 * @returns JSON对象
 */
$.fn.serializeJson = function() {
	var o = {};
	var a = this.serializeArray();
	$.each(a, function() {
		if (o[this.name] !== undefined) {
			if (o[this.name] == null || !o[this.name].push) {
				o[this.name] = [o[this.name]];
			}
			o[this.name].push(this.value || null);
		} else {
			o[this.name] = this.value || null;
		}
	});
    return o;
}

/**
 * 获取登陆用户数据
 * 
 * @param id
 *            账号编号
 * @returns
 */
function getAccountInfo(){
	var data;
	$.ajax({
	   type: "POST",
	   url: "/epsp/mvc/accountinfo/getAccountInfo.json",
	   dataType:'json',
	   async : false,
	   success: function(result){
		   data = result;
	   }
	});
	return data;
}

/**
 * 获取登陆企业数据
 * 
 * @returns
 */
function getCompanyInfo(){
	var data;
	$.ajax({
	   type: "POST",
	   url: "/epsp/mvc/accountinfo/getCompanyInfo.json",
	   dataType:'json',
	   async : false,
	   success: function(result){
		   data = result;
	   }
	});
	return data;
}

/**
 * 获取登陆企业数据
 * 
 * @returns
 */
function getGuild(){
	var data;
	$.ajax({
	   type: "POST",
	   url: "/epsp/mvc/accountinfo/getGuild.json",
	   dataType:'json',
	   async : false,
	   success: function(result){
		   data = result;
	   }
	});
	return data;
}

/**
 * 获取配置项数据
 * 
 * @returns
 */
function getConfigInfo(keyName){
	var data;
	$.ajax({
	   type: "POST",
	   url: "/epsp/mvc/sysconfig/getConfigByKeyName.json",
	   data:{
		   keyName:keyName
	   },
	   dataType:'json',
	   async : false,
	   success: function(result){
		   data = result;
	   }
	});
	return data;
}

function getOrgId(){
	var oid = $(window.parent.document).find('#oid').val();
	return oid;
}

String.prototype.endWith=function(s){
	  if(s==null||s==""||this.length==0||s.length>this.length)
     return false;
  if(this.substring(this.length-s.length)==s)
     return true;
  else
     return false;
  return true;
 }

 String.prototype.startWith=function(s){
  if(s==null||s==""||this.length==0||s.length>this.length)
   return false;
  if(this.substr(0,s.length)==s)
     return true;
  else
     return false;
  return true;
 }
 
 function windowClose(){
	var ua = navigator.userAgent;
	var ie = navigator.appName == "Microsoft Internet Explorer"?true:false;
	if(ie){
		var IEversion = parseFloat(ua.substring(ua.indexOf("MSIE")+5,ua.indexOf(";",ua.indexOf("MSIE"))));
		if(IEversion < 5.5){
			var str = "<object id = 'noTipClose' classid='clsid:ADB880A6-D8FF-11CF-9377-00AA003B7A11'>";
			str += "<param name='Command' value='Close'/></object>";
			document.body.insertAdjacentHTML("beforeEnd",str);
			document.all.noTipClose.Click();
		}else{
			window.opener = null;
			window.open('','_self','');
			window.close();
		}
	}else{
		window.close();
	}
}

function closeBrowser(obj){
	$('.alert_browser').next().remove();
	$('.alert_browser').remove();
	$("html").css("overflow","auto");
	$("body").css("overflow","auto");
}