function showLowBrowserAlert(){
	var useragent = navigator.userAgent.toLowerCase(),surportBrowser = false; 
	if(useragent.indexOf('mobile') == -1){
		if("ActiveXObject" in window){
			if((useragent.indexOf('msie 8') != -1||useragent.indexOf('msie 9') != -1||useragent.indexOf('msie 10') != -1)&&useragent.indexOf('qqbrowser') == -1){
				surportBrowser = true;			
			}else if(useragent.indexOf('msie 6') != -1||useragent.indexOf('msie 7') != -1||useragent.indexOf('qqbrowser') != -1){
				surportBrowser = false;	
			}else{
				surportBrowser = true;	
			}
		}else if(useragent.indexOf('chrome') != -1 && useragent.indexOf('metasr') == -1){
			surportBrowser = true
		}else if(useragent.indexOf('firefox') != -1){
			surportBrowser = true
		}
		
		if(surportBrowser){
			$("html").css("overflow","visible");
			$("body").css("overflow","auto");
			return surportBrowser;
		}else{		
			var strHTML = '<div class="alert_browser"><a href="javascript:closeBrowser(this);" id="deleteTip"><img src="/epsp/resources/images/home_close.png"></a>'+
			'<div class="info_txt">'+
			'	<h3 class="tit">亲，智造东莞暂时不支持您使用的浏览器哦~</h3>'+
			'	<p class="tit_aside">为了得到我们网站最好的体验效果，建议亲选择以下任意一个web浏览器</p>'+
			'</div>'+
			'<span class="bg"></span>'+
			'<span class="clear_wrap "></span>'+
			'<div class="recommend">'+
			'	<a class="icon_ie" href="http://dlsw.baidu.com/sw-search-sp/soft/41/23253/IE8-WindowsXP-x86-CHS.2728888507.exe" title="IE浏览器" target="_blank">Internet Explorer</a>'+
			'	<a class="icon_360" href="http://se.360.cn/" title="360浏览器" target="_blank">360</a>'+
			'	<a class="icon_safari" href="http://www.firefox.com.cn/" title="火狐浏览器" target="_blank">Firefox</a>'+
			'	<a class="icon_chrome" href="http://dlsw.baidu.com/sw-search-sp/soft/9d/14744/ChromeStandalone_V42.0.2311.152_Setup.1431487787.exe" title="谷歌浏览器" target="_blank">Chrome</a>'+
			'</div>'+
			'</div>'+
			'<span class="browser_overlay"></span>';
			//setTimeout(function(){
				$("body").append(strHTML);
			//},100);
		}
	}
	return surportBrowser;
}