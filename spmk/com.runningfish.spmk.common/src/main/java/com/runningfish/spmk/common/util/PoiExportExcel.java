package com.runningfish.spmk.common.util;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.runningfish.spmk.common.ExcelExportVo;

/**
 * 
 * 公用导出excel
 * @author taowange
 *
 */
public class PoiExportExcel
{
	private XSSFWorkbook wb = null;

	private XSSFSheet sheet = null;

	/**
	 * @param wb
	 * @param sheet
	 */
	public PoiExportExcel(XSSFWorkbook wb, XSSFSheet sheet)
	{
		this.wb = wb;
		this.sheet = sheet;
	}

	/**
	 * 合并单元格后给合并后的单元格加边框
	 * 
	 * @param region
	 * @param cs
	 */
	public void setRegionStyle(CellRangeAddress region, XSSFCellStyle cs)
	{

		int toprowNum = region.getFirstRow();
		for (int i = toprowNum; i <= region.getLastRow(); i++)
		{
			XSSFRow row = sheet.getRow(i);
			for (int j = region.getFirstColumn(); j <= region.getLastColumn(); j++)
			{
				XSSFCell cell = row.getCell(j);// XSSFCellUtil.getCell(row,
												// (short) j);
				cell.setCellStyle(cs);
			}
		}
	}

	/**
	 * 设置表头的单元格样式
	 * 
	 * @return
	 */
	public XSSFCellStyle getHeadStyle()
	{
		// 创建单元格样式
		XSSFCellStyle cellStyle = wb.createCellStyle();
		// 设置单元格的背景颜色为淡蓝色
		cellStyle.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
		cellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		// 设置单元格居中对齐
		cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		// 设置单元格垂直居中对齐
		cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		// 创建单元格内容显示不下时自动换行
		cellStyle.setWrapText(true);
		// 设置单元格字体样式
		XSSFFont font = wb.createFont();
		// 设置字体加粗
//		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		font.setFontHeight((short) 200);
		cellStyle.setFont(font);
		// 设置单元格边框为细线条
		cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
		return cellStyle;
	}

	/**
	 * 设置表体的单元格样式
	 * 
	 * @return
	 */
	public XSSFCellStyle getBodyStyle()
	{
		// 创建单元格样式
		XSSFCellStyle cellStyle = wb.createCellStyle();
		// 设置单元格居中对齐
		cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		// 设置单元格垂直居中对齐
		cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		// 创建单元格内容显示不下时自动换行
		cellStyle.setWrapText(true);
		// 设置单元格字体样式
		XSSFFont font = wb.createFont();
		// 设置字体加粗
//		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		font.setFontHeight((short) 200);
		cellStyle.setFont(font);
		// 设置单元格边框为细线条
		cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
		return cellStyle;
	}
	
	/**
	 * 导出excel文件公用方法
	 * @param excelExportVo 导出文件vo
	 * @param outputStream 导出流
	 * @param list 查询的数据集合
	 */
	public static void exportExcel(ExcelExportVo excelExportVo,ServletOutputStream outputStream,
				List<Object> list)
    {   
        // 创建一个workbook 对应一个excel应用文件  
        XSSFWorkbook workBook = new XSSFWorkbook();  
        // 在workbook中添加一个sheet,对应Excel文件中的sheet  
        XSSFSheet sheet = workBook.createSheet(excelExportVo.getSheetName());  
        PoiExportExcel exportUtil = new PoiExportExcel(workBook, sheet);  
        XSSFCellStyle headStyle = exportUtil.getHeadStyle();  
        XSSFCellStyle bodyStyle = exportUtil.getBodyStyle();  
        //设置默认列宽
        sheet.setDefaultColumnWidth(16);
        // 构建表头  
        XSSFRow headRow = sheet.createRow(0);  
        //设置表头高度
        headRow.setHeight((short)1000);
        XSSFCell cell = null;  
        for (int i = 0; i < excelExportVo.getTitles().length; i++)  
        {  
            cell = headRow.createCell(i);  
            cell.setCellStyle(headStyle);  
            cell.setCellValue(excelExportVo.getTitles()[i]);  
        }  
        // 构建表体数据  
        if (list != null && list.size() > 0)  
        {  
            for (int j = 0; j < list.size(); j++)  
            {  
                XSSFRow bodyRow = sheet.createRow(j + 1);
                //设置内容行高
                bodyRow.setHeight((short)600);
                Object obj = list.get(j);  
                for(int i = 0 ; i < excelExportVo.getInvokeName().length;i++){    
                	//创建表格
                	cell = bodyRow.createCell(i);  
                	//设置样式
                	cell.setCellStyle(bodyStyle); 
                	//获取对象属性值
                	Object str = ClassInvokeUtil.getter(obj, excelExportVo.getInvokeName()[i]);
                	//表格设置值
                	cell.setCellValue((String) str);  
                } 
            }  
        }  
        try  
        {  
            workBook.write(outputStream);  
            outputStream.flush();  
            outputStream.close();  
        }  
        catch (IOException e)  
        {  
            e.printStackTrace();  
        }  
        finally  
        {  
            try  
            {  
                outputStream.close();  
            }  
            catch (IOException e)  
            {  
                e.printStackTrace();  
            }  
        }  
  
    }  
}