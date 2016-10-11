package com.stc.assertionmodel;

import com.stc.automationengine.WebRobot;

import static com.stc.automationengine.RestRobot.restRobot;
import static com.stc.automationengine.WebRobot.robot;

public class Assert {
	
	private Assert(){
	}
	
	public static void assertElementPresent(String element,String passMessage,String failMessage){
		robot.assertElementPresent(element,passMessage,failMessage);
	}
	
	public static void assertElementEnabled(String locator,String passMessage,String failMessage){
		robot.assertElementEnabled(locator, passMessage, failMessage);
	}
	
	public static void assertElementDisabled(String locator,String passMessage,String failMessage){
		robot.assertElementDisabled(locator, passMessage, failMessage);
	}
	
	public static void assertElementNotPresent(String element,String passMessage,String failMessage){
		robot.assertElementNotPresent(element,passMessage,failMessage);
	}
	
	public static void assertNumberOfXmlNodesInResponse(String xPath,String expectedValue,String passMessage,String failMessage){
		restRobot.assertNumberOfXmlNodesInResponse(xPath, expectedValue, passMessage, failMessage);
	}
	
	public static void assertXMLResponse(String xPath,String expectedValue,String passMessage,String failMessage){
		restRobot.assertXMLResponse(xPath, expectedValue, passMessage, failMessage);
	}
	
   public static void assertElementValue(String locator,String element,String passMessage,String failMessage){
		robot.assertElementValue(locator,element,passMessage,failMessage);
	}
	
	public static void assertTextBoxValue(String locator,String element,String passMessage,String failMessage){
		robot.assertTextBoxValue(locator,element,passMessage,failMessage);
	}
	
	public static void assertTextPresent(String expectedValue,String passMessage,String failMessage){
		robot.assertTextPresent(expectedValue,passMessage,failMessage);
	}
	
	public static void assertElementVisible(String locator,String passMessage,String failMessage){
		robot.assertElementVisible(locator,passMessage,failMessage);
	}
	
	public static void assertElementNotVisible(String locator,String passMessage,String failMessage){
		robot.assertElementNotVisible(locator,passMessage,failMessage);
	}
	
	public static void assertcheck(String locator,String passMessage,String failMessage){
		robot.assertChecked(locator,passMessage,failMessage);
	}
	
	public static void assertNotcheck(String locator,String passMessage,String failMessage){
		robot.assertNotChecked(locator,passMessage,failMessage);
	}
	
	public static void assertSelect(String locator,String listDropDownOption,String passMessage,String failMessage){
		robot.assertSelectValue(locator,listDropDownOption,passMessage,failMessage);
	}
}
