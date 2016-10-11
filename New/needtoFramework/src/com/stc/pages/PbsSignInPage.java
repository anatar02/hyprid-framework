package com.stc.pages;

import com.stc.assertionmodel.Verify;

public class PbsSignInPage extends Page {

	/**
	 * @param args
	 */
	public PbsSignInPage() {

	}

	public void pbsSignInPage(String Username, String Password)
			throws InterruptedException {
		robot.navigate("http://10.52.165.240:8080/pbs-admin/login");
		robot.sendKeys(("txtUsername"), "");
		robot.sendKeys(("txtUsername"), Username); // resolve logical name here
													// and pass actual object
		robot.sendKeys("txtPassword", Password); // pass logical name
		robot.click(("btnLogin"));

		robot.waitForElementVisible(("pbsname"));

	}

	public void signOut() {
		robot.click(("logout"));
	}

}
