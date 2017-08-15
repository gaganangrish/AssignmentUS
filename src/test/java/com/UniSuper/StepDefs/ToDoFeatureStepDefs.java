package com.UniSuper.StepDefs;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ToDoFeatureStepDefs {
	
	@Given("^I am on TodoMVC App home page$")
	public void i_am_on_TodoMVC_App_home_page() throws Throwable {
		System.out.println("In step defs for given");
	}
	
	@When("^I click on AngularJS link on ToDoMVC app home page$")
	public void i_click_on_AngularJS_link_on_ToDoMVC_app_home_page() throws Throwable {
		System.out.println("In step defs for When");
	}

	@Then("^I should navigate to ToDo App page$")
	public void i_should_navigate_to_ToDo_App_page() throws Throwable {
		System.out.println("In step defs for given");
	}

	@Then("^I should see a ToDo bar on the ToDo app page$")
	public void i_should_see_a_ToDo_bar_on_the_ToDo_app_page() throws Throwable {
		System.out.println("In step defs for given");
	}


}
