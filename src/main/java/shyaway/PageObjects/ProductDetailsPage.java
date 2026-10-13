
package shyaway.PageObjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import AbstractComponents.Common;

public class ProductDetailsPage extends Common {

    WebDriver driver;
    

    public ProductDetailsPage(WebDriver driver) {
    	super(driver);
        this.driver = driver;
       
    }

    By productTitle  = By.cssSelector(".producttitle-titlelargeTitle-oXJ");
    By productPrice = By.cssSelector("span[class*='price-finalPrice-NXz']");
    By mrpPrice = By.cssSelector("span[class*='price-oldPrice']");
    By addTocart = By.xpath("//button[@type='submit']");
    By proceedTocart = By.cssSelector("a[class*='availableOffers-proceedToCart-cAk']");
    
    By bandOptions = By.cssSelector(
    	    ".swiper-slide[class*='option-bandSliderItem'][data-tab] > div");

    By sizeOptions = By.cssSelector(
    	    "button[class*='tile-root'][title]");
    
    public boolean hasBandOptions() {
        wait.until(driver ->
            !driver.findElements(bandOptions).isEmpty()
            || !driver.findElements(sizeOptions).isEmpty()
        );

        return !driver.findElements(bandOptions).isEmpty();
    }
    
    public String getProductName() {
        return waitForVisibilityOfElement(productTitle)
                .getText()
                .trim();
    }
    

	public String getProductPrice() {
	    return waitForVisibilityOfElement(productPrice)
	            .getText()
	            .trim();
	}
	public String getMRP() {
		return waitForVisibilityOfElement(mrpPrice).getText().trim();
	}
    
	public void selectBand(String band) {
		By bandOption = By.xpath(
		        "//div[contains(@class,'option-bandSliderItem') and @data-tab='"
		        + band + "']/div"
		    );
		
		clickElement(bandOption);
	}

	public void selectSize(String size) {

	    String normalizedSize = size.replaceAll("\\s+", "");

	    By sizeOption = By.xpath(
	        "//button[@title='" + normalizedSize + "']"
	    );

	    WebElement option = wait.until(
	        ExpectedConditions.visibilityOfElementLocated(sizeOption)
	    );

	    // Bring the button into the visible center of the page
	    ((JavascriptExecutor) driver).executeScript(
	        "arguments[0].scrollIntoView({block:'center'});",
	        option
	    );
	    clickElement(sizeOption);
	    
	}

	public void addToCart() {
		clickElement(addTocart);
	}
	
	public void proceedToCart() {
		clickElement(proceedTocart);
	}
}