package com.stc.pages;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static com.stc.automationengine.WebRobot.robot;
import static com.stc.Logger.GSLogger.gsLogger;

import com.stc.automationengine.WebRobot;


public class Page {
	//This is to avoid static import of robot in each Page class
	protected static WebRobot robot=null;
	protected static HashMap<String,Object>guiMap=null;
	protected static String currentPage=null;
	 
	
	public Page(){
		HashMap<String,String>pageMap=null;	
		Properties p;
			

		if(robot==null)
			robot=WebRobot.robot;
		
		String page=this.getClass().getSimpleName();
		
		if(guiMap==null){
			guiMap=new HashMap<String, Object>();
		}
		
		//If GUI map for this page is not loaded then load it
		if(guiMap.get(page)==null){
			pageMap=new HashMap<String,String>();
		
			//load the Global GUI Map
			p=loadGUIMap("Global");
		
			if(p!=null)
				pageMap.putAll((Map)p);
		
			//load the Page specific GUI Map
			//Any objects present in Global GUI map will be overwritten by Page specific GUI map if found
			//String className=this.getClass().getName();
			
			//p=loadGUIMap(className.substring(className.lastIndexOf('.')+1,className.length()));
				
			p=loadGUIMap(this.getClass().getSimpleName());
			
			if(p!=null)
				pageMap.putAll((Map)p);
			
			//Add the map for this page in GUI map
			guiMap.put(page, pageMap);
			
		}
		
		//Track the current page of application
		//robot.setCurrentPage(this.getClass().getName());
		setCurrentPage(this.getClass().getName());
		
	}
	
	private void setCurrentPage(String pageName) {
		currentPage=pageName;
	}

	public static String getCurrentPage(){
		return currentPage;
	}
	
	public String resolveObject(String locator){
		HashMap<String,String>pageMap=null;
		
		String page=this.getClass().getSimpleName();
		
		//Get the map for this page
		pageMap=(HashMap<String, String>) guiMap.get(page);
		
		if(pageMap== null)
			return locator;
		
		String object=pageMap.get(locator);
		
		//if object is not found return the locator passed in
		if(object==null)
			return locator;

		return object;
	}
	
	private Properties loadGUIMap(String mapName){
		//String className=this.getClass().getName();
		//className=className.substring(className.lastIndexOf('.')+1,className.length());
		try{
			Properties p=new Properties();
			
			p.load(new FileInputStream(System.getProperty("user.dir")+"/resource/object/"+mapName+".properties"));
			
			//tempGUIMap=new HashMap<String,String>((Map)p);
			
			return p;
		}catch(Exception e){
			//For now just print stack trace
			gsLogger.writeERROR("Exception occured-> Page -> loadGUIMap() -> ", e);
			//e.printStackTrace();
		}
		return null;
	}
}
