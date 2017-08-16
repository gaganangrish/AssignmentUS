/**
 * 
 */
package com.UniSuper.baseClass;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
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
import org.junit.Assert;
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
 * Super class containing all common methods 
 * 
 * 
 * @author gagan_000
 * 
 */
public abstract class BaseClass {

	final static Logger logger = Logger.getLogger(BaseClass.class);
	static WebDriver Driver;
	static Date date = new Date();
	static SimpleDateFormat sdf = new SimpleDateFormat("hh_mm");
	static String formattedDate = sdf.format(date);
	public static String path = System.getProperty("user.dir")+"/target/Automation Screenshots/";
	static DateFormat dateFormat = new SimpleDateFormat("ddMMMyyyy");
	public static String newdate = dateFormat.format(date).toString();
	public static String foldername = path + newdate;

	/**
	 * @param BrowserType
	 * @return
	 */
	public static WebDriver BrowserSetup(String BrowserType) {
		if (BrowserType.contentEquals("firefox")) {
			System.setProperty("webdriver.gecko.driver", "src/test/resources/drivers/geckodriver.exe");
			Driver = new FirefoxDriver();
		} else if (BrowserType.contentEquals("chrome")) {
			System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
			Driver = new ChromeDriver();
		} else if (BrowserType.contentEquals("ie")) {
			System.setProperty("webdriver.ie.driver", "src/test/resources/drivers/IEDriverServer.exe");
			Driver = new InternetExplorerDriver();
		}else {
			Assert.fail("Unsupported browser. Only chrome, firefox and ie is supported");
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

	/**
	 * @param ele
	 * @param driver
	 * @return
	 */
	public boolean mouseHover(WebElement ele, WebDriver driver) {
		Actions aobj = new Actions(driver);
		aobj.moveToElement(ele).perform();
		return true;
	}
	
	/**
	 * @param driver
	 */
	public static void waitForPageLoad(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, 30); wait.until(ExpectedConditions.jsReturnsValue("return document.readyState==\"complete\";"));
	}
	
	
	/**
	 * @param ele
	 * @param timeout
	 * @param driver
	 * @param eleName
	 * @return
	 */
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
			logger.info("Webdriver exception. Trying to press enter on the element: " + eleName, e);

			try {
				aobj.moveToElement(ele).click().build().perform();
				ele.sendKeys(Keys.RETURN);
				return true;
			} catch (Exception e2) {
				logger.info("Webdriver exception. Trying to actions click on the element: " + eleName, e2);

				try {
					aobj.moveToElement(ele).click().build().perform();
					return true;
				} catch (Exception e3) {
					logger.info(e3);
					return false;
				}
			}
		} catch (Exception e) {
			logger.info("Webdriver exception. Trying to press enter on the element: " + eleName, e);
			try {
				aobj.moveToElement(ele).click().build().perform();
				ele.sendKeys(Keys.RETURN);
				return true;
			} catch (Exception e2) {
				logger.info("Not able to click: " + eleName, e2);
				return false;
			}
		}
	}
	
	/**
	 * @param ele
	 * @param timeout
	 * @param driver
	 * @return
	 */
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
	
	/**
	 * @param URL
	 * @return
	 */
	public static boolean GetURL(String URL) {

		Driver.get(URL);
		return true;

	}
	
	/**
	 * @param driver
	 * @param filename
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void capturescreenshot(WebDriver driver, String filename) throws IOException, InterruptedException{
		Thread.sleep(1000);
		Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
		String browserName = cap.getBrowserName().toLowerCase();
		String os = cap.getPlatform().toString();
		String v = cap.getVersion().toString();
		Shutterbug.shootPage(driver, ScrollStrategy.BOTH_DIRECTIONS).withName(filename+"_"+browserName+"_"+v+"_"+os+"_"+formattedDate).save(foldername+"//");

	}
	
	/**
	 * @param myElement
	 * @param textToEnter
	 * @return
	 */
	public boolean enterTextAndPressEnter(WebElement myElement, String textToEnter) {
		try {
			
//			myElement.clear();
			myElement.sendKeys(Keys.BACK_SPACE);
			myElement.sendKeys(Keys.CONTROL+"a");
			myElement.sendKeys(textToEnter);
			myElement.sendKeys(Keys.ENTER);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Exception in entering text.", e);
			return false;
		}

	}
	
	/**
	 * @param ele
	 * @param driver
	 * @return
	 */
	public boolean doubleClickElement(WebElement ele, WebDriver driver) {
		Actions aobj = new Actions(driver);
		try {
		aobj.doubleClick(ele).perform();
		return true;
		}catch(Exception e) {
			logger.info("Not able to perform double click.", e);
			return false;
		}
		
	}
	
	/**
	 * @param text
	 * @return
	 * @throws AWTException
	 * @throws InterruptedException
	 */
	public boolean enterTextUsingRobotClass(String text) throws AWTException, InterruptedException {
		Robot robj = new Robot();
		robj.keyPress(KeyEvent.VK_CONTROL);
		robj.keyPress(KeyEvent.VK_A);
		robj.keyRelease(KeyEvent.VK_A);
		robj.keyRelease(KeyEvent.VK_CONTROL);
		robj.keyPress(KeyEvent.VK_BACK_SPACE);
		robj.keyRelease(KeyEvent.VK_BACK_SPACE);
		
		char[] charArr = text.toCharArray();
		for (int i = 0; i < charArr.length; i++) {
			pressChar(charArr[i]);
		}
		
		robj.keyPress(KeyEvent.VK_ENTER);
		robj.keyRelease(KeyEvent.VK_ENTER);
		return true;
	}
	
	/**
	 * @param ch
	 * @throws AWTException
	 */
	public void pressChar(char ch) throws AWTException {
		Robot robot = new Robot();
		switch (ch) {
		case '-':robot.keyPress(KeyEvent.VK_MINUS);robot.keyRelease(KeyEvent.VK_MINUS);break;
		case '.':robot.keyPress(KeyEvent.VK_PERIOD);robot.keyRelease(KeyEvent.VK_PERIOD);break;
		case '0':robot.keyPress(KeyEvent.VK_0);robot.keyRelease(KeyEvent.VK_0);break;	
		case '1':robot.keyPress(KeyEvent.VK_1);robot.keyRelease(KeyEvent.VK_1);break;	
		case '2':robot.keyPress(KeyEvent.VK_2);robot.keyRelease(KeyEvent.VK_2);break;	
		case '3':robot.keyPress(KeyEvent.VK_3);robot.keyRelease(KeyEvent.VK_3);break;	
		case '4':robot.keyPress(KeyEvent.VK_4);robot.keyRelease(KeyEvent.VK_4);break;	
		case '5':robot.keyPress(KeyEvent.VK_5);robot.keyRelease(KeyEvent.VK_5);break;	
		case '6':robot.keyPress(KeyEvent.VK_6);robot.keyRelease(KeyEvent.VK_6);break;	
		case '7':robot.keyPress(KeyEvent.VK_7);robot.keyRelease(KeyEvent.VK_7);break;	
		case '8':robot.keyPress(KeyEvent.VK_8);robot.keyRelease(KeyEvent.VK_8);break;	
		case '9':robot.keyPress(KeyEvent.VK_9);robot.keyRelease(KeyEvent.VK_9);break;	
		case ':':robot.keyPress(KeyEvent.VK_SHIFT);robot.keyPress(KeyEvent.VK_SEMICOLON);robot.keyRelease(KeyEvent.VK_SHIFT);robot.keyRelease(KeyEvent.VK_SEMICOLON);break;		
		case 'A':robot.keyPress(KeyEvent.VK_SHIFT);robot.keyPress(KeyEvent.VK_A);robot.keyRelease(KeyEvent.VK_SHIFT);robot.keyRelease(KeyEvent.VK_A);break;	
		case 'B':robot.keyPress(KeyEvent.VK_SHIFT);robot.keyPress(KeyEvent.VK_B);robot.keyRelease(KeyEvent.VK_SHIFT);robot.keyRelease(KeyEvent.VK_B);break;	
		case 'C':robot.keyPress(KeyEvent.VK_SHIFT);robot.keyPress(KeyEvent.VK_C);robot.keyRelease(KeyEvent.VK_SHIFT);robot.keyRelease(KeyEvent.VK_C);break;	
		case 'D':robot.keyPress(KeyEvent.VK_SHIFT);robot.keyPress(KeyEvent.VK_D);robot.keyRelease(KeyEvent.VK_SHIFT);robot.keyRelease(KeyEvent.VK_D);break;	
		case 'E':robot.keyPress(KeyEvent.VK_SHIFT);robot.keyPress(KeyEvent.VK_E);robot.keyRelease(KeyEvent.VK_SHIFT);robot.keyRelease(KeyEvent.VK_E);break;	
		case 'F':robot.keyPress(KeyEvent.VK_SHIFT);robot.keyPress(KeyEvent.VK_F);robot.keyRelease(KeyEvent.VK_SHIFT);robot.keyRelease(KeyEvent.VK_F);break;	
		case 'G':robot.keyPress(KeyEvent.VK_SHIFT);robot.keyPress(KeyEvent.VK_G);robot.keyRelease(KeyEvent.VK_SHIFT);robot.keyRelease(KeyEvent.VK_G);break;	
		case 'H':robot.keyPress(KeyEvent.VK_SHIFT);robot.keyPress(KeyEvent.VK_H);robot.keyRelease(KeyEvent.VK_SHIFT);robot.keyRelease(KeyEvent.VK_H);break;	
		case 'I':robot.keyPress(KeyEvent.VK_SHIFT);robot.keyPress(KeyEvent.VK_I);robot.keyRelease(KeyEvent.VK_SHIFT);robot.keyRelease(KeyEvent.VK_I);break;	
		case 'J':robot.keyPress(KeyEvent.VK_SHIFT);robot.keyPress(KeyEvent.VK_J);robot.keyRelease(KeyEvent.VK_SHIFT);robot.keyRelease(KeyEvent.VK_J);break;	
		case 'K':robot.keyPress(KeyEvent.VK_SHIFT);robot.keyPress(KeyEvent.VK_K);robot.keyRelease(KeyEvent.VK_SHIFT);robot.keyRelease(KeyEvent.VK_K);break;	
		case 'L':robot.keyPress(KeyEvent.VK_SHIFT);robot.keyPress(KeyEvent.VK_L);robot.keyRelease(KeyEvent.VK_SHIFT);robot.keyRelease(KeyEvent.VK_L);break;	
		case 'M':robot.keyPress(KeyEvent.VK_SHIFT);robot.keyPress(KeyEvent.VK_M);robot.keyRelease(KeyEvent.VK_SHIFT);robot.keyRelease(KeyEvent.VK_M);break;	
		case 'N':robot.keyPress(KeyEvent.VK_SHIFT);robot.keyPress(KeyEvent.VK_N);robot.keyRelease(KeyEvent.VK_SHIFT);robot.keyRelease(KeyEvent.VK_N);break;	
		case 'O':robot.keyPress(KeyEvent.VK_SHIFT);robot.keyPress(KeyEvent.VK_O);robot.keyRelease(KeyEvent.VK_SHIFT);robot.keyRelease(KeyEvent.VK_O);break;	
		case 'P':robot.keyPress(KeyEvent.VK_SHIFT);robot.keyPress(KeyEvent.VK_P);robot.keyRelease(KeyEvent.VK_SHIFT);robot.keyRelease(KeyEvent.VK_P);break;	
		case 'Q':robot.keyPress(KeyEvent.VK_SHIFT);robot.keyPress(KeyEvent.VK_Q);robot.keyRelease(KeyEvent.VK_SHIFT);robot.keyRelease(KeyEvent.VK_Q);break;	
		case 'R':robot.keyPress(KeyEvent.VK_SHIFT);robot.keyPress(KeyEvent.VK_R);robot.keyRelease(KeyEvent.VK_SHIFT);robot.keyRelease(KeyEvent.VK_R);break;	
		case 'S':robot.keyPress(KeyEvent.VK_SHIFT);robot.keyPress(KeyEvent.VK_S);robot.keyRelease(KeyEvent.VK_SHIFT);robot.keyRelease(KeyEvent.VK_S);break;	
		case 'T':robot.keyPress(KeyEvent.VK_SHIFT);robot.keyPress(KeyEvent.VK_T);robot.keyRelease(KeyEvent.VK_SHIFT);robot.keyRelease(KeyEvent.VK_T);break;	
		case 'U':robot.keyPress(KeyEvent.VK_SHIFT);robot.keyPress(KeyEvent.VK_U);robot.keyRelease(KeyEvent.VK_SHIFT);robot.keyRelease(KeyEvent.VK_U);break;	
		case 'V':robot.keyPress(KeyEvent.VK_SHIFT);robot.keyPress(KeyEvent.VK_V);robot.keyRelease(KeyEvent.VK_SHIFT);robot.keyRelease(KeyEvent.VK_V);break;	
		case 'W':robot.keyPress(KeyEvent.VK_SHIFT);robot.keyPress(KeyEvent.VK_W);robot.keyRelease(KeyEvent.VK_SHIFT);robot.keyRelease(KeyEvent.VK_W);break;	
		case 'X':robot.keyPress(KeyEvent.VK_SHIFT);robot.keyPress(KeyEvent.VK_X);robot.keyRelease(KeyEvent.VK_SHIFT);robot.keyRelease(KeyEvent.VK_X);break;	
		case 'Y':robot.keyPress(KeyEvent.VK_SHIFT);robot.keyPress(KeyEvent.VK_Y);robot.keyRelease(KeyEvent.VK_SHIFT);robot.keyRelease(KeyEvent.VK_Y);break;	
		case 'Z':robot.keyPress(KeyEvent.VK_SHIFT);robot.keyPress(KeyEvent.VK_Z);robot.keyRelease(KeyEvent.VK_SHIFT);robot.keyRelease(KeyEvent.VK_Z);break;	
		case '\\':robot.keyPress(KeyEvent.VK_BACK_SLASH);robot.keyRelease(KeyEvent.VK_BACK_SLASH);break;	
		case '_':robot.keyPress(KeyEvent.VK_SHIFT);robot.keyPress(KeyEvent.VK_MINUS);robot.keyRelease(KeyEvent.VK_SHIFT);robot.keyRelease(KeyEvent.VK_MINUS);break;	
		case 'a':robot.keyPress(KeyEvent.VK_A);robot.keyRelease(KeyEvent.VK_A);break;	
		case 'b':robot.keyPress(KeyEvent.VK_B);robot.keyRelease(KeyEvent.VK_B);break;
		case 'c':robot.keyPress(KeyEvent.VK_C);robot.keyRelease(KeyEvent.VK_C);break;
		case 'd':robot.keyPress(KeyEvent.VK_D);robot.keyRelease(KeyEvent.VK_D);break;
		case 'e':robot.keyPress(KeyEvent.VK_E);robot.keyRelease(KeyEvent.VK_E);break;
		case 'f':robot.keyPress(KeyEvent.VK_F);robot.keyRelease(KeyEvent.VK_F);break;
		case 'g':robot.keyPress(KeyEvent.VK_G);robot.keyRelease(KeyEvent.VK_G);break;
		case 'h':robot.keyPress(KeyEvent.VK_H);robot.keyRelease(KeyEvent.VK_H);break;
		case 'i':robot.keyPress(KeyEvent.VK_I);robot.keyRelease(KeyEvent.VK_I);break;
		case 'j':robot.keyPress(KeyEvent.VK_J);robot.keyRelease(KeyEvent.VK_J);break;
		case 'k':robot.keyPress(KeyEvent.VK_K);robot.keyRelease(KeyEvent.VK_K);break;
		case 'l':robot.keyPress(KeyEvent.VK_L);robot.keyRelease(KeyEvent.VK_L);break;
		case 'm':robot.keyPress(KeyEvent.VK_M);robot.keyRelease(KeyEvent.VK_M);break;
		case 'n':robot.keyPress(KeyEvent.VK_N);robot.keyRelease(KeyEvent.VK_N);break;
		case 'o':robot.keyPress(KeyEvent.VK_O);robot.keyRelease(KeyEvent.VK_O);break;
		case 'p':robot.keyPress(KeyEvent.VK_P);robot.keyRelease(KeyEvent.VK_P);break;
		case 'q':robot.keyPress(KeyEvent.VK_Q);robot.keyRelease(KeyEvent.VK_Q);break;
		case 'r':robot.keyPress(KeyEvent.VK_R);robot.keyRelease(KeyEvent.VK_R);break;
		case 's':robot.keyPress(KeyEvent.VK_S);robot.keyRelease(KeyEvent.VK_S);break;
		case 't':robot.keyPress(KeyEvent.VK_T);robot.keyRelease(KeyEvent.VK_T);break;
		case 'u':robot.keyPress(KeyEvent.VK_U);robot.keyRelease(KeyEvent.VK_U);break;
		case 'v':robot.keyPress(KeyEvent.VK_V);robot.keyRelease(KeyEvent.VK_V);break;
		case 'w':robot.keyPress(KeyEvent.VK_W);robot.keyRelease(KeyEvent.VK_W);break;
		case 'x':robot.keyPress(KeyEvent.VK_X);robot.keyRelease(KeyEvent.VK_X);break;
		case 'y':robot.keyPress(KeyEvent.VK_Y);robot.keyRelease(KeyEvent.VK_Y);break;
		case 'z':robot.keyPress(KeyEvent.VK_Z);robot.keyRelease(KeyEvent.VK_Z);break;
		case ' ':robot.keyPress(KeyEvent.VK_SPACE);robot.keyRelease(KeyEvent.VK_SPACE);break;

		default:
			break;
		}
	}
}
