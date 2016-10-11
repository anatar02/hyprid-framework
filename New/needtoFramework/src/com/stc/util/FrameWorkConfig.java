package com.stc.util;

import static com.stc.Logger.GSLogger.gsLogger;
import static com.stc.globals.Constants.*;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class FrameWorkConfig {

	public static HashMap<String, String> configMap = null;

	public static void readConfig() {
		Properties p = null;
		
		try{
			if (configMap == null) {
				configMap = new HashMap<String, String>();
				p = loadConfig(System.getProperty("user.dir")+"/resource/config/"+FRAMEWORK_CONFIG_FILE);
				if (p != null)
					configMap.putAll((Map) p);
			}
		}catch(Exception e){
			gsLogger.writeERROR("Exception occured-> FrameWorkConfig -> readConfig() -> ", e);
		}
	}
	
	
	private static Properties loadConfig(String fileName){
		//String className=this.getClass().getName();
		//className=className.substring(className.lastIndexOf('.')+1,className.length());
		try{
			Properties p=new Properties();
			
			p.load(new FileInputStream(fileName));
			
			//tempGUIMap=new HashMap<String,String>((Map)p);
			
			return p;
		}catch(Exception e){
			gsLogger.writeERROR("Exception occured-> FrameWorkConfig -> loadConfig() -> ", e);
		}
		return null;
	}
	
	public static String getBrowser(){
		String browser=configMap.get("browserName");
		if(browser==null)
			return "IE"; //default to IE
		
		if (browser.equals("FF")){
			return "FF";
		}
		else if (browser.equals("GC")){
			return "GC";
		}
		else {
			return "IE";
		}
	}


	public static int getBrowserTimeOut() {
		String timeout;
		try{
			timeout=configMap.get("browserTimeOut");
			if(timeout==null)
				timeout="10"; //default to 10 seconds
		}catch(Exception e){
			timeout="10"; //default to 10 seconds
			
			gsLogger.writeERROR("Exception occured-> FrameWorkConfig -> getBrowserTimeOut() -> ", e);
		}
		return new Integer(timeout);
	}
}
