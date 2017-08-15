/**
 * 
 */
package com.UniSuper.pageObjects;

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

}
