package com.UniSuper.StepDefs;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

import com.UniSuper.baseClass.BaseClass;
import com.UniSuper.commUtils.CommonUtils;
import com.UniSuper.pageObjects.ToDoAngularJS;
import com.UniSuper.pageObjects.ToDoMVCHome;

import cucumber.api.PendingException;
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
	ArrayList<Integer> updatedIndexes = new ArrayList<Integer>();
	String currentURL;

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
				logger.info("Exception in capturing screenshot.", e);
			}

			// Embedding screenshot in the cucumber report
			if (driver instanceof TakesScreenshot) {
				TakesScreenshot camera = (TakesScreenshot) driver;
				byte[] screenshot = camera.getScreenshotAs(OutputType.BYTES);
				scenario.embed(screenshot, "image/png");
			}
		}
		if (driver != null) {
			driver.close();
			driver.quit();
			logger.info("Browser Closed ++++++++++++++++++++++");
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
		currentURL = driver.getCurrentUrl();
	}

	@Then("^I should navigate to ToDo App page$")
	public void i_should_navigate_to_ToDo_App_page() throws Throwable {
		waitForPageLoad(driver);
		String expectedURL = "http://todomvc.com/examples/angularjs/#/";
		Assert.assertTrue("User is not getting navigated to ToDo app page. Expected URL: " + expectedURL
				+ ". Actual URL: " + currentURL, currentURL.equalsIgnoreCase(expectedURL));
	}

	@Then("^I should see a ToDo bar on the ToDo app page$")
	public void i_should_see_a_ToDo_bar_on_the_ToDo_app_page() throws Throwable {
		ToDoAngularJS page = PageFactory.initElements(driver, ToDoAngularJS.class);
		boolean isDisplayed = waitForElementToBeDisplayed(page.newToDoBar, 2, driver);
		Assert.assertTrue("ToDo bar is not showing on the page. Current page URL: " + driver.getCurrentUrl(),
				isDisplayed);
	}

	@Given("^I want to see test run report$")
	public void i_want_to_see_test_run_report() throws Throwable {
		Assert.assertTrue(true);
	}

	@Given("^I navigate to ToDo App page$")
	public void i_navigate_to_ToDo_App_page() throws Throwable {
		// Clicking on AngularJS link on home page
		i_click_on_AngularJS_link_on_ToDoMVC_app_home_page();

		// Checking if user is navigating to ToDo App page
		i_should_navigate_to_ToDo_App_page();

		// Checking if ToDo Bar is displaying
		i_should_see_a_ToDo_bar_on_the_ToDo_app_page();

	}

	@When("^I add a new ToDo as \"(.*?)\"$")
	public void i_add_a_new_ToDo_as(String arg1) throws Throwable {
		ToDoAngularJS page = PageFactory.initElements(driver, ToDoAngularJS.class);
		boolean isTextEntered = enterTextAndPressEnter(page.newToDoBar, arg1);
		Assert.assertTrue("Not able to enter text in ToDo bar. Exception", isTextEntered);
	}

	@Then("^newly added ToDo \"(.*?)\" should display in the ToDo list$")
	public void newly_added_ToDo_should_display_in_the_ToDo_list(String arg1) throws Throwable {
		ToDoAngularJS page = PageFactory.initElements(driver, ToDoAngularJS.class);

		// get total number of items in the ToDo list
		int totalNumberOfItems = page.toDoListItems.size();

		// get the text of the last item in the list (which is newly added)
		String textOfLastItem = null;
		for (int i = 0; i < totalNumberOfItems; i++) {
			if (i == (totalNumberOfItems - 1)) {
				textOfLastItem = page.toDoListItems.get(i).getText();
			}
		}
		Assert.assertTrue("Newly added todo is not matching in the todo list. Actual Text: " + textOfLastItem
				+ " Expected Text: " + arg1, textOfLastItem.equalsIgnoreCase(arg1));
	}

	@When("^I add a new ToDo as \"(.*?)\" in the ToDo list$")
	public void i_add_a_new_ToDo_as_in_the_ToDo_list(String arg1) throws Throwable {
		// adding a new todo in the list
		i_add_a_new_ToDo_as(arg1);

		// checking if the todo has been added successfully
		newly_added_ToDo_should_display_in_the_ToDo_list(arg1);
	}

	@When("^I update the existing ToDo from \"(.*?)\" to \"(.*?)\"$")
	public void i_update_the_existing_ToDo_from_to(String arg1, String arg2) throws Throwable {
		ToDoAngularJS page = PageFactory.initElements(driver, ToDoAngularJS.class);

		// getting total number of todos in the list
		int totalNumberofToDos = page.toDoListItems.size();

		// checking and updating all todos with matching text
		for (int i = 0; i < totalNumberofToDos; i++) {
			String currentToDoText = page.toDoListItems.get(i).getText();
			if (currentToDoText.equalsIgnoreCase(arg1)) {
				doubleClickElement(page.toDoListItemsTextLabel.get(i), driver);
				// enterTextAndPressEnter(page.toDoListItemsTextLabel.get(i), arg2);
				// Actions aobj = new Actions(driver);
				// aobj.sendKeys(page.toDoListItemsTextLabel.get(i), "abc derer
				// ").build().perform();

				// JavascriptExecutor jse = (JavascriptExecutor)driver;
				// jse.executeScript("arguments[0].value='(222)222-2222';",
				// page.toDoListItemsTextLabel.get(i));

				enterTextUsingRobotClass(arg2);

				// adding index of updated item to arraylist
				updatedIndexes.add(i);
			}

		}
	}

	@Then("^I should see the updated ToDo item as \"(.*?)\" in the ToDo list$")
	public void i_should_see_the_updated_ToDo_item_as_in_the_ToDo_list(String arg1) throws Throwable {
		ToDoAngularJS page = PageFactory.initElements(driver, ToDoAngularJS.class);

		// getting total number of ToDos
		int totalNumberOfTodos = page.toDoListItems.size();

		// looping through the updated indexes arraylist
		for (int i = 0; i < updatedIndexes.size(); i++) {

			// looping through all items of ToDo list
			for (int j = 0; j < totalNumberOfTodos; j++) {

				// checking if current item's index in the todo list matching with the updated
				// index array current value
				if (updatedIndexes.get(j) == i) {

					// checking the text of the item with expected text
					String getActualTextOfItem = page.toDoListItems.get(i).getText();
					Assert.assertTrue(
							"Text of the ToDo items is not matching with expected. Item number: " + (i + 1)
									+ " Expected Text: " + arg1 + " Actual Text: " + getActualTextOfItem,
							getActualTextOfItem.equalsIgnoreCase(arg1));
				}

			}

		}

	}

	@When("^I complete the existing \"(.*?)\" ToDo from the list of ToDos$")
	public void i_complete_the_existing_ToDo_from_the_list_of_ToDos(String arg1) throws Throwable {
		ToDoAngularJS page = PageFactory.initElements(driver, ToDoAngularJS.class);

		// getting total number of ToDos
		int totalNumberOfTodos = page.toDoListItems.size();

		// looping through each todo item in the list
		for (int i = 0; i < totalNumberOfTodos; i++) {
			// checking current todo item text
			if (page.toDoListItems.get(i).getText().equalsIgnoreCase(arg1)) {
				// completing the ToDo by clicking the checkbox
				waitAndClick(page.toDoListItemsCheckbox.get(i), 1, driver, "Checkbox for Todo item number: " + (i + 1));
				// adding index of completed todo to the array list
				updatedIndexes.add(i);
			}
		}
	}

	@Then("^I should see a strike through line on the completed ToDo in the ToDo list$")
	public void i_should_see_a_strike_through_line_on_the_completed_ToDo_in_the_ToDo_list() throws Throwable {
		ToDoAngularJS page = PageFactory.initElements(driver, ToDoAngularJS.class);

		// Getting the CSS style of the completed ToDo
		String expectedStyle = "line-through";
		for (int i = 0; i < updatedIndexes.size(); i++) {
			String actualStyle = page.toDoListItemsTextLabel.get(updatedIndexes.get(i)).getCssValue("text-decoration");
			Assert.assertTrue("Actual text style is not matching with expected for Todo item number: " + (i + 1)
					+ " Expected: " + expectedStyle + " Actual: " + actualStyle, actualStyle.contains(expectedStyle));

		}

	}

	@Given("^an exiting \"(.*?)\" ToDo is already present and is completed$")
	public void an_exiting_ToDo_is_already_present_and_is_completed(String arg1) throws Throwable {
		// adding and verifying a newly added todo in the list
		i_add_a_new_ToDo_as_in_the_ToDo_list(arg1);

		// marking the todo as complete and verifying if it's showing as strike through
		i_complete_the_existing_ToDo_from_the_list_of_ToDos(arg1);
		i_should_see_a_strike_through_line_on_the_completed_ToDo_in_the_ToDo_list();

	}

	@When("^I reactivate the \"(.*?)\" ToDo from the ToDo list$")
	public void i_reactivate_the_ToDo_from_the_ToDo_list(String arg1) throws Throwable {
		ToDoAngularJS page = PageFactory.initElements(driver, ToDoAngularJS.class);

		// getting total number of ToDos
		int totalNumberOfTodos = page.toDoListItems.size();

		// looping through updateindex array list
		for (int i = 0; i < updatedIndexes.size(); i++) {
			// looping through all todos present in the list
			for (int j = 0; j < totalNumberOfTodos; j++) {
				// checking if current todo item is the one which was updated in the last step
				// and if the item's text is matching with input text
				if ((j == updatedIndexes.get(i)) && page.toDoListItems.get(i).getText().equalsIgnoreCase(arg1)) {
					waitAndClick(page.toDoListItemsCheckbox.get(i), 1, driver, "Checkbox for item number: " + (i + 1));
				}
			}
		}
	}

	@Then("^ToDo \"(.*?)\" should get reactivated$")
	public void todo_should_get_reactivated(String arg1) throws Throwable {
		ToDoAngularJS page = PageFactory.initElements(driver, ToDoAngularJS.class);

		// Getting the CSS style of the completed ToDo
		String expectedStyle = "line-through solid rgb(121, 121, 121)";
		for (int i = 0; i < updatedIndexes.size(); i++) {
			String actualStyle = page.toDoListItemsTextLabel.get(updatedIndexes.get(i)).getCssValue("text-decoration");
			Assert.assertTrue("Todo item still showing as strike through. Todo item number: " + (i + 1) + " Expected: "
					+ expectedStyle + " Actual: " + actualStyle, !actualStyle.equalsIgnoreCase(expectedStyle));

		}
	}

	@Given("^ToDos \"(.*?)\" and \"(.*?)\" are already present$")
	public void todos_and_are_already_present(String arg1, String arg2) throws Throwable {
		// adding 2 new todos
		i_add_a_new_ToDo_as_in_the_ToDo_list(arg1);
		i_add_a_new_ToDo_as_in_the_ToDo_list(arg2);
	}

	@When("^I click on toggle all button$")
	public void i_click_on_toggle_all_button() throws Throwable {
		ToDoAngularJS page = PageFactory.initElements(driver, ToDoAngularJS.class);
		waitAndClick(page.toggleAllCheckbox, 1, driver, "Toggle all checkbox");
	}

	@Then("^all of the ToDos are marked complete$")
	public void all_of_the_ToDos_are_marked_complete() throws Throwable {
		ToDoAngularJS page = PageFactory.initElements(driver, ToDoAngularJS.class);

		// getting total number of todos
		int totalNumberOfTodos = page.toDoListItems.size();

		// Getting the CSS style of the all ToDo and checking if it's marked complete
		String expectedStyle = "line-through";
		for (int i = 0; i < totalNumberOfTodos; i++) {
			String actualStyle = page.toDoListItemsTextLabel.get(i).getCssValue("text-decoration");
			Assert.assertTrue("Actual text style is not matching with expected for Todo item number: " + (i + 1)
					+ " Expected: " + expectedStyle + " Actual: " + actualStyle, actualStyle.contains(expectedStyle));

		}
	}

	@Given("^ToDo \"(.*?)\" is present as active and \"(.*?)\" is present as completed todo$")
	public void todo_is_present_as_active_and_is_present_as_completed_todo(String arg1, String arg2) throws Throwable {
		// adding 2 new todos
		i_add_a_new_ToDo_as_in_the_ToDo_list(arg1);
		i_add_a_new_ToDo_as_in_the_ToDo_list(arg2);

		// Marking second todo as complete
		i_complete_the_existing_ToDo_from_the_list_of_ToDos(arg2);
		i_should_see_a_strike_through_line_on_the_completed_ToDo_in_the_ToDo_list();
	}

	@When("^I click on completed filter button$")
	public void i_click_on_completed_filter_button() throws Throwable {
		ToDoAngularJS page = PageFactory.initElements(driver, ToDoAngularJS.class);
		waitAndClick(page.completedFilter, 2, driver, "Completed filter button");
	}

	@Then("^I only see	\"(.*?)\" todo$")
	public void i_only_see_todo(String arg1) throws Throwable {
		ToDoAngularJS page = PageFactory.initElements(driver, ToDoAngularJS.class);

		// getting total number of todos present
		int totalNumberofToDos = page.toDoListItems.size();

		Assert.assertTrue("More then 1 ToDo items present in the list. Total number is: " + totalNumberofToDos,
				totalNumberofToDos == 1);

		// checking text of current todo item
		String actualText = page.toDoListItems.get(0).getText();
		Assert.assertTrue("Actual text of todo item present is not matching with expected text. Actual: "
				+ actualText + " Expected: " + arg1, arg1.equalsIgnoreCase(actualText));
	}
	
	@When("^I click clear button for \"(.*?)\" ToDo$")
	public void i_click_clear_button_for_ToDo(String arg1) throws Throwable {
		ToDoAngularJS page = PageFactory.initElements(driver, ToDoAngularJS.class);
		
		// getting total number of todos present
		int totalNumberofToDos = page.toDoListItems.size();
		
		//looping through all todos
		for (int i = 0; i < totalNumberofToDos; i++) {
			//Checking todo text and clearing it by pressing the clear button
			String currentToDoText = page.toDoListItems.get(i).getText();
			if (currentToDoText.equalsIgnoreCase(arg1)) {
				mouseHover(page.toDoListItemsTextLabel.get(i), driver);
				waitAndClick(page.clearToDoButton.get(i), 1, driver, "Clear Todo button for item: "+(i+1));
				totalNumberofToDos = page.toDoListItems.size();
			}
		}
		
	}
	
	@Given("^ToDos \"(.*?)\" and \"(.*?)\" are already present in completed state$")
	public void todos_and_are_already_present_in_completed_state(String arg1, String arg2) throws Throwable {
		//Adding 2 new todos
		todos_and_are_already_present(arg1, arg2);
		
		//marking all todos as complete
		i_click_on_toggle_all_button();
	}

	@When("^I click on clear commpleted button$")
	public void i_click_on_clear_commpleted_button() throws Throwable {
		ToDoAngularJS page = PageFactory.initElements(driver, ToDoAngularJS.class);
		waitAndClick(page.clearCommpleted, 2, driver, "Clear complete button");
	}

	@Then("^both the completed ToDo are removed$")
	public void both_the_completed_ToDo_are_removed() throws Throwable {
		ToDoAngularJS page = PageFactory.initElements(driver, ToDoAngularJS.class);
		
		// getting total number of todos present
		int totalNumberofToDos = page.toDoListItems.size();
		
		//checking if total number of todo present is to be 0
		Assert.assertTrue("Total number of todos present is greater then 0. Number is: "+totalNumberofToDos, 0==totalNumberofToDos);
	}



}
