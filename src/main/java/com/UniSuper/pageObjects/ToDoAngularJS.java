/**
 * 
 */
package com.UniSuper.pageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.UniSuper.baseClass.BaseClass;

/**
 * Page object for ToDoMVC Angular page
 * 
 * @author gagan_000
 * 
 */
public class ToDoAngularJS extends BaseClass{
	
public WebDriver driver;
	
	@FindBy(id = "new-todo")
	public WebElement newToDoBar;
	
	@FindBy(xpath = "//ul[@id='todo-list']//li")
	public List<WebElement> toDoListItems;
	
	@FindBy(xpath = "//label[@ng-dblclick='editTodo(todo)']")
	public List<WebElement> toDoListItemsTextLabel;

	@FindBy(xpath = "//ul//input[@type='checkbox']")
	public List<WebElement> toDoListItemsCheckbox;
	
	@FindBy(id = "toggle-all")
	public WebElement toggleAllCheckbox;
	
	@FindBy(linkText = "Completed")
	public WebElement completedFilter;
	
	@FindBy(className = "destroy")
	public List<WebElement> clearToDoButton;
	
	@FindBy(id = "clear-completed")
	public WebElement clearCommpleted;
	
	
	
	
	

}
