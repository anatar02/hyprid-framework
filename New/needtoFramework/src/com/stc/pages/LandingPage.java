package com.stc.pages;

public class LandingPage extends Page{

	public LandingPage(){
		
	}
	
	public void openLandingPage(){
		robot.navigate("http://10.52.165.233:8080/mycompany/?blLocaleCode=ko_kr");
		robot.waitForElement("imgFrenchPlace");
	}
	
	public ProductDetailPage selectProduct(String product){
		robot.click(product);
		
		ProductDetailPage productDetailPage=new ProductDetailPage();
		
		robot.waitForElement("btnAddtoCart");
		
		return productDetailPage;
	}
}
