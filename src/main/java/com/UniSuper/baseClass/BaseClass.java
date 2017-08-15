/**
 * 
 */
package com.UniSuper.baseClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.assertthat.selenium_shutterbug.utils.web.ScrollStrategy;

/**
 * Abstract class containing all common methods 
 * 
 * 
 * @author gagan_000
 * 
 */
public class BaseClass {

	final static Logger logger = Logger.getLogger(BaseClass.class);
	static WebDriver Driver;
	static Date date = new Date();
	static SimpleDateFormat sdf = new SimpleDateFormat("hh_mm");
	static String formattedDate = sdf.format(date);
	public static String path = System.getProperty("user.dir")+"/target/Automation Screenshots/";
	static DateFormat dateFormat = new SimpleDateFormat("ddMMMyyyy");
	public static String newdate = dateFormat.format(date).toString();
	public static String foldername = path + newdate;

	public static WebDriver BrowserSetup(String BrowserType) {
		if (BrowserType.contentEquals("firefox")) {
			System.setProperty("webdriver.gecko.driver", "src/test/resources/drivers/geckodriver.exe");
			Driver = new FirefoxDriver();
		} else if (BrowserType.contentEquals("chrome")) {
			System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
			Driver = new ChromeDriver();
		} else if (BrowserType.contentEquals("ie")) {
			System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/IEDriverServer.exe");
			Driver = new InternetExplorerDriver();
		}

		Driver.manage().window().maximize();
		return Driver;
	}
	
	
	/**
	 * @param orignalFilePath
	 * @param destFilePath
	 */
	public static void moveFiles(String orignalFilePath, String destFilePath) {
		InputStream inStream = null;
		OutputStream outStream = null;

		try {

			File afile = new File(orignalFilePath);
			File bfile = new File(destFilePath);

			inStream = new FileInputStream(afile);
			outStream = new FileOutputStream(bfile);

			byte[] buffer = new byte[1024];

			int length;
			// copy the file content in bytes
			while ((length = inStream.read(buffer)) > 0) {

				outStream.write(buffer, 0, length);

			}

			inStream.close();
			outStream.close();
			System.out.println("File is copied successful! " + orignalFilePath);

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("File not there - " + orignalFilePath);
		}

	}

	public static boolean waitAndClick(WebElement ele, int timeout, WebDriver driver, String eleName) {
		WebElement element = null;
		Actions aobj = new Actions(driver);

		// JS executor will scroll the element in the view
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ele);

		try {
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			element = wait.until(ExpectedConditions.visibilityOf(ele));
			element = wait.until(ExpectedConditions.elementToBeClickable(ele));
			element.click();
			return true;
		} catch (WebDriverException e) {
			logger.debug("Webdriver exception. Trying to press enter on the element: " + eleName, e);

			try {
				aobj.moveToElement(ele).click().build().perform();
				ele.sendKeys(Keys.RETURN);
				return true;
			} catch (Exception e2) {
				logger.debug("Webdriver exception. Trying to actions click on the element: " + eleName, e2);

				try {
					aobj.moveToElement(ele).click().build().perform();
					return true;
				} catch (Exception e3) {
					logger.debug(e3);
					return false;
				}
			}
		} catch (Exception e) {
			logger.debug("Webdriver exception. Trying to press enter on the element: " + eleName, e);
			try {
				aobj.moveToElement(ele).click().build().perform();
				ele.sendKeys(Keys.RETURN);
				return true;
			} catch (Exception e2) {
				logger.debug("Not able to click: " + eleName, e2);
				return false;
			}
		}
	}
	
	public static boolean waitForElementToBeDisplayed(WebElement ele, int timeout, WebDriver driver){
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			WebElement element = wait.until(ExpectedConditions.visibilityOf(ele));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		
	}
	
	public static boolean GetURL(String URL) {

		Driver.get(URL);
		return true;

	}
	
	public static void capturescreenshot(WebDriver driver, String filename) throws IOException, InterruptedException{
		Thread.sleep(1000);
		Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
		String browserName = cap.getBrowserName().toLowerCase();
		String os = cap.getPlatform().toString();
		String v = cap.getVersion().toString();
		Shutterbug.shootPage(driver, ScrollStrategy.BOTH_DIRECTIONS).withName(filename+"_"+browserName+"_"+v+"_"+os+"_"+formattedDate).save(foldername+"//");

	}
}
