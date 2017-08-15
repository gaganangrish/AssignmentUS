/**
 * 
 */
package com.UniSuper.commUtils;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import com.UniSuper.baseClass.BaseClass;

/**
 * @author gagan_000
 *
 */
public class CommonUtils extends BaseClass{
	
	final static Logger logger = Logger.getLogger(CommonUtils.class);
	String baseURL = System.getProperty("webdriver.base.url");
	String browserName = System.getProperty("webdriver.browser.name");
	
//	String baseURL = "http://todomvc.com/";
//	String browserName = "chrome";
	
	
	public WebDriver launchBrowser(WebDriver driver) {
		
		logger.debug("Launching base url: "+baseURL);
		logger.debug("Launhing browser in: "+browserName);
		
		driver = BrowserSetup(browserName);
		if (!GetURL(baseURL)) {
			logger.fatal("Unable to get URL:" + driver.getCurrentUrl() + ", Environment may be unavailable. Aborting Test");
			Assert.fail("Error on page. Check URL: " + driver.getCurrentUrl());
		}
		return driver;
	}

}
