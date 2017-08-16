/**
 * 
 */
package com.UniSuper.commUtils;

import org.apache.commons.lang.StringUtils;
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
	
	
	/**
	 * @param driver
	 * @return
	 */
	public WebDriver launchBrowser(WebDriver driver) {
		
		//setting default values for baseURL and browserName
		if (StringUtils.isEmpty(baseURL)) {
			baseURL = "http://todomvc.com/";
		}
		if (StringUtils.isEmpty(browserName)) {
			browserName = "chrome";
		}
		
		logger.info("Launching base url: "+baseURL);
		logger.info("Launhing browser in: "+browserName);
		
		driver = BrowserSetup(browserName);
		if (!GetURL(baseURL)) {
			logger.fatal("Unable to get URL:" + driver.getCurrentUrl() + ", Environment may be unavailable. Aborting Test");
			Assert.fail("Error on page. Check URL: " + driver.getCurrentUrl());
		}
		return driver;
	}

}
