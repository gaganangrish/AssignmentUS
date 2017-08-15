/**
 * 
 */
package com.UniSuper.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.UniSuper.baseClass.BaseClass;


/**
 * Page object for ToDoMVC home page
 * 
 * @author gagan_000
 *
 */
public class ToDoMVCHome extends BaseClass{
	
	public WebDriver driver;
	
	@FindBy(xpath = "//a[text()='AngularJS']")
	public WebElement angularJSLink;

}
