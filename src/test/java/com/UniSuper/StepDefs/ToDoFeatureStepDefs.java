package com.UniSuper.StepDefs;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.UniSuper.baseClass.BaseClass;
import com.UniSuper.commUtils.CommonUtils;
import com.UniSuper.pageObjects.ToDoAngularJS;
import com.UniSuper.pageObjects.ToDoMVCHome;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Step definations
 * 
 * @author gagan_000
 *
 */
public class ToDoFeatureStepDefs extends BaseClass{
	
	static WebDriver driver;
	private Scenario scenario;
	final static Logger logger = Logger.getLogger(ToDoFeatureStepDefs.class);

    @Before
    public void before(Scenario scenario) {
        this.scenario = scenario;
    }
    
	@After()
	public void afterScenario(Scenario scenario) throws IOException, InterruptedException {
		if (scenario.isFailed()) {
			try {
				capturescreenshot(driver, scenario.getName());
			} catch (Exception e) {
				logger.debug("Exception in capturing screenshot.", e);
			}
			
			//Embedding screenshot in the cucumber report
            if (driver instanceof TakesScreenshot) {
                TakesScreenshot camera = (TakesScreenshot) driver;
                byte[] screenshot = camera.getScreenshotAs(OutputType.BYTES);
                scenario.embed(screenshot, "image/png");
            }
		}
		if (driver != null){
			driver.close();
			driver.quit();	
			logger.debug("Browser Closed");
		}
	}

	
	@Given("^I am on TodoMVC App home page$")
	public void i_am_on_TodoMVC_App_home_page() throws Throwable {
		CommonUtils page = PageFactory.initElements(driver, CommonUtils.class);
		driver = page.launchBrowser(driver);
	}
	
	@When("^I click on AngularJS link on ToDoMVC app home page$")
	public void i_click_on_AngularJS_link_on_ToDoMVC_app_home_page() throws Throwable {
		ToDoMVCHome page = PageFactory.initElements(driver, ToDoMVCHome.class);
		waitAndClick(page.angularJSLink, 2, driver, "Angular JS link");
	}

	@Then("^I should navigate to ToDo App page$")
	public void i_should_navigate_to_ToDo_App_page() throws Throwable {
		String currentURL = driver.getCurrentUrl();
		String expectedURL = "http://todomvc.com/examples/angularjs/#/";
		Assert.assertTrue("User is not getting navigated to ToDo app page. Expected URL: "+expectedURL+". Actual URL: "+currentURL, currentURL.equalsIgnoreCase(expectedURL));
	}

	@Then("^I should see a ToDo bar on the ToDo app page$")
	public void i_should_see_a_ToDo_bar_on_the_ToDo_app_page() throws Throwable {
		ToDoAngularJS page = PageFactory.initElements(driver, ToDoAngularJS.class);
		boolean isDisplayed = waitForElementToBeDisplayed(page.newToDoBar, 2, driver);
		Assert.assertTrue(false);
//		Assert.assertTrue("ToDo bar is not showing on the page. Current page URL: "+driver.getCurrentUrl(), isDisplayed);
	}
	
	@Given("^I want to see test run report$")
	public void i_want_to_see_test_run_report() throws Throwable {
		Assert.assertTrue(true);
	}


}
