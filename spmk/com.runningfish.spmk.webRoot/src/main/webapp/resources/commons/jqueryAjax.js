/**
 * 扩展 ajax 增加 后台返回的业务处理 引用加载 by 5100 zhangwen 2012 11 16
 */

(function($) {
	// 备份jquery的ajax方法
	var _ajax = $.ajax;

	// 重写jquery的ajax方法
	$.ajax = function(opt) {
		// 备份opt中error和success方法
		var fn = {
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			},
			success : function(data, textStatus) {
			}
		}
		if (opt.error) {
			fn.error = opt.error;
		}
		if (opt.success) {
			fn.success = opt.success;
		}

		// 扩展增强处理
		var _opt = $
				.extend(
						opt,
						{
							error : function(XMLHttpRequest, textStatus,
									errorThrown) {
								if(parent.window!=undefined)
									parent.window.frames['centerFrame'].window.location.href='/net5100/workbench/error.html?error=服务器出现错误！'
								else
								    window.location.href = '/net5100/workbench/error.html?error=服务器出现错误！'
							},
							success : function(data, textStatus) {
                             
								if (typeof (data) == "object"&&data!=null) {
									if ('systemError' in data) { // 系统错误处理
										if(parent.window!=undefined)
											parent.window.frames['centerFrame'].window.location.href='/net5100/workbench/error.html?error='
												+ data.systemError.msg;
										else
										    window.location.href = '/net5100/workbench/error.html?error='
													+ data.systemError.msg;
									} else if ('sesssionError' in data) { // session
										if(parent.window!=undefined)
										    parent.window.location.href = '/net5100/workbench/outSession.html';
										else
											window.location.href ='/net5100/workbench/outSession.html'
									} else if ('purviewError' in data) { // 没有业务模块权限处理
										if(parent.window!=undefined)
											parent.window.frames['centerFrame'].window.location.href='/net5100/workbench/purviewError.html';
										else	
										   window.location.href = '/net5100/workbench/purviewError.html';
								   } else {
										fn.success(data, textStatus);
									}
								} else {
									fn.success(data, textStatus);
								}
							}
						});
		_ajax(_opt);
	};
})(jQuery);
