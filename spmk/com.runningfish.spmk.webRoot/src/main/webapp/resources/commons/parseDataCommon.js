/**
 * 给显示元素赋值
 * keys：例如图书对象的属性
 * data:例如返回的图书对像
 * 
 */
function assignment(keys, data, exName){
	 if(keys && data){
		 $.each(keys, function(k, v){
			 if(v.id){
				 if(!(exName && isExistField(v.name, exName))){
					 $(v).text(checkValue(dynamicParseData(data, v.id), ""));
				 }
			 }
		 });
	 }
}

/**
 * 给显示元素赋值
 * keys：例如图书对象的属性
 * data:例如返回的图书对像
 * 
 */
function assignmentInput(keys, data, exName){
	 if(keys && data){
		 $.each(keys, function(k, v){
			 if(v.name){
				 if(!(exName && isExistField(v.name, exName))){
					 $(v).val(checkValue(dynamicParseData(data, v.name), ""));
				 }
			 }
		 });
	 }
}

/**
 * 解析数据
 * 
 * @param data
 *            要解析的数据，格式：{name:"Mr.A", age: "20"}
 * @param name
 *            name字段
 * @returns {String} 返回解析结果
 */
function dynamicParseData(data, name) {
	var res = "";
	if (data == null) {
		return res;
	};
	$.each(data, function(k, v) {
		if (k == name) {
			res = v;
			return false;
		};
	});
	return checkValue(res, "");
}

/**
 * 解析数据
 * 
 * @param data
 *            数据格式：[{keys[0]:對比值key,keys[1]:返回值key}]
 * @param index
 * @returns {String}
 */
function dynamicParseArrayObj(data, keys, index) {
	var text;
	$.each(data, function(k, v) {
		if (v[keys[0]] == index) {
			text = v[keys[1]];
			return false;
		};
	});
	return checkValue(text, "");
}

/**
 * 判断数据是否有name值
 * 
 * @param name
 *            name值
 * @param array
 *            数组
 * @returns {Boolean} true：存在 false：不存在
 */
function isExistField(name, array) {
	var res = false;
	if ($.isArray(array)) {
		$.each(array, function(z, x) {
			if (name == x) {
				res = true;
			};
		});
	};
	return res;
}