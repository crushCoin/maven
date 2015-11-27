/**
 * @(#)PoiReadExcel.java 1.0 2015-3-23
 * @Copyright:  Copyright 2000 - 2015 Isoftstone Tech. Co. Ltd. All Rights Reserved.
 * @Description: 
 * 
 * Modification History:
 * Date:        2015-3-23 下午5:06:09
 * Author:      wangtao 28873
 * Version:     EPSPV1.D1.0.0.0
 * Description: (Initialize)
 * Reviewer:    
 * Review Date: 
 */
package com.runningfish.spmk.common.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * 根据对象读取excel文件数据
 * @author wangtao
 *
 */
public class PoiReadExcel {
    public PoiReadExcel() {
    	
    } 
    /*
    public static void main(String[] args) {
    	PoiReadExcel POIExcelUtils = new PoiReadExcel();
    	String filePath = "F:\\smc元数据模板\\audio.xlsx";
    	Map<String,Object> map = POIExcelUtils.read(filePath,0,"com.excel.pojo.Audio");
    	if(null!=map &&(Boolean) map.get("scucces")){
    		@SuppressWarnings("unchecked")
            List<Object> list = (List<Object>) map.get("result");
    		for(int i = 0 ; i < list.size(); i ++){
        		System.out.println(list.get(i).toString());
        	}
    	}
    	
	}*/
    /**
     * 读取excel，支持excel 97~03 / excel 07
     * @param fileName 文件名
     * @param sheetNum 要读取的sheet页
     * @param classPath 数据对于类路径
     * @return 集合List
     */
    public Map<String,Object> read(InputStream is,int sheetNum,String classPath ) {
       Workbook wb = null;
       //File f = new File(fileName);
       //FileInputStream is;
       Map<String,Object> map = new HashMap<String, Object>();
       try {
           //is = new FileInputStream(f);
           wb = WorkbookFactory.create(is);
           map = readWB(wb,sheetNum,classPath);
           is.close();
       } catch (FileNotFoundException e) {
    	   e.printStackTrace();
    	   map.put("scucces", false);
    	   map.put("msg", "读取excel描述文件失败");
       } catch (IOException e) {
    	   e.printStackTrace();
    	   map.put("scucces", false);
    	   map.put("msg", "读取excel描述文件失败");
       } catch (InvalidFormatException e) {
    	   e.printStackTrace();
    	   map.put("scucces", false);
    	   map.put("msg", "读取excel描述文件失败");
       } catch (Exception e) {
    	   e.printStackTrace();
    	   map.put("scucces", false);
    	   map.put("msg", "读取excel描述文件失败");
	}    
   return map;
}
    
    /** 
     * 读取Excel 保存数据
     * @param wb excel
     * @param sheetNum 要读取的sheet页
     * @param classPath 对应类的路径
     * @return 数据集合
     */

    private Map<String,Object> readWB(Workbook wb,int sheetNum,String classPath) throws Exception{   
    	List<Object> data = new ArrayList<Object>();;
    	Map<String,Object> map = new HashMap<String, Object>();
		// 读取sheet0
        for (int k = 0; k < wb.getNumberOfSheets(); k++) {
            //sheet   
            //Sheet sheet = wb.getSheetAt(k);  
         Sheet sheet = wb.getSheetAt(k);
         //System.out.println("PhysicalNumberOfRows:"+sheet.getPhysicalNumberOfRows());
         //System.out.println("FirstRowNum:"+sheet.getFirstRowNum());
         //System.out.println("LastRowNum:"+sheet.getLastRowNum());
         List<String> listTile = excelTileList(wb, sheetNum);
         if(null==listTile||listTile.size()==0){
        	 map.put("scucces", false);
        	 map.put("msg", "描述文件表头信息为空");
        	 return map;
         }
         //加载类
         Class<?> objClass=Class.forName(classPath);
         Object obj = null;
         //获取总行数
         int  rows = sheet.getLastRowNum();
         for(int i = 0 ; i<= rows;i++ ){
        	 if(i!=0){
        		 //实例化类
                 obj=objClass.newInstance();
        		 Row row = sheet.getRow(i);	//获取第一行数据
            	 for(int j = 0 ; j < row.getLastCellNum(); j ++ ){
            		Cell cell = row.getCell(j);  //获取第一个单元格
            		//System.out.println(getCellValue(cell));
            		//cell.setCellType(HSSFCell.ENCODING_UTF_16); //中文乱码处理 
            		//取单元格值
                	String value = getCellValue(cell);
                	//往对象设置值
                	ClassInvokeUtil.setter(obj,listTile.get(j),value,String.class);
            	 }
            	 data.add(obj);
        	 }
         } 
         map.put("scucces", true);
    	 map.put("result", data);
      } 
      return map;
    }
    
    /**
     * 读取excel表头数据
     * @param wb 工作薄
     * @param sheetNum sheet页
     * @return
     */
    private List<String> excelTileList(Workbook wb,int sheetNum){
    	List<String> list = new ArrayList<String>();
    	Sheet sheet = wb.getSheetAt(sheetNum);
       	Row row = sheet.getRow(0);	//获取第一行数据
       	for(int j = 0 ; j < row.getLastCellNum(); j ++ ){
       		 Cell cell = row.getCell(j);  //获取第一个单元格
       		 String value = getCellValue(cell);
       		 list.add(value);
        }
       	return list;
    }
    
    /**
     * 获取每个labal的值
     * @param cell Cell对象
     * @return String value值
     */
    private String getCellValue(Cell cell){
    	String value = "";
    	if (cell != null) {     
            switch (cell.getCellType()) {                                   
               case HSSFCell.CELL_TYPE_FORMULA:  
            	   //"FORMULA value=" 
                   value = cell.getCellFormula();   
                   break; 
               case HSSFCell.CELL_TYPE_NUMERIC:  
                   if(HSSFDateUtil.isCellDateFormatted(cell)){  
                	   // 如果是Date类型则，取得该Cell的Date值   
                	   //Date date = cell.getDateCellValue();   
                	   //"DATE value=" 
                       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                       value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString();
                   }else{   
                	   // 取得当前Cell的数值   
//                	   Integer num = new Integer((int) cell .getNumericCellValue()); 
                	   
                	   // "NUMERIC value=" 
                       Double v = cell.getNumericCellValue();
                       if(null != v){
                           value = String.valueOf(v.intValue()); 
                       }
                   }
                   break;    
                case HSSFCell.CELL_TYPE_STRING: // 字符串
                	//取得当前的Cell字符串   
                   value = cell.getStringCellValue().replaceAll("'", "''");   
                   break;                
                case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
                	//"BOOLEAN value="+
                    value =  ""+cell.getBooleanCellValue();                            
                   break;                 
                default:   
              }   
         }
    	return  value;
    }
    
    public static InputStream readFile(String filePath) throws FileNotFoundException{
    	InputStream is = null;
    	is = new FileInputStream(filePath);
    	return is;
    }
}