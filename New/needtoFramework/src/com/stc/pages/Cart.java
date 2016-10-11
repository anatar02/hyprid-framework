package com.stc.pages;


import static com.stc.Logger.GSLogger.gsLogger;

public class Cart extends Page{

	public Cart(){
		//robot.waitForElement("popupCart");
	}
	
	public double getCartTotal() {
		// TODO Auto-generated method stub
		try{
		String cartTotal= robot.getElementText("subtotal");
		
		String token=cartTotal.substring(0, cartTotal.length()-1);
		
		return new Double(token).doubleValue();
		}catch(Exception e){
			gsLogger.writeERROR("Exception occured-> Cart -> getCartTotal() -> ", e);
		}
		return (double) 0.0;
	}

	public int getNumberOfProductsInCart() {
		// TODO Auto-generated method stub
			try{
				//return new Integer(robot.getTextBoxValue("css=table#cart_products tr:nth-child(1) td:nth-child(3) form input:nth-child(3)")).intValue();
				return new Integer(robot.getTextBoxValue("txtFirstProductQuantity")).intValue();
			}catch(NumberFormatException e){
				gsLogger.writeERROR("Exception occured-> Cart -> getNumberOfProductsInCart() -> ", e);
			}
			return -1;
	}

	public double getProductCost() {
		// TODO Auto-generated method stub
		try{
			String productCost=robot.getElementText("lblFirstProductCost");
		
			String token=productCost.substring(0, productCost.length()-1);
				
			return new Double(token).doubleValue();
		}catch(Exception e){
			gsLogger.writeERROR("Exception occured-> Cart -> getProductCost() -> ", e);
		}
		return (double) 0.0;
	}
	
	public double getProductSubTotal() {
		// TODO Auto-generated method stub
		try{
			String subTotal=robot.getElementText("lblFirstProductSubTotal");
		
			String token=subTotal.substring(0, subTotal.length()-1);
			
			return new Double(token).doubleValue();
		}catch(Exception e){
			gsLogger.writeERROR("Exception occured-> Cart -> getProductSubTotal() -> ", e);
		}
		return (double) 0.0;
	}
	
	public Cart setProuctQuantity(String productQuantity){
		robot.sendKeys("txtFirstProductQuantity", productQuantity);
		robot.submit("btnFirstProductUpdateQuantity");
		robot.waitForElement("lblCartTotal");
		//robot.submit("css=table#cart_products tr:nth-child(1) td:nth-child(3) input[value=\"업데이트\"]");
		
		//Allow time for update
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// Consume
		}
		return this;
	}
	
	public LoginPage checkOut(){
		robot.click("btnCheckout");
		
		LoginPage loginPage=new LoginPage();
		
		robot.waitForElement("btnLogin");
		
		return loginPage;
	}
}
