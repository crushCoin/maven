package com.runningfish.spmk.test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public class TestReflect 
{
	private static String STATIC_RESOURCE_FOLDER = "resource";
	
	static private String META_INF_DIRECTORY = "META-INF";
    static private String MANIFEST_NAME = "MANIFEST.MF";
    static private String MANIFEST_FILE_PATH = String.format("%s%s%s", META_INF_DIRECTORY, File.pathSeparator,
            MANIFEST_NAME);
    
	public static void main(String[] args) 
    {
		String reflectName = TestReflect.class.getName();
		Class<? extends Object> runTimeObj = TestReflect.class.getClass();
		//System.out.println(reflectName);
		//System.out.println(runTimeObj);
		
		Number n = 0.0f; 
		Class<? extends Number> c = n.getClass(); 
		//System.out.println(c);
		
		ClassLoader voidClass = void.class.getClassLoader();
		//System.out.println(voidClass);
		
		Map<String,String> envValue = System.getenv();
		/*Iterator<String> iterator = envValue.keySet().iterator();
		while(iterator.hasNext()){
			String keyName = iterator.next();
			System.out.println(keyName+":"+envValue.get(keyName));
		}*/
		
		Iterator<Entry<String, String>> entry = envValue.entrySet().iterator();
		/*while(entry.hasNext()){
			Entry<String, String> obj = entry.next();
			System.out.println(obj.getKey()+":"+obj.getValue());
		}*/
		
		Properties properties = System.getProperties();
		/*Iterator<Entry<Object,Object>> propertyEntry = properties.entrySet().iterator();
		while(propertyEntry.hasNext()){
			Entry<Object,Object> obj = propertyEntry.next();
			System.out.println(obj.getKey()+":"+obj.getValue());
		}*/
		
		Date date = new Date();
		int year = date.getYear()+1990;
		int month = date.getMonth()+1;
		int day = date.getDate();
		int dayOfWeek = date.getDay();
		int hour = date.getHours();
		int minute = date.getMinutes();
		/*System.out.println(year+"-"+month+"-"+day+"  "+dayOfWeek+"  "+hour+":"+minute);
		System.out.println(date);
		System.out.println(System.currentTimeMillis());
		System.out.println(new Date().getTime());*/
		
		//String resourceUrl = String.format("%s/", STATIC_RESOURCE_FOLDER);
		//System.out.println(resourceUrl);
		
		String resourceUrl1 = String.format("kk=[{}]", MANIFEST_FILE_PATH);
		System.out.println(resourceUrl1);
		
		//System.out.println(MANIFEST_FILE_PATH);
		
		//String url = TestReflect.class.getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
		//System.out.println(url);
	}
    
}
