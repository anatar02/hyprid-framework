package com.stc.pages;

public class ProductDetailPage extends Page{
	
	public ProductDetailPage (){
		//Need to add wait for element
	}
	
	//Adds the current product to Cart
	public ProductDetailPage addProductToCart(){
		robot.click("btnAddtoCart");
		
		//This is to avaod BLC session expired issue
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
		
		return this;
	}

}
