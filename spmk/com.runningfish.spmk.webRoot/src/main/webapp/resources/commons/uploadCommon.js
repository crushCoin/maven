$.extend({
	ajaxUpload: function(button, options) {
		var _settings = {
				action: '/epsp/mvc/fileupload/fileUpload.json',
				name: 'file',
				data: {},
				inputAttrs: null, // input[type=file]属性集，格式：{attrName:attrValue}。
				autoSubmit: true,
				responseType: 'json',
				hoverClass: 'hover',
				disabledClass: 'disabled',            
				onChange: null,
				onSubmit: null,
				onComplete: null
		};
		$.extend(true, _settings, options);
		
		var au = new AjaxUpload(button, {
			action: _settings.action,	// 提交的url
			autoSubmit: _settings.autoSubmit,		// 自动提交
			responseType: _settings.responseType,			// 服务器返回的数据格式
			name: _settings.name,							// 提交到后台的文件参数名称
			data: _settings.data,
			onChange: function(file, extension) {	// 文件选择后使用,file--上传文件名, extension--文件扩展名
				if (_settings.onChange != null) {
					return _settings.onChange(file, extension, au._button);
				}
			},
			onSubmit: function(file, extension) {	// 文件上传前使用
				if (_settings.onSubmit != null) {
					return _settings.onSubmit(file, extension, au._button);
				}
			},
			onComplete: function(file, data) {		//文件上传成功后，file--上传的文件名, data--服务器返回的数据
				if (_settings.onComplete != null) {
					_settings.onComplete(file, data, au._button);
				}
			}
		});
		if (_settings.inputAttrs) {
			if (!au._input) {
				au._createInput();
				$.each(_settings.inputAttrs, function(k, v){
					au._input.setAttribute(k, v);
				});
			}
		}
		return au;
	}
});