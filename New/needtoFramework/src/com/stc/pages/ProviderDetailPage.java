package com.stc.pages;

public class ProviderDetailPage extends Page{

	public ProviderDetailPage() {
		// TODO Auto-generated constructor stub
	}

	
	
	public void onboardProvider(String Name,String RegistarationNumber,String Address,String PhoneNumber,String CEOName,String RepresentativeName,String RepresentativePhoneNo,String RepresentativeMobileNo,String RepresentativeEmail,String Notes) throws InterruptedException 
	{
            robot.sendKeys("name",Name);
			robot.sendKeys("regNum",RegistarationNumber);
			robot.sendKeys("address",Address);
			robot.sendKeys("phone",PhoneNumber);
			robot.sendKeys("ceo",CEOName);
			robot.sendKeys("repName", RepresentativeName);
			robot.sendKeys("repPhNum",RepresentativePhoneNo);
			robot.sendKeys("repMobNum",RepresentativeMobileNo);
	        robot.sendKeys("remEmail",RepresentativeEmail);
		    robot.sendKeys("notes",Notes);
		Thread.sleep(200);
		}
	
	
	public void viewproviderList() throws InterruptedException
	{
		robot.click(("providersLink"));
		robot.click("link=View Provider List");
		Thread.sleep(3000);
		}
	
}