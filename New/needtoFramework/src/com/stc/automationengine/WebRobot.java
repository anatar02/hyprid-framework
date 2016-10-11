package com.stc.automationengine;

import java.io.File;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.commons.io.FileUtils;

import com.stc.globals.TestType;
import com.stc.pages.Page;
import com.stc.report.XMLReporter;
import com.stc.result.*;

import static com.stc.util.DriverFactory.*;

import static com.stc.report.XMLReporter.*;
import static com.stc.result.TestCaseResult.*;
import static com.stc.Logger.GSLogger.gsLogger;
import static com.stc.pages.Page.getCurrentPage;
import static com.stc.globals.Constants.*;
import static com.stc.result.StepDefinitionResult.*;

public class WebRobot {
	private WebDriver driver;
	private WebDriverWait wait = null;
	private TestCaseResult testcaseResult;
	private StepDefinitionResult stepDefinitionResult;
	private TestStepResult stepResult;
	private static final String SCREENSHOTS_DIR = "screenshots";
	// private static String currentPage=null;
	public static WebRobot robot = new WebRobot();

	private WebRobot() {
		driver = getDriver();
		// driver=getChromeDriver();
		wait = getWait();
		testcaseResult = geTestCaseResult();
		stepDefinitionResult = geStepDefinitionResult();
	}

	/*
	 * public boolean presenceOfElementLocated(String locator){ try{
	 * if(locator.startsWith("css=")){
	 * driver.findElement(By.cssSelector(locator.substring(4))); }else
	 * if(locator.startsWith("//")){ driver.findElement(By.xpath(locator));
	 * }else if(locator.startsWith("name=")){
	 * driver.findElement(By.name(locator.substring(5))); }else
	 * if(locator.startsWith("link=")){
	 * //driver.findElement(By.linkText(locator.substring(5)));
	 * driver.findElement(By.partialLinkText(locator.substring(5))); }else
	 * if(locator.startsWith("class=")){
	 * driver.findElement(By.className(locator.substring(6))); }else {
	 * driver.findElement(By.id(locator)); }
	 * 
	 * return true; }catch(NoSuchElementException e){ return false; } }
	 */

	// Wait for element to be visible or timeout
	public void waitForElementVisible(String locator) {
		try {
			locator = resolveGUIObject(locator);

			if (locator.startsWith("css=")) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By
						.cssSelector(locator.substring(4))));

			} else if (locator.startsWith("//")) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By
						.xpath(locator)));

			} else if (locator.startsWith("name=")) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By
						.name(locator.substring(5))));
			} else if (locator.startsWith("link=")) {
				System.out.println("Searching by link text");
				wait.until(ExpectedConditions.visibilityOfElementLocated(By
						.partialLinkText(locator.substring(5))));

			} else if (locator.startsWith("class=")) {
				System.out.println("Searching by class");
				wait.until(ExpectedConditions.visibilityOfElementLocated(By
						.className(locator.substring(6))));
			} else {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By
						.id(locator)));
			}
			setStepPass("WaitForElementVisible(" + locator + ")");
		} catch (TimeoutException ex) {
			String fileName = takeScreenShot("WaitForElementVisible");
			setStepWarn("WaitForElementVisible(" + locator + ")", ex, fileName);
		} catch (Exception e) {
			String fileName = takeScreenShot("WaitForElementVisible");
			setStepFail("WaitForElementVisible(" + locator + ")", e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> WaitForElementVisible() -> ",
					e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	// Wait for element to be clickable or timeout
	public void waitForElementClickable(String locator) {
		try {
			locator = resolveGUIObject(locator);

			if (locator.startsWith("css=")) {
				wait.until(ExpectedConditions.elementToBeClickable(By
						.cssSelector(locator.substring(4))));

			} else if (locator.startsWith("//")) {
				wait.until(ExpectedConditions.elementToBeClickable(By
						.xpath(locator)));

			} else if (locator.startsWith("name=")) {
				wait.until(ExpectedConditions.elementToBeClickable(By
						.name(locator.substring(5))));

			} else if (locator.startsWith("link=")) {
				wait.until(ExpectedConditions.elementToBeClickable(By
						.partialLinkText(locator.substring(5))));

			} else if (locator.startsWith("class=")) {
				wait.until(ExpectedConditions.elementToBeClickable(By
						.className(locator.substring(6))));

			} else {
				wait.until(ExpectedConditions.elementToBeClickable(By
						.id(locator)));
			}
			setStepPass("WaitForElementClickable(" + locator + ")");
		} catch (TimeoutException ex) {
			String fileName = takeScreenShot("WaitForElementClickable");
			setStepWarn("WaitForElementClickable(" + locator + ")", ex,
					fileName);
		} catch (Exception ex) {
			String fileName = takeScreenShot("WaitForElementClickable");
			setStepFail("WaitForElementClickable(" + locator + ")", ex,
					fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> waitForElementClickable() -> ",
					ex);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	public void waitForElement(String locator) {
		try {
			locator = resolveGUIObject(locator);

			if (locator.startsWith("css=")) {
				wait.until(ExpectedConditions.presenceOfElementLocated(By
						.cssSelector(locator.substring(4))));

			} else if (locator.startsWith("//")) {
				wait.until(ExpectedConditions.presenceOfElementLocated(By
						.xpath(locator)));

			} else if (locator.startsWith("name=")) {
				wait.until(ExpectedConditions.presenceOfElementLocated(By
						.name(locator.substring(5))));
			} else if (locator.startsWith("link=")) {
				wait.until(ExpectedConditions.presenceOfElementLocated(By
						.partialLinkText(locator.substring(5))));

			} else if (locator.startsWith("class=")) {
				wait.until(ExpectedConditions.presenceOfElementLocated(By
						.className(locator.substring(6))));
			} else {
				wait.until(ExpectedConditions.presenceOfElementLocated(By
						.id(locator)));
			}

			setStepPass("WaitForElement(" + locator + ")");
		} catch (TimeoutException ex) {
			String fileName = takeScreenShot("WaitForElement");
			setStepWarn("WaitForElement(" + locator + ")", ex, fileName);
		} catch (Exception e) {
			String fileName = takeScreenShot("WaitForElement");
			setStepFail("WaitForElement(" + locator + ")", e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> waitForElement() -> ", e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	public void sendKeys(String locator, String text) {
		try {
			locator = resolveGUIObject(locator);

			WebElement e = getElement(locator);
			e.clear();
			e.sendKeys(text);

			setStepPass("SendKeys(" + locator + "," + text + ")");
		} catch (Exception e) {
			String fileName = takeScreenShot("SendKeys");
			setStepFail("SendKeys(" + locator + "," + text + ")", e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> sendKeys() -> ", e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}

	}

	// This method is added for debugging purpose
	public void writePageSource() {
		gsLogger.writeINFO("PageTitle " + driver.getTitle());
		gsLogger.writeINFO("PageSource " + driver.getPageSource());
		gsLogger.writeINFO("GETTING MORE WINDOWS");
		Set<String> windows = driver.getWindowHandles();

		Iterator<String> i = windows.iterator();
		while (i.hasNext()) {
			gsLogger.writeINFO("ONE MORE WINDOW");
			String handle = i.next();

			driver.switchTo().window(handle);

			gsLogger.writeINFO("PageTitle " + driver.getTitle());
			gsLogger.writeINFO("PageSource " + driver.getPageSource());
		}
	}

	public void navigate(String url) {
		try {
			driver.get(url);

			setStepPass("Navigate(" + url + ")");
		} catch (Exception e) {
			String fileName = takeScreenShot("Navigate");
			setStepFail("Navigate(" + url + ")", e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> navigate() -> ", e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	public void click(String locator) {
		try {
			locator = resolveGUIObject(locator);

			WebElement e = getElement(locator);

			Actions builder = new Actions(driver);
			builder.moveToElement(e).click(e).perform();

			// e.click();

			setStepPass("Click(" + locator + ")");
		} catch (Exception e) {
			String fileName = takeScreenShot("Click");
			setStepFail("Click(" + locator + ")", e, fileName);
			gsLogger.writeERROR("Exception occured-> WebRobot -> click() -> ",
					e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	public void submit(String locator) {
		try {
			locator = resolveGUIObject(locator);

			WebElement e = getElement(locator);
			e.submit();

			setStepPass("Submit(" + locator + ")");
		} catch (Exception e) {
			String fileName = takeScreenShot("Submit");
			setStepFail("Submit(" + locator + ")", e, fileName);
			gsLogger.writeERROR("Exception occured-> WebRobot -> submit() -> ",
					e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	public void doubleClick(String locator) {
		try {
			locator = resolveGUIObject(locator);

			WebElement e = getElement(locator);
			Actions a = new Actions(driver);
			a.doubleClick(e).build().perform();

			setStepPass("DoubleClick(" + locator + ")");
		} catch (Exception e) {
			String fileName = takeScreenShot("DoubleClick");
			setStepFail("DoubleClick(" + locator + ")", e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> doubleClick() -> ", e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	// Performs a context-click at the current mouse location.
	public void contextClick() {
		try {
			Actions a = new Actions(driver);
			a.contextClick().build().perform();

			setStepPass("ContextClick(At Current Mouse Location)");
		} catch (Exception e) {
			String fileName = takeScreenShot("ContextClick");
			setStepFail("ContextClick(At Current Mouse Location)", e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> ContextClick() -> ", e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	public void contextClick(String locator) {
		try {
			locator = resolveGUIObject(locator);

			WebElement e = getElement(locator);
			Actions a = new Actions(driver);
			a.contextClick(e).build().perform();

			setStepPass("ContextClick(" + locator + ")");
		} catch (Exception e) {
			String fileName = takeScreenShot("Click");
			setStepFail("ContextClick(" + locator + ")", e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> contextClick() -> ", e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	// Verify alert is present
	public void verifyAlertPresent(String passMessage, String failMessage) {
		try {
			driver.switchTo().alert();
			setStepPass("VerifyAlertPresent() [" + passMessage + "]");
		} catch (Exception e) {
			String fileName = takeScreenShot("VerifyAlertPresent");
			setStepFail("VerifyAlertPresent() [" + failMessage + "]", e,
					fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> verifyAlertPresent() -> ",
					e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	// verify element is present
	public void verifyElementPresent(String locator, String passMessage,
			String failMessage) {
		try {
			locator = resolveGUIObject(locator);
			getElement(locator);
			setStepPass("VerifyElementPresent(" + locator + ") [" + passMessage
					+ "]");
		} catch (Exception e) {
			String fileName = takeScreenShot("VerifyElementPresent");
			setStepFail("VerifyElementPresent(" + locator + ") [" + failMessage
					+ "]", e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> verifyElementPresent() -> ",
					e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	// verify element is enabled
	public void verifyElementEnabled(String locator, String passMessage,
			String failMessage) {
		try {
			locator = resolveGUIObject(locator);

			WebElement e = getElement(locator);
			if (e.isEnabled())
				setStepPass("VerifyElementEnabled(" + locator + ") ["
						+ passMessage + "]");
			else
				throw new RuntimeException(failMessage);
		} catch (Exception e) {
			String fileName = takeScreenShot("VerifyElementEnabled");
			setStepFail("VerifyElementEnabled(" + locator + ") [" + failMessage
					+ "]", e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> verifyElementEnabled() -> ",
					e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	// verify element is disabled
	public void verifyElementDisabled(String locator, String passMessage,
			String failMessage) {
		try {
			locator = resolveGUIObject(locator);

			WebElement e = getElement(locator);
			if (e.isEnabled())
				throw new RuntimeException(failMessage);
			else
				setStepPass("VerifyElementDisabled(" + locator + ") ["
						+ passMessage + "]");

		} catch (Exception e) {
			String fileName = takeScreenShot("VerifyElementDisabled");
			setStepFail("VerifyElementDisabled(" + locator + ") ["
					+ failMessage + "]", e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> verifyElementDisabled() -> ",
					e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	// verify value of given element
	public void verifyElementValue(String locator, String expectedValue,
			String passMessage, String failMessage) {
		try {
			locator = resolveGUIObject(locator);

			WebElement e = getElement(locator);
			String actualValue = e.getText();

			if (expectedValue.equals(actualValue)) {
				setStepPass("VerifyElementValue(" + locator + ","
						+ expectedValue + ") [" + passMessage + "]");
			} else {
				String fileName = takeScreenShot("VerifyElementValue");
				setStepFail("VerifyElementValue(" + locator + ","
						+ expectedValue + ") [" + failMessage + "]",
						new Exception("Expected: " + expectedValue + " Got: "
								+ actualValue), fileName);
			}
		} catch (Exception e) {
			String fileName = takeScreenShot("VerifyElementValue");
			setStepFail("VerifyElementValue(" + locator + "," + expectedValue
					+ ") [" + failMessage + "]", e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> verifyElementValue() -> ",
					e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	// verify text present inside an textbox
	public void verifyTextBoxValue(String locator, String expectedValue,
			String passMessage, String failMessage) {
		try {
			locator = resolveGUIObject(locator);

			WebElement e = getElement(locator);
			String actualValue = e.getAttribute("value");

			if (expectedValue.equals(actualValue)) {
				setStepPass("VerifyTextBoxValue(" + locator + ","
						+ expectedValue + ") [" + passMessage + "]");
			} else {
				String fileName = takeScreenShot("VerifyTextBoxValue");
				setStepFail("VerifyTextBoxValue(" + locator + ","
						+ expectedValue + ") [" + failMessage + "]",
						new Exception("Expected: " + expectedValue + " Got: "
								+ actualValue), fileName);
			}
		} catch (Exception e) {
			String fileName = takeScreenShot("VerifyTextBoxValue");
			setStepFail("VerifyTextBoxValue(" + locator + "," + expectedValue
					+ ") [" + failMessage + "]", e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> verifyTextBoxValue() -> ",
					e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	// Verify text is present on page
	public void verifyTextPresent(String expectedValue, String passMessage,
			String failMessage) {
		try {
			if (driver.getPageSource().contains(expectedValue)) {
				setStepPass("VerifyTextPresent(" + expectedValue + ") ["
						+ passMessage + "]");
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			String fileName = takeScreenShot("VerifyTextPresent");
			setStepFail("VerifyTextPresent(" + expectedValue + ") ["
					+ failMessage + "]", e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> verifyTextPresent() -> ",
					e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	// Verify alert is not present
	public void verifyAlertNotPresent(String passMessage, String failMessage) {
		try {
			driver.switchTo().alert();
			String fileName = takeScreenShot("VerifyAlertNotPresent");
			setStepFail("VerifyAlertNotPresent() [" + failMessage + "]",
					new Exception("Alert present"), fileName);
		} catch (NoAlertPresentException a) {
			setStepPass("VerifyAlertNotPresent() [" + passMessage + "]");
		} catch (Exception e) {
			String fileName = takeScreenShot("VerifyAlertNotPresent");
			setStepFail("VerifyAlertNotPresent() [" + failMessage + "]", e,
					fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> verifyAlertNotPresent() -> ",
					e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	// verify element is not present
	public void verifyElementNotPresent(String locator, String passMessage,
			String failMessage) {
		try {
			locator = resolveGUIObject(locator);

			getElement(locator);
			String fileName = takeScreenShot("VerifyElementNotPresent");
			setStepFail("VerifyElementNotPresent(" + locator + ") ["
					+ failMessage + "]", new Exception("Element " + locator
					+ " present"), fileName);
		} catch (Exception e) {
			setStepPass("VerifyElementNotPresent(" + locator + ") ["
					+ passMessage + "]");
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	// Verify element is visible
	public void verifyElementVisible(String locator, String passMessage,
			String failMessage) {

		try {
			locator = resolveGUIObject(locator);

			if (locator.startsWith("css=")) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By
						.cssSelector(locator.substring(4))));

			} else if (locator.startsWith("//")) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By
						.xpath(locator)));

			} else if (locator.startsWith("name=")) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By
						.name(locator.substring(5))));
			} else if (locator.startsWith("link=")) {
				System.out.println("Searching by link text");
				wait.until(ExpectedConditions.visibilityOfElementLocated(By
						.partialLinkText(locator.substring(5))));

			} else if (locator.startsWith("class=")) {
				System.out.println("Searching by class");
				wait.until(ExpectedConditions.visibilityOfElementLocated(By
						.className(locator.substring(6))));
			} else {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By
						.id(locator)));
			}
			setStepPass("VerifyElementVisible(" + locator + ") [" + passMessage
					+ "]");
		} catch (TimeoutException ex) {
			String fileName = takeScreenShot("VerifyElementVisible");
			setStepWarn("VerifyElementVisible(" + locator + ")", ex, fileName);
		} catch (Exception e) {
			String fileName = takeScreenShot("VerifyElementVisible");
			setStepFail("VerifyElementVisible(" + locator + ") [" + failMessage
					+ "]", e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> verifyElementVisible() -> ",
					e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	// Verify element is not visible
	public void verifyElementNotVisible(String locator, String passMessage,
			String failMessage) {
		try {
			locator = resolveGUIObject(locator);

			if (locator.startsWith("css=")) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By
						.cssSelector(locator.substring(4))));

			} else if (locator.startsWith("//")) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By
						.xpath(locator)));

			} else if (locator.startsWith("name=")) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By
						.name(locator.substring(5))));
			} else if (locator.startsWith("link=")) {
				System.out.println("Searching by link text");
				wait.until(ExpectedConditions.visibilityOfElementLocated(By
						.partialLinkText(locator.substring(5))));

			} else if (locator.startsWith("class=")) {
				System.out.println("Searching by class");
				wait.until(ExpectedConditions.visibilityOfElementLocated(By
						.className(locator.substring(6))));
			} else {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By
						.id(locator)));
			}

			String fileName = takeScreenShot("VerifyElementNotVisible");
			setStepFail("VerifyElementNotVisible(" + locator + ") ["
					+ failMessage + "]", new Exception("Element " + locator
					+ " visible"), fileName);
		} catch (TimeoutException ex) {
			setStepPass("VerifyElementNotVisible(" + locator + ") ["
					+ passMessage + "]");
		} catch (Exception e) {
			String fileName = takeScreenShot("VerifyElementNotVisible");
			setStepFail("VerifyElementNotVisible(" + locator + ") ["
					+ failMessage + "]", e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> verifyElementNotVisible() -> ",
					e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	// verify if check box is checked
	public void verifyChecked(String locator, String passMessage,
			String failMessage) {
		try {
			locator = resolveGUIObject(locator);

			WebElement e = getElement(locator);

			if (e.isSelected()) {
				setStepPass("VerifyChecked(" + locator + ") [" + passMessage
						+ "]");
			} else {
				throw new Exception("Element " + locator + " not checked");
			}
		} catch (Exception e) {
			String fileName = takeScreenShot("VerifyChecked");
			setStepFail("VerifyChecked(" + locator + ") [" + failMessage + "]",
					e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> verifyChecked() -> ", e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	// verify if check box is not checked
	public void verifyNotChecked(String locator, String passMessage,
			String failMessage) {
		try {
			locator = resolveGUIObject(locator);

			WebElement e = getElement(locator);

			if (!e.isSelected()) {
				setStepPass("VerifyNotChecked(" + locator + ") [" + passMessage
						+ "]");
			} else {
				throw new Exception("Element " + locator + " checked");
			}
		} catch (Exception e) {
			String fileName = takeScreenShot("VerifyNotChecked");
			setStepFail("VerifyNotChecked(" + locator + ") [" + failMessage
					+ "]", e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> verifyNotChecked() -> ", e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	// /verify value selected in dropdown
	public void verifySelectValue(String locator, String listDropDownOption,
			String passMessage, String failMessage) {
		try {
			locator = resolveGUIObject(locator);

			WebElement e = getElement(locator);
			Select lstDropDown = new Select(e);

			if (listDropDownOption.startsWith("label=")) {
				lstDropDown.selectByVisibleText(locator.substring(6));
				setStepPass("VerifySelectValue(" + locator + ") ["
						+ passMessage + "]");
			}

			if (listDropDownOption.startsWith("value=")) {
				lstDropDown.selectByValue(locator.substring(6));
				setStepPass("VerifySelectValue(" + locator + ") ["
						+ passMessage + "]");
			}

			if (listDropDownOption.startsWith("index=")) {
				lstDropDown.selectByIndex(new Integer(locator.substring(6)));
				setStepPass("VerifySelectValue(" + locator + ") ["
						+ passMessage + "]");
			}

			lstDropDown.selectByVisibleText(locator);
			setStepPass("VerifySelectValue(" + locator + ","
					+ listDropDownOption + ") [" + passMessage + "]");
		} catch (Exception e) {
			String fileName = takeScreenShot("Select");
			setStepFail("VerifySelectValue(" + locator + ","
					+ listDropDownOption + ") [" + failMessage + "]", e,
					fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> verifySelectValue("
							+ locator + ") -> ", e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	// assert element is present
	public void assertElementPresent(String locator, String passMessage,
			String failMessage) {
		try {
			locator = resolveGUIObject(locator);
			getElement(locator);
			setStepPass("AssertElementPresent(" + locator + ") [" + passMessage
					+ "]");
		} catch (Exception e) {
			String fileName = takeScreenShot("AssertElementPresent");
			setStepFail("AssertElementPresent(" + locator + ") [" + failMessage
					+ "]", e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> assertElementPresent() -> ",
					e);
			// Throw again so TestNG can abort the testcase
			throw new RuntimeException(e.getMessage());
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	// assert element is enabled
	public void assertElementEnabled(String locator, String passMessage,
			String failMessage) {
		try {
			locator = resolveGUIObject(locator);

			WebElement e = getElement(locator);
			if (e.isEnabled())
				setStepPass("AssertElementEnabled(" + locator + ") ["
						+ passMessage + "]");
			else
				throw new RuntimeException(failMessage);
		} catch (Exception e) {
			String fileName = takeScreenShot("AssertElementEnabled");
			setStepFail("AssertElementEnabled(" + locator + ") [" + failMessage
					+ "]", e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> assertElementEnabled() -> ",
					e);

			// Throw again so TestNG can abort the testcase
			throw new RuntimeException(e.getMessage());
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	// assert element is disabled
	public void assertElementDisabled(String locator, String passMessage,
			String failMessage) {
		try {
			locator = resolveGUIObject(locator);

			WebElement e = getElement(locator);
			if (e.isEnabled())
				throw new RuntimeException(failMessage);
			else
				setStepPass("AssertElementDisabled(" + locator + ") ["
						+ passMessage + "]");

		} catch (Exception e) {
			String fileName = takeScreenShot("AssertElementDisabled");
			setStepFail("AssertElementDisabled(" + locator + ") ["
					+ failMessage + "]", e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> assertElementDisabled() -> ",
					e);

			// Throw again so TestNG can abort the testcase
			throw new RuntimeException(e.getMessage());
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	// assert element is not present
	public void assertElementNotPresent(String locator, String passMessage,
			String failMessage) {
		boolean assertFail = false;
		try {
			locator = resolveGUIObject(locator);

			getElement(locator);
			Exception ex = new RuntimeException("Element " + locator
					+ " present");
			String fileName = takeScreenShot("AssertElementNotPresent");
			setStepFail("AssertElementNotPresent(" + locator + ") ["
					+ failMessage + "]", ex, fileName);
			assertFail = true;
			// throwing above object ex forces to handle it at compile time when
			// we throw it again from catch block
			throw new RuntimeException("Element " + locator + " present");
		} catch (Exception e) {
			if (!assertFail) {
				setStepPass("AssertElementNotPresent(" + locator + ") ["
						+ passMessage + "]");
			} else {
				throw new RuntimeException(e.getMessage());
			}
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	// assert value of given element
	public void assertElementValue(String locator, String expectedValue,
			String passMessage, String failMessage) {
		try {
			locator = resolveGUIObject(locator);

			WebElement e = getElement(locator);
			String actualValue = e.getText();

			if (expectedValue.equals(actualValue)) {
				setStepPass("AssertElementValue(" + locator + ","
						+ expectedValue + ") [" + passMessage + "]");
			} else {
				throw new RuntimeException("Expected: " + expectedValue
						+ " Got: " + actualValue);
			}
		} catch (Exception e) {
			String fileName = takeScreenShot("AssertElementValue");
			setStepFail("AssertElementValue(" + locator + "," + expectedValue
					+ ") [" + failMessage + "]", e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> assertElementValue() -> ",
					e);
			// Throw again so TestNG can abort the testcase
			throw new RuntimeException(e.getMessage());
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	// Assert TextBox Value is present
	public void assertTextBoxValue(String locator, String expectedValue,
			String passMessage, String failMessage) {
		try {
			locator = resolveGUIObject(locator);

			WebElement e = getElement(locator);
			String actualValue = e.getAttribute("value");

			if (expectedValue.equals(actualValue)) {
				setStepPass("AssertTextBoxValue(" + locator + ","
						+ expectedValue + ") [" + passMessage + "]");
			} else {
				throw new RuntimeException("Expected: " + expectedValue
						+ " Got: " + actualValue);
			}
		} catch (Exception e) {
			String fileName = takeScreenShot("AssertTextBoxValue");
			setStepFail("AssertTextBoxValue(" + locator + "," + expectedValue
					+ ") [" + failMessage + "]", e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> assertTextBoxValue() -> ",
					e);
			// Throw again so TestNG can abort the testcase
			throw new RuntimeException(e.getMessage());
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	// Assert text is present on page
	public void assertTextPresent(String expectedValue, String passMessage,
			String failMessage) {
		try {
			if (driver.getPageSource().contains(expectedValue)) {
				setStepPass("AssertTextPresent(" + expectedValue + ") ["
						+ passMessage + "]");
			} else {
				throw new RuntimeException("Expected Value: " + expectedValue
						+ " not present");
			}
		} catch (Exception e) {
			String fileName = takeScreenShot("AssertTextPresent");
			setStepFail("AssertTextPresent(" + expectedValue + ") ["
					+ failMessage + "]", e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> assertTextPresent() -> ",
					e);
			// Throw again so TestNG can abort the testcase
			throw new RuntimeException(e.getMessage());
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	// Assert element is visible
	public void assertElementVisible(String locator, String passMessage,
			String failMessage) {

		try {
			locator = resolveGUIObject(locator);

			if (locator.startsWith("css=")) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By
						.cssSelector(locator.substring(4))));

			} else if (locator.startsWith("//")) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By
						.xpath(locator)));

			} else if (locator.startsWith("name=")) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By
						.name(locator.substring(5))));
			} else if (locator.startsWith("link=")) {
				System.out.println("Searching by link text");
				wait.until(ExpectedConditions.visibilityOfElementLocated(By
						.partialLinkText(locator.substring(5))));

			} else if (locator.startsWith("class=")) {
				System.out.println("Searching by class");
				wait.until(ExpectedConditions.visibilityOfElementLocated(By
						.className(locator.substring(6))));
			} else {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By
						.id(locator)));
			}
			setStepPass("AssertElementVisible(" + locator + ") [" + passMessage
					+ "]");
		} catch (TimeoutException ex) {
			String fileName = takeScreenShot("AssertElementVisible");
			setStepWarn("AssertElementVisible(" + locator + ")", ex, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> assertElementVisible() -> ",
					ex);
			// Throw again so TestNG can abort the testcase
			throw ex;
		} catch (Exception e) {
			String fileName = takeScreenShot("AssertElementVisible");
			setStepFail("AssertElementVisible(" + locator + ") [" + failMessage
					+ "]", e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> assertElementVisible() -> ",
					e);
			// Throw again so TestNG can abort the testcase
			throw new RuntimeException(e.getMessage());
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	// Assert element is not visible
	public void assertElementNotVisible(String locator, String passMessage,
			String failMessage) {
		try {
			locator = resolveGUIObject(locator);

			if (locator.startsWith("css=")) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By
						.cssSelector(locator.substring(4))));

			} else if (locator.startsWith("//")) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By
						.xpath(locator)));

			} else if (locator.startsWith("name=")) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By
						.name(locator.substring(5))));
			} else if (locator.startsWith("link=")) {
				System.out.println("Searching by link text");
				wait.until(ExpectedConditions.visibilityOfElementLocated(By
						.partialLinkText(locator.substring(5))));

			} else if (locator.startsWith("class=")) {
				System.out.println("Searching by class");
				wait.until(ExpectedConditions.visibilityOfElementLocated(By
						.className(locator.substring(6))));
			} else {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By
						.id(locator)));
			}

			Exception ex = new RuntimeException("Element " + locator
					+ " visible");
			String fileName = takeScreenShot("AssertElementNotVisible");
			setStepFail("AssertElementNotVisible(" + locator + ") ["
					+ failMessage + "]", ex, fileName);
			// throwing above object ex forces to handle it at compile time when
			// we throw it again from catch block
			throw new RuntimeException("Element " + locator + " visible");
		} catch (TimeoutException ex) {
			String fileName = takeScreenShot("AssertElementNotVisible");
			setStepPass("AssertElementNotVisible(" + locator + ") ["
					+ passMessage + "]");
			// Throw again so TestNG can abort the testcase
			throw ex;
		} catch (Exception e) {
			String fileName = takeScreenShot("AssertElementNotVisible");
			setStepFail("AssertElementNotVisible(" + locator + ") ["
					+ failMessage + "]", e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> assertElementNotVisible() -> ",
					e);
			// Throw again so TestNG can abort the testcase
			throw new RuntimeException(e.getMessage());
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	// assert check box is checked
	public void assertChecked(String locator, String passMessage,
			String failMessage) {
		try {
			locator = resolveGUIObject(locator);

			WebElement e = getElement(locator);

			if (e.isSelected()) {
				setStepPass("AssertChecked(" + locator + ") [" + passMessage
						+ "]");
			} else {
				throw new RuntimeException("Element " + locator
						+ " not checked");
			}
		} catch (Exception e) {
			String fileName = takeScreenShot("AssertChecked");
			setStepFail("AssertChecked(" + locator + ") [" + failMessage + "]",
					e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> assertcheck() -> ", e);
			// Throw again so TestNG can abort the testcase
			throw new RuntimeException(e.getMessage());
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	// assert checkbox is not checked
	public void assertNotChecked(String locator, String passMessage,
			String failMessage) {
		try {
			locator = resolveGUIObject(locator);

			WebElement e = getElement(locator);

			if (!e.isSelected()) {
				setStepPass("AssertNotChecked(" + locator + ") [" + passMessage
						+ "]");
			} else {
				throw new RuntimeException("Element " + locator + " checked");
			}
		} catch (Exception e) {
			String fileName = takeScreenShot("AssertNotChecked");
			setStepFail("AssertNotChecked(" + locator + ") [" + failMessage
					+ "]", e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> assertNotChecked() -> ", e);
			// Throw again so TestNG can abort the testcase
			throw new RuntimeException(e.getMessage());
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	public void assertSelectValue(String locator, String listDropDownOption,
			String passMessage, String failMessage) {
		try {
			locator = resolveGUIObject(locator);

			WebElement e = getElement(locator);
			Select lstDropDown = new Select(e);

			if (listDropDownOption.startsWith("label=")) {
				lstDropDown.selectByVisibleText(locator.substring(6));
				setStepPass("AssertSelectValue(" + locator + ") ["
						+ passMessage + "]");
			}

			if (listDropDownOption.startsWith("value=")) {
				lstDropDown.selectByValue(locator.substring(6));
				setStepPass("AssertSelectValue(" + locator + ")  ["
						+ passMessage + "]");
			}

			if (listDropDownOption.startsWith("index=")) {
				lstDropDown.selectByIndex(new Integer(locator.substring(6)));
				setStepPass("AssertSelectValue(" + locator + ") ["
						+ passMessage + "]");
			}

			lstDropDown.selectByVisibleText(locator);
			setStepPass("AssertSelectValue(" + locator + ","
					+ listDropDownOption + ") [" + passMessage + "]");
		} catch (Exception e) {
			String fileName = takeScreenShot("AssertSelectValue");
			setStepFail("AssertSelectValue(" + locator + ","
					+ listDropDownOption + ") [" + failMessage + "]", e,
					fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> assertSelectValue("
							+ locator + ") -> ", e);
			// Throw again so TestNG can abort the testcase
			throw new RuntimeException(e.getMessage());
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	public void mouseOver(String locator) {
		try {
			locator = resolveGUIObject(locator);

			WebElement e = getElement(locator);
			Actions a = new Actions(driver);
			a.moveToElement(e).build().perform();
			setStepPass("MouseOver(" + locator + ")");
		} catch (Exception e) {
			String fileName = takeScreenShot("MouseOver");
			setStepFail("MouseOver(" + locator + ")", e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> mouseOver() -> ", e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	public void doubleClick() {
		try {
			Actions a = new Actions(driver);
			a.doubleClick().build().perform();

			setStepPass("DoubleClick(At Current Mouse Location)");
		} catch (Exception e) {
			String fileName = takeScreenShot("DoubleClick");
			setStepFail("DoubleClick(At Current Mouse Location)", e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> doubleClick() -> ", e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	// Performs a modifier key Down Press.
	public void keyDown() {
		try {
			Actions a = new Actions(driver);
			a.keyDown(Keys.DOWN).build().perform();

			setStepPass("KeyDown()");
		} catch (Exception e) {
			String fileName = takeScreenShot("KeyDown");
			setStepFail("KeyDown()", e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> keyDown() -> ", e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	// Performs a modifier key Down press after focusing on an element.
	public void keyDown(String locator) {
		try {
			locator = resolveGUIObject(locator);

			WebElement e = getElement(locator);
			Actions a = new Actions(driver);
			a.keyDown(e, Keys.DOWN).build().perform();

			setStepPass("KeyDown(" + locator + ")");
		} catch (Exception e) {
			String fileName = takeScreenShot("KeyDown(" + locator + ")");
			setStepFail("KeyDown(" + locator + ")", e, fileName);
			gsLogger.writeERROR("Exception occured-> WebRobot -> keyDown("
					+ locator + ") -> ", e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	// Performs a modifier key Up Press.
	public void keyUp() {
		try {
			Actions a = new Actions(driver);
			a.keyUp(Keys.UP).build().perform();

			setStepPass("KeyUP()");
		} catch (Exception e) {
			String fileName = takeScreenShot("KeyUp");
			setStepFail("KeyUp()", e, fileName);
			gsLogger.writeERROR("Exception occured-> WebRobot -> keyUp() -> ",
					e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	// Performs a modifier key Up press after focusing on an element.
	public void keyUp(String locator) {
		try {
			locator = resolveGUIObject(locator);

			WebElement e = getElement(locator);
			Actions a = new Actions(driver);
			a.keyUp(e, Keys.UP).build().perform();

			setStepPass("KeyUp(" + locator + ")");
		} catch (Exception e) {
			String fileName = takeScreenShot("KeyUp(" + locator + ")");
			setStepFail("KeyUp(" + locator + ")", e, fileName);
			gsLogger.writeERROR("Exception occured-> WebRobot -> keyUp("
					+ locator + ") -> ", e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	// Clicks (without releasing) at the current mouse location.
	public void clickAndHold() {
		try {
			Actions a = new Actions(driver);
			a.clickAndHold().build().perform();

			setStepPass("ClickAndHold()");
		} catch (Exception e) {
			String fileName = takeScreenShot("ClickAndHold");
			setStepFail("ClickAndHold()", e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> clickAndHold() -> ", e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	// Clicks (without releasing) in the middle of the given element.
	public void clickAndHold(String locator) {
		try {
			locator = resolveGUIObject(locator);

			WebElement e = getElement(locator);
			Actions a = new Actions(driver);
			a.clickAndHold(e).build().perform();

			setStepPass("ClickAndHold(" + locator + ")");
		} catch (Exception e) {
			String fileName = takeScreenShot("ClickAndHold(" + locator + ")");
			setStepFail("ClickAndHold(" + locator + ")", e, fileName);
			gsLogger.writeERROR("Exception occured-> WebRobot -> clickAndHold("
					+ locator + ") -> ", e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	// Releases the depressed left mouse button at the current mouse location.
	public void release() {
		try {
			Actions a = new Actions(driver);
			a.release().build().perform();

			setStepPass("Release()");
		} catch (Exception e) {
			String fileName = takeScreenShot("Release");
			setStepFail("Release()", e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> release() -> ", e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	// Releases the depressed left mouse button, in the middle of the given
	// element.
	public void release(String locator) {
		try {
			locator = resolveGUIObject(locator);

			WebElement e = getElement(locator);
			Actions a = new Actions(driver);
			a.release(e).build().perform();

			setStepPass("Release(" + locator + ")");
		} catch (Exception e) {
			String fileName = takeScreenShot("Release(" + locator + ")");
			setStepFail("Release(" + locator + ")", e, fileName);
			gsLogger.writeERROR("Exception occured-> WebRobot -> release("
					+ locator + ") -> ", e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	// Clicks at the current mouse location.
	public void click() {
		try {
			Actions a = new Actions(driver);
			a.click().build().perform();

			setStepPass("Click()");
		} catch (Exception e) {
			String fileName = takeScreenShot("Click");
			setStepFail("Click()", e, fileName);
			gsLogger.writeERROR("Exception occured-> WebRobot -> click() -> ",
					e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	// Moves the mouse to the middle of the element.
	public void moveToElement(String locator) {
		try {
			locator = resolveGUIObject(locator);

			WebElement e = getElement(locator);
			Actions a = new Actions(driver);
			a.moveToElement(e).build().perform();

			setStepPass("MoveToElement(" + locator + ")");
		} catch (Exception e) {
			String fileName = takeScreenShot("MoveToElement(" + locator + ")");
			setStepFail("MoveToElement(" + locator + ")", e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> moveToElement(" + locator
							+ ") -> ", e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	// Moves the mouse to an offset from the top-left corner of the element.
	public void moveToElement(String locator, int xOffset, int yOffset) {
		try {
			locator = resolveGUIObject(locator);

			WebElement e = getElement(locator);
			Actions a = new Actions(driver);
			a.moveToElement(e, xOffset, yOffset).build().perform();

			setStepPass("MoveToElement(" + locator + "," + xOffset + ","
					+ xOffset + ")");
		} catch (Exception e) {
			String fileName = takeScreenShot("MoveToElement(" + locator + ")");
			setStepFail("MoveToElement(" + locator + "," + xOffset + ","
					+ xOffset + ")", e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> moveToElement(" + locator
							+ ") -> ", e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	// Moves the mouse from its current position (or 0,0) by the given offset.
	public void moveByOffset(int xOffset, int yOffset) {
		try {
			Actions a = new Actions(driver);
			a.moveByOffset(xOffset, yOffset).build().perform();

			setStepPass("MoveByOffset(" + xOffset + "," + xOffset + ")");
		} catch (Exception e) {
			String fileName = takeScreenShot("MoveByOffset");
			setStepFail("MoveByOffset(" + xOffset + "," + xOffset + ")", e,
					fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> moveByOffset() -> ", e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	// A convenience method that performs click-and-hold at the location of the
	// source element,
	// moves to the location of the target element, then releases the mouse.
	public void dragAndDrop(String sourceElement, String targetElement) {
		try {
			sourceElement = resolveGUIObject(sourceElement);
			targetElement = resolveGUIObject(targetElement);

			WebElement se = getElement(sourceElement);
			WebElement te = getElement(targetElement);
			Actions a = new Actions(driver);
			a.dragAndDrop(se, te).build().perform();

			setStepPass("DragAndDrop(" + sourceElement + "," + targetElement
					+ ")");
		} catch (Exception e) {
			String fileName = takeScreenShot("DragAndDrop");
			setStepFail("DragAndDrop(" + sourceElement + "," + targetElement
					+ ")", e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> dragAndDrop() -> ", e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	// A convenience method that performs click-and-hold at the location of the
	// source element,
	// moves by a given offset, then releases the mouse.
	public void dragAndDropBy(String locator, int xOffset, int yOffset) {
		try {
			locator = resolveGUIObject(locator);

			WebElement e = getElement(locator);
			Actions a = new Actions(driver);
			a.dragAndDropBy(e, xOffset, yOffset).build().perform();

			setStepPass("DragAndDropBy(" + locator + "," + xOffset + ","
					+ yOffset + ")");
		} catch (Exception e) {
			String fileName = takeScreenShot("DragAndDropBy");
			setStepFail("DragAndDropBy(" + locator + "," + xOffset + ","
					+ yOffset + ")", e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> dragAndDropBy() -> ", e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	// Sets the value for slider
	public void setSliderValue(String locator, String scrollValue) {
		try {
			locator = resolveGUIObject(locator);

			WebElement e = getElement(locator);
			Actions a = new Actions(driver);
			a.clickAndHold(e).perform();
			a.moveByOffset(new Integer(scrollValue), 0);
			a.release().build().perform();

			setStepPass("SetSliderValue(" + locator + "," + scrollValue + ")");
		} catch (Exception e) {
			String fileName = takeScreenShot("SetSliderValue(" + locator + ")");
			setStepFail("SetSliderValue(" + locator + "," + scrollValue + ")",
					e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> setSliderValue(" + locator
							+ ") -> ", e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	public String getElementText(String locator) {
		try {
			locator = resolveGUIObject(locator);

			WebElement e = getElement(locator);
			setStepPass("GetElementText(" + locator + ")");
			return e.getText();
		} catch (Exception e) {
			String fileName = takeScreenShot("GetElementText");
			setStepFail("GetElementText(" + locator + ")", e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> getElementText() -> ", e);
			return null;
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	public String getTextBoxValue(String locator) {
		try {
			locator = resolveGUIObject(locator);

			WebElement e = getElement(locator);
			setStepPass("GetTextBoxValue(" + locator + ")");
			return e.getAttribute("value");
		} catch (Exception e) {
			String fileName = takeScreenShot("GetTextBoxValue");
			setStepFail("GetTextBoxValue(" + locator + ")", e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> getTextBoxValue() -> ", e);
			return null;
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	// TODO- Need to test
	// Selects option in listbox
	public void select(String locator, String listDropDownOption) {
		try {
			locator = resolveGUIObject(locator);

			WebElement e = getElement(locator);
			Select lstDropDown = new Select(e);

			if (listDropDownOption.startsWith("label=")) {
				lstDropDown.selectByVisibleText(locator.substring(6));
				setStepPass("Select(" + locator + ")");
			}

			if (listDropDownOption.startsWith("value=")) {
				lstDropDown.selectByValue(locator.substring(6));
				setStepPass("Select(" + locator + ")");
			}

			if (listDropDownOption.startsWith("index=")) {
				lstDropDown.selectByIndex(new Integer(locator.substring(6)));
				setStepPass("Select(" + locator + ")");
			}

			lstDropDown.selectByVisibleText(locator);
			setStepPass("Select(" + locator + "," + listDropDownOption + ")");
		} catch (Exception e) {
			String fileName = takeScreenShot("Select");
			setStepFail("Select(" + locator + "," + listDropDownOption + ")",
					e, fileName);
			gsLogger.writeERROR("Exception occured-> WebRobot -> select("
					+ locator + ") -> ", e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	// Check a toggle-button (checkbox/radio)
	public void check(String locator) {
		try {
			locator = resolveGUIObject(locator);

			WebElement e = getElement(locator);

			if (e.isSelected()) {
				setStepPass("Check(" + locator + ")");
			}

			e.click();

			if (e.isSelected()) {
				setStepPass("Check(" + locator + ")");
			} else {
				String fileName = takeScreenShot("check");
				setStepFail("Check(" + locator + ")", new Exception("Element "
						+ locator + " not selected"), fileName);
			}

		} catch (Exception e) {
			String fileName = takeScreenShot("Check");
			setStepFail("Check(" + locator + ")", e, fileName);
			gsLogger.writeERROR("Exception occured-> WebRobot -> check() -> ",
					e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	// Uncheck a toggle-button (checkbox/radio)
	public void uncheck(String locator) {
		try {
			locator = resolveGUIObject(locator);

			WebElement e = getElement(locator);

			if (!e.isSelected()) {
				setStepPass("Uncheck(" + locator + ")");
			}

			e.click();

			if (!e.isSelected()) {
				setStepPass("Uncheck(" + locator + ")");
			} else {
				String fileName = takeScreenShot("Uncheck");
				setStepFail("Uncheck(" + locator + ")", new Exception(
						"Element " + locator + " selected"), fileName);
			}

		} catch (Exception e) {
			String fileName = takeScreenShot("Uncheck");
			setStepFail("Uncheck(" + locator + ")", e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> uncheck() -> ", e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	// Verify if toggle-button (checkbox/radio) is Checked
	public void isChecked(String locator) {
		try {
			locator = resolveGUIObject(locator);

			WebElement e = getElement(locator);

			if (e.isSelected()) {
				setStepPass("IsChecked(" + locator + ")");
			} else {
				String fileName = takeScreenShot("IsChecked");
				setStepFail("IsChecked(" + locator + ")", new Exception(
						"Element " + locator + " not Checked"), fileName);
			}

		} catch (Exception e) {
			String fileName = takeScreenShot("IsChecked");
			setStepFail("IsChecked(" + locator + ")", e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> isChecked() -> ", e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	// Verify if toggle-button (checkbox/radio) is Unchecked
	public void isUnChecked(String locator) {
		try {
			locator = resolveGUIObject(locator);

			WebElement e = getElement(locator);

			if (!e.isSelected()) {
				setStepPass("IsUnChecked(" + locator + ")");
			} else {
				String fileName = takeScreenShot("IsUnChecked");
				setStepFail("IsChecked(" + locator + ")", new Exception(
						"Element " + locator + " is Checked"), fileName);
			}

		} catch (Exception e) {
			String fileName = takeScreenShot("IsUnChecked");
			setStepFail("IsUnChecked(" + locator + ")", e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> isUnChecked() -> ", e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	// Click OK on alert
	public void chooseOKOnAlert() {
		try {
			Alert alert = driver.switchTo().alert();
			alert.accept();
			setStepPass("ChooseOKOnAlert()");
		} catch (Exception e) {
			String fileName = takeScreenShot("ChooseOKOnAlert");
			setStepFail("ChooseOKOnAlert()", e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> chooseOKOnAlert() -> ", e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	// Maximize the window
	public void maximizeWindow() {
		try {
			driver.manage().window().maximize();
			setStepPass("MaximizeWindow()");
		} catch (Exception e) {
			String fileName = takeScreenShot("MaximizeWindow");
			setStepFail("MaximizeWindow()", e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> maximizeWindow() -> ", e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	// TODO - Need to Test & Confirm
	// Switch windows
	public void switchWindow(String locator) {
		boolean windowFound = false;
		try {
			locator = resolveGUIObject(locator);

			String currentWindowHandle = driver.getWindowHandle();
			for (String handle : driver.getWindowHandles()) {
				driver.switchTo().window(handle);
				driver.getCurrentUrl().contains(locator);
				windowFound = true;
				break;
			}
			if (windowFound == true) {
				setStepPass("SwitchWindow()");
			} else {
				driver.switchTo().window(currentWindowHandle);
				String fileName = takeScreenShot("SwitchWindow");
				setStepFail("SwitchWindow(" + locator + ")", new Exception(
						"Element " + locator + " not found"), fileName);
			}
		} catch (Exception e) {
			String fileName = takeScreenShot("SwitchWindow");
			setStepFail("SwitchWindow(" + locator + ")", e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> switchWindow() -> ", e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	// Browser close. Closes the browser instance.
	public void closeDriver() {
		try {
			driver.close();
			// driver.quit();
			setStepPass("CloseDriver()");
		} catch (Exception e) {
			String fileName = takeScreenShot("CloseDriver");
			setStepFail("CloseDriver()", e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> closeDriver() -> ", e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
	}

	public void deleteAllCookies() {
		driver.manage().deleteAllCookies();
	}

	// This method is called internally by WebRobot
	// It gets resolved object. No need to call again resolveGUIObject
	private WebElement getElement(String locator) {
		if (locator.startsWith("css=")) {
			return driver.findElement(By.cssSelector(locator.substring(4)));
		}

		if (locator.startsWith("//")) {
			return driver.findElement(By.xpath(locator));
		}

		if (locator.startsWith("name=")) {
			return driver.findElement(By.name(locator.substring(5)));
		}

		if (locator.startsWith("link=")) {
			// return driver.findElement(By.linkText(locator.substring(5)));
			return driver.findElement(By.partialLinkText(locator.substring(5)));
		}

		if (locator.startsWith("class=")) {
			return driver.findElement(By.className(locator.substring(6)));
		}

		return driver.findElement(By.id(locator));
	}
	
	private List<WebElement> getElements(String locator) {
		if (locator.startsWith("css=")) {
			return driver.findElements(By.cssSelector(locator.substring(4)));
		}

		if (locator.startsWith("//")) {
			return driver.findElements(By.xpath(locator));
		}

		if (locator.startsWith("name=")) {
			return driver.findElements(By.name(locator.substring(5)));
		}

		if (locator.startsWith("link=")) {
			// return driver.findElement(By.linkText(locator.substring(5)));
			return driver.findElements(By.partialLinkText(locator.substring(5)));
		}

		if (locator.startsWith("class=")) {
			return driver.findElements(By.className(locator.substring(6)));
		}

		return driver.findElements(By.id(locator));
	}

	private void setStepPass(String step) {
		stepResult = new TestStepResult();

		stepResult.setStep(step);
		stepResult.setStatus(PASS);
	}

	private void setStepFail(String step, Exception e,
			String errorScreenShotFile) {
		stepResult = new TestStepResult();

		stepResult.setStep(step);
		stepResult.setStatus(FAIL);
		stepResult.setErrMsg(e.getMessage());
		stepResult.setErrorScreenshotFile(errorScreenShotFile);

		StringWriter stacktrace = new StringWriter();
		e.printStackTrace(new PrintWriter(stacktrace));
		stepResult.setStackTrace(stacktrace.toString());
	}

	private void setStepWarn(String step, Exception e,
			String errorScreenShotFile) {
		stepResult = new TestStepResult();

		stepResult.setStep(step);
		stepResult.setStatus(WARNING);
		stepResult.setErrMsg(e.getMessage());
		stepResult.setErrorScreenshotFile(errorScreenShotFile);

		StringWriter stacktrace = new StringWriter();
		e.printStackTrace(new PrintWriter(stacktrace));
		stepResult.setStackTrace(stacktrace.toString());
	}

	private String takeScreenShot(String step) {
		try {
			String path = createScreenshotDir();

			String dateTime = new SimpleDateFormat("yyyyMMdd_HHmmss")
					.format(Calendar.getInstance().getTime());
			String fileName = testcaseResult.getTestcaseName() + "_" + step
					+ "_" + dateTime + ".jpg";

			File screenshot = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.FILE);
			// WebDriver augmentedDriver =new Augmenter().augment(driver);
			// File
			// screenshot=((TakesScreenshot)augmentedDriver).getScreenshotAs(OutputType.FILE);

			FileUtils.copyFile(screenshot, new File(path + "/" + fileName));

			// return relative path to store in report
			return "../" + SCREENSHOTS_DIR + "/" + fileName;
		} catch (Exception e) {
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> takeScreenShot() -> ", e);
			return "";
		}
	}

	private String createScreenshotDir() {
		XMLReporter xmlReporter = getXMLReporter();
		File f = new File(xmlReporter.getReportPath() + "/" + SCREENSHOTS_DIR);

		if (!f.exists())
			f.mkdir();

		return f.getAbsolutePath();
	}

	/*
	 * private String resolveGUIObject(String locator){ try{ String
	 * callerClassName=Thread.currentThread().getStackTrace()[3].getClassName();
	 * 
	 * //It's an instance of page class
	 * if(callerClassName.contains("com.stc.pages")){ Page p =(Page)
	 * Class.forName(callerClassName).newInstance();
	 * 
	 * //Track the current page application is on
	 * setCurrentPage(callerClassName);
	 * 
	 * return p.resolveObject(locator); }else{ //It's not page object //So try
	 * to resolve the object from current page application is on Page p =(Page)
	 * Class.forName(currentPage).newInstance(); return
	 * p.resolveObject(locator); } }catch(Exception e){
	 * gsLogger.writeERROR("Exception occured-> WebRobot -> resolveGUIObject() -> "
	 * , e); //return passed in locator without processing return locator; } }
	 */

	private String resolveGUIObject(String locator) {
		try {
			// Page p =(Page) Class.forName(currentPage).newInstance();
			Page p = (Page) Class.forName(getCurrentPage()).newInstance();
			return p.resolveObject(locator);
		} catch (Exception e) {
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> resolveGUIObject() -> ", e);
			// return passed in locator without processing
			return locator;
		}
	}

	public void assertEquals(String expected, String actual,
			String passMessage, String failMessage)

	{

		//driver.findElement(ExpectedConditions.visibilityOfElementLocated(""))
		// System.out.println("in robot class eqls method");

		try {

			if (expected.equals(actual)) {
				setStepPass("AssertEquals(" + expected + "," + actual + ") ["
						+ passMessage + "]");
			} else {
				throw new RuntimeException("Expected: " + expected + " Got: "
						+ actual);
			}

		} catch (Exception e) {
			String fileName = takeScreenShot("IsChecked");
			setStepFail(failMessage, e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> asertEquals() -> ", e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}

	}

	
	
	
	public void assertEquals(Integer expected, Integer actual,
			String passMessage, String failMessage)

	{

		// System.out.println("in robot class eqls method");

		try {

			if (expected.equals(actual)) {
				setStepPass("AssertEquals(" + expected + "," + actual + ") ["
						+ passMessage + "]");
			} else {
				throw new RuntimeException("Expected: " + expected + " Got: "
						+ actual);
			}

		} catch (Exception e) {
			String fileName = takeScreenShot("IsChecked");
			setStepFail(failMessage, e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> asertEquals() -> ", e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}

	}

	
	
	
	public void verifyEquals(String expected, String actual,
			String passMessage, String failMessage)

	{

		// System.out.println("in robot class eqls method");

		try {

			if (expected.equals(actual)) {
				setStepPass("VerifyEquals(" + expected + "," + actual + ") ["
						+ passMessage + "]");
			} else {
				throw new RuntimeException("Expected: " + expected + " Got: "
						+ actual);
			}

		} catch (Exception e) {
			String fileName = takeScreenShot("IsChecked");
			setStepFail(failMessage, e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> verifyEquals() -> ", e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}

	}

	public String getValueFromProperties(String locator) {
		try {
			// Page p =(Page) Class.forName(currentPage).newInstance();
			Page p = (Page) Class.forName(getCurrentPage()).newInstance();
			return p.resolveObject(locator);
		} catch (Exception e) {
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> resolveGUIObject() -> ", e);
			// return passed in locator without processing
			return locator;
		}
	}

	public void implicitWait(int time) {
		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
	}
	
	
	public void pageLoadTimeout(int time) {
		driver.manage().timeouts().pageLoadTimeout(time, TimeUnit.SECONDS);
	}

	

	public void verifyEquals(Boolean expected, Boolean actual,
			String passMessage, String failMessage)

	{

		try {

			if (expected.equals(actual)) {
				setStepPass("VerifyEquals(" + expected + "," + actual + ") ["
						+ passMessage + "]");
			} else {
				throw new RuntimeException("Expected: " + expected + " Got: "
						+ actual);
			}

		} catch (Exception e) {
			String fileName = takeScreenShot("IsChecked");
			setStepFail(failMessage, e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> verifyEquals() -> ", e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}

		// TODO Auto-generated method stub

	}
	
	public Set<String> getWindowHandles()
	{
	return driver.getWindowHandles();	
	}
	
	public List<WebElement> getWebElementList(String locator)

	{
		List<WebElement> list=null;

		try {
             
			locator = resolveGUIObject(locator);
		 list = getElements(locator);

			//return driver.findElements(by.)
			if (list !=null) {
				setStepPass("getWebElementArraySize( List of web elements found with given locator ) ["+locator+"] ");
			} else {
				throw new RuntimeException("Locator is not returning more than one web element with locator as" +locator);
			}

		} catch (Exception e) {
			String fileName = takeScreenShot("IsChecked");
			setStepFail("Locator is not returning more than one web element" +locator, e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> verifyEquals() -> ", e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}

		// TODO Auto-generated method stub
		
		return  list;

	}

	public void verifyEquals(Integer expected, Integer actual,
			String passMessage, String failMessage) 
	{
		
		
		
		
		
		try {

			if (expected.equals(actual)) {
				setStepPass("VerifyEquals(" + expected + "," + actual + ") ["
						+ passMessage + "]");
			} else {
				throw new RuntimeException("Expected: " + expected + " Got: "
						+ actual);
			}

		} catch (Exception e) {
			String fileName = takeScreenShot("IsChecked");
			setStepFail(failMessage, e, fileName);
			gsLogger.writeERROR(
					"Exception occured-> WebRobot -> verifyEquals() -> ", e);
		} finally {
			if (TestType.getTestType().equals(FUNCTIONAL_TEST))
				testcaseResult.addTestStepResult(stepResult);
			else
				stepDefinitionResult.addTestStepResult(stepResult);
		}
		
		// TODO Auto-generated method stub
		
	}

	/*
	 * public void setCurrentPage(String pageName) { currentPage=pageName; }
	 */
}