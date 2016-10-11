package com.stc.util;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.remote.RemoteWebDriver;

import static com.stc.Logger.GSLogger.gsLogger;

public final class DriverFactory {
	private static WebDriver driver=null;
	private static WebDriverWait wait=null;
	private static int timeoutSeconds;
	
	public static String BROWSER_NAME="";
	public static String BROWSER_VERSION="";
	public static String PLATFORM="";
	
	private DriverFactory(){
		
	}
	
	//Thread safe implementation
	//Not using for now
	/*public static WebDriver getDriver(){
		if(driver==null){
			synchronized(DriverFactory.class){
				if(driver==null){
					//driver=new ChromeDriver();
					driver=new FirefoxDriver();
					//driver=new InternetExplorerDriver();
					driver.manage().timeouts().implicitlyWait(timeoutSeconds,TimeUnit.SECONDS);
				}
			}
		}
		return driver;
	}*/
	
	public static WebDriver getDriver(){
		if(driver==null){
			String browser=FrameWorkConfig.getBrowser();
			timeoutSeconds=FrameWorkConfig.getBrowserTimeOut();
			
			if (browser.equals("FF")){
				getFFDriver();
			}
			else if (browser.equals("IE")){
				getIEDriver();
			}
			else if (browser.equals("GC")){
				getChromeDriver();
			}
			
			//Get actualCapabilities
			Capabilities actualCapabilities = ((RemoteWebDriver) driver).getCapabilities();
			BROWSER_NAME=actualCapabilities.getBrowserName().toUpperCase();
			BROWSER_VERSION=actualCapabilities.getVersion();
			PLATFORM=actualCapabilities.getPlatform().toString().toUpperCase();	
		}
		return driver;
	}
	
	private static WebDriver getIEDriver() {
		DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
		capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		capabilities.setJavascriptEnabled(true);			
		capabilities.setCapability("ignoreProtectedModeSettings", true);
		System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"/resource/drivers/IEDriverServer_win32.exe");
		driver = new InternetExplorerDriver(capabilities);
		driver.manage().timeouts().implicitlyWait(timeoutSeconds,TimeUnit.SECONDS);	
		
		return driver;
	}
	
	/*private static void getIEDriver() {
		if(driver==null){
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"/drivers/IEDriverServer.exe");
			driver = new InternetExplorerDriver(capabilities);
		}
	}*/
	

	private static WebDriver getFFDriver(){			
		FirefoxProfile firefoxProfile =  new FirefoxProfile();
		//firefoxProfile.setPreference("network.proxy.type", 4);//Auto detect Proxy settings			
		firefoxProfile.setPreference("app.update.enabled", false); //Disable auto update
		firefoxProfile.setPreference("browser.shell.checkDefaultBrowser", false); //Disable default browser check
		firefoxProfile.setAcceptUntrustedCertificates(true); //Accept certificates
		firefoxProfile.setPreference("browser.tabs.autoHide", true); //Hide tabs
		firefoxProfile.setPreference("browser.tabs.warnOnClose", false); //Disable warning on tab open
		firefoxProfile.setPreference("browser.tabs.warnOnOpen", false); //Disable warning on tab close
		firefoxProfile.setPreference("browser.rights.3.shown", true); //Disable Know your rights Option	
		driver = new FirefoxDriver(firefoxProfile);		
		driver.manage().timeouts().implicitlyWait(timeoutSeconds,TimeUnit.SECONDS);	
		
		return driver;
	}
	
	
	private static WebDriver getChromeDriver(){
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/resource/drivers/chromedriver.exe");
		driver=new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(timeoutSeconds,TimeUnit.SECONDS);	
		
		return driver;
	}
	
	public static WebDriverWait getWait(){
		if(wait==null){
			wait=new WebDriverWait(driver, timeoutSeconds);
		}
		return wait;
	}
	
	public static void setWait(int timeoutSecs){
		timeoutSeconds=timeoutSecs;
		wait=new WebDriverWait(driver, timeoutSeconds);
	}
	
	public static void setImplicitWait(int timeoutSeconds){
		if(driver!=null)
			driver.manage().timeouts().implicitlyWait(timeoutSeconds,TimeUnit.SECONDS);
	}
	
	public static void closeDriver(){
		try{
			if(driver!=null)
				//driver.close();
				driver.quit();

			
			driver=null;
			wait=null;
		}catch(Exception e){
			//e.printStackTrace();
			gsLogger.writeERROR("Exception occured-> DriverFactory -> closeDriver() -> ", e);
		}
	}
}
