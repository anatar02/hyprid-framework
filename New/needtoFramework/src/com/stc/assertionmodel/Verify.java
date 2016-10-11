package com.stc.assertionmodel;

import static com.stc.automationengine.RestRobot.restRobot;
import static com.stc.automationengine.WebRobot.robot;

import com.stc.automationengine.WebRobot;

public class Verify {
	
	private Verify(){
		
	}
	
	public static void verifyAlertPresent(String passMessage,String failMessage){
		robot.verifyAlertPresent(passMessage,failMessage);
	}
	
	public static void verifyAlertNotPresent(String passMessage,String failMessage){
		robot.verifyAlertNotPresent(passMessage,failMessage);
	}
	
	public static void verifyElementPresent(String element,String passMessage,String failMessage){
		robot.verifyElementPresent(element,passMessage,failMessage);
	}
	
	public static void verifyElementNotPresent(String element,String passMessage,String failMessage){
		robot.verifyElementNotPresent(element,passMessage,failMessage);
	}
	
	public static void verifyElementValue(String localtor,String value,String passMessage,String failMessage){
		robot.verifyElementValue(localtor, value,passMessage,failMessage);
	}
	
	public static void verifyTextBoxValue(String localtor,String value,String passMessage,String failMessage){
		robot.verifyTextBoxValue(localtor, value,passMessage,failMessage);
	}
	
	public static void verifyNumberOfXmlNodesInResponse(String xPath,String expectedValue,String passMessage,String failMessage){
		restRobot.verifyNumberOfXmlNodesInResponse(xPath, expectedValue, passMessage, failMessage);
	}
	
	public static void verifyXMLResponse(String xPath,String expectedValue,String passMessage,String failMessage){
		restRobot.verifyXMLResponse(xPath, expectedValue, passMessage, failMessage);
	}
	public static void verifyTextPresent(String expectedValue,String passMessage,String failMessage){
		robot.verifyTextPresent(expectedValue,passMessage,failMessage);
	}
	
	public static void verifyElementEnabled(String locator,String passMessage,String failMessage){
		robot.verifyElementEnabled(locator, passMessage, failMessage);
	}
	
	public static void verifyElementDisabled(String locator,String passMessage,String failMessage){
		robot.verifyElementDisabled(locator, passMessage, failMessage);
	}
	
	public static void verifyElementVisible(String locator,String passMessage,String failMessage){
		robot.verifyElementVisible(locator,passMessage,failMessage);
	}
	
	public static void verifyElementNotVisible(String locator,String passMessage,String failMessage){
		robot.verifyElementNotVisible(locator,passMessage,failMessage);
	}
	
	public static void verifycheck(String locator,String passMessage,String failMessage){
		robot.verifyChecked(locator,passMessage,failMessage);
	}
	
	public static void verifyNotcheck(String localtor,String passMessage,String failMessage){
		robot.verifyNotChecked(localtor,passMessage,failMessage);
	}
	
	public static void verifySelect(String localtor,String listDropDownOption,String passMessage,String failMessage){
		robot.verifySelectValue(localtor,listDropDownOption,passMessage,failMessage);
	}
}
