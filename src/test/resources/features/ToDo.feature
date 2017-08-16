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
Scenario: Add new ToDo
	And I navigate to ToDo App page
	When I add a new ToDo as "buy paper"
	Then newly added ToDo "buy paper" should display in the ToDo list
	
@Test3 @Thread1
Scenario: Edit existing ToDo
	And I navigate to ToDo App page
	When I add a new ToDo as "buy paper" in the ToDo list
	And I update the existing ToDo from "buy paper" to "buy paper from coles"
	Then I should see the updated ToDo item as "buy paper from coles" in the ToDo list
	
@Test4 @Thread2
Scenario: Complete an existing ToDo
	And I navigate to ToDo App page
	When I add a new ToDo as "buy paper" in the ToDo list
	And I complete the existing "buy paper" ToDo from the list of ToDos
	Then I should see a strike through line on the completed ToDo in the ToDo list
	
@Test5 @Thread1
Scenario: Reactive an existing completed ToDo
	And I navigate to ToDo App page
	And an exiting "buy paper" ToDo is already present and is completed
	When I reactivate the "buy paper" ToDo from the ToDo list
	Then ToDo "buy paper" should get reactivated 
	
	
@Test6 @Thread2
Scenario: Add 2 new ToDos
	And I navigate to ToDo App page
	When I add a new ToDo as "buy paper"
	Then newly added ToDo "buy paper" should display in the ToDo list
	When I add a new ToDo as "buy printer"
	Then newly added ToDo "buy printer" should display in the ToDo list
	
@Test7 @Thread1
Scenario: Complete all ToDos
	And I navigate to ToDo App page
	And ToDos "buy paper" and "buy printer" are already present
	When I click on toggle all button
	Then all of the ToDos are marked complete	
	
@Test8 @Thread2
Scenario: Filter Todos by completed state
	And I navigate to ToDo App page
	And ToDo "buy paper" is present as active and "buy printer" is present as completed todo
	When I click on completed filter button
	Then I only see	"buy printer" todo
	
@Test9 @Thread1
Scenario: Clear a single ToDo
	And I navigate to ToDo App page
	And ToDo "buy paper" is present as active and "buy printer" is present as completed todo
	When I click clear button for "buy paper" ToDo
	Then I only see	"buy printer" todo

@Test10 @Thread2
Scenario: Clear all completed ToDos
	And I navigate to ToDo App page
	And ToDos "buy paper" and "buy printer" are already present in completed state
	When I click on clear commpleted button
	Then both the completed ToDo are removed