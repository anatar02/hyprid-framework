package com.stc.pages;

public class PageHeader extends Page{

	public Cart openCart(){
		
		robot.click("cartLink");
		robot.waitForElementVisible("dlgCartPopup");
		
		return new Cart();
	}

	//public SignedOutPage logout() {
		// TODO Auto-generated method stub
		//return null;
	//}
}
