package com.stc.pages;

public class SellerDetailPage extends Page{

	public SellerDetailPage() {
		// TODO Auto-generated constructor stub
	}

	
	
	public void onboardSeller(String Name,String RegistarationNumber,String Address,String PhoneNumber,String CEOName,String RepresentativeName,String RepresentativePhoneNo,String RepresentativeMobileNo,String RepresentativeEmail,String Notes) throws InterruptedException 
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
	
	
	public void viewsellerList() throws InterruptedException
	{
		robot.click(("sellersLink"));
		robot.click("link=View Seller List");
		Thread.sleep(3000);
		}

}