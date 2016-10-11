package com.stc.pages;

import org.openqa.selenium.WebElement;

import com.stc.assertionmodel.Verify;

public class MainMenuHeaderPage extends Page {

	public MainMenuHeaderPage() {
		// TODO Auto-generated constructor stub
	}

	public void sellerLinkCheck() throws InterruptedException {

		robot.click(("sellersLink"));
		robot.click(("addseller"));
		Thread.sleep(2000);
	}

	public void viewsellerList() throws InterruptedException {
		robot.click("link=Sellers");
		robot.click("link=View Seller List");

		Thread.sleep(3000);

	}

	public void providerLinkCheck() throws InterruptedException {

		robot.click(("providersLink"));
		robot.click(("addprovider"));
		Thread.sleep(2000);
	}

	public void viewproviderList() throws InterruptedException {
		robot.click("link=Providers");
		robot.click("link=View Provider List");

		Thread.sleep(3000);

	}
}
