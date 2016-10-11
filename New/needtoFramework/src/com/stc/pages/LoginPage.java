package com.stc.pages;


public class LoginPage extends Page {

	public LoginPage login(String userName,String password){
		robot.sendKeys("txtUserId", userName);
		robot.sendKeys("txtPassword", password);
		robot.click("btnLogin");
		
		return this;
	}
}
