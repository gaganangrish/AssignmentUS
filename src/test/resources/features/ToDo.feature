@ToDoFeature
Feature: ToDo App Feature
	Background:
	Given I am on TodoMVC App home page

@Test1 @Thread1
Scenario: Click on AngularJS link
	When I click on AngularJS link on ToDoMVC app home page
	Then I should navigate to ToDo App page
	And I should see a ToDo bar on the ToDo app page
	
	@Test2 @Thread2
Scenario: Click on AngularJS link - Thread2
	When I click on AngularJS link on ToDoMVC app home page
	Then I should navigate to ToDo App page
	And I should see a ToDo bar on the ToDo app page