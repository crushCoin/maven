package com.runningfish.spmk.common;

import java.io.Serializable;

public class ExcelExportVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String exportFileName;//导出excel文件名称
	private String[] invokeName; //导出对象字段vo属性
	private String[] titles; //导出字段标题信息
	private String sheetName;//导出excel sheetName 名称
	
	public String getExportFileName() {
		return exportFileName;
	}
	public void setExportFileName(String exportFileName) {
		this.exportFileName = exportFileName;
	}
	public String[] getInvokeName() {
		return invokeName;
	}
	public void setInvokeName(String[] invokeName) {
		this.invokeName = invokeName;
	}
	public String[] getTitles() {
		return titles;
	}
	public void setTitles(String[] titles) {
		this.titles = titles;
	}
	public String getSheetName() {
		return sheetName;
	}
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	
}
