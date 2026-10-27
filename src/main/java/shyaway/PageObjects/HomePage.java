package shyaway.PageObjects;


import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import AbstractComponents.Common;
import AbstractComponents.ElementActions;

public class HomePage extends Common { 

    WebDriver driver;
    
    public HomePage(WebDriver driver) {
    	super(driver);
		this.driver = driver ;
	}

    By searchBox = By.id("search_query");

    

    public void goTo() {
        driver.get("https://www.shyaway.com/");
    }

	    public void searchProduct(String productName) throws IOException {
	
	        sendKeys(searchBox, productName);
	
	        driver.findElement(searchBox).submit();
	    }

}
