
package shyaway.PageObjects;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import AbstractComponents.Common;

public class SearchResultsPage extends Common {

    WebDriver driver;
    WebDriverWait wait;

    public SearchResultsPage(WebDriver driver) {
    	super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(
            driver, Duration.ofSeconds(10)
        );
    }

    By productItems = By.cssSelector(".producttitle-titlesmallTitle-S92");

    public int getProductCount() {
        List<WebElement> products =  waitForVisibilityOfAllElements(productItems);

        return products.size();
    }

    public void printProductNames() {
        List<WebElement> products =  waitForVisibilityOfAllElements(productItems);

        for (WebElement product : products) {
            //System.out.println(product.getText().trim());
        }
    }
    
    public void selectProductByKeyword(String keyword) {

        List<WebElement> products =
                waitForVisibilityOfAllElements(productItems);

        for (WebElement product : products) {

            if (product.getText()
                    .toLowerCase()
                    .contains(keyword.toLowerCase())) {

                product.click();
                return;
            }
        }

        throw new NoSuchElementException(
                "No product found matching: " + keyword
        );
    }
    
    public void selectFirstProduct() throws InterruptedException {
        waitForVisibilityOfAllElements(productItems).get(0).click();
       
    }
}
