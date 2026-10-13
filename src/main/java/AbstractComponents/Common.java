
package AbstractComponents;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Common implements ElementActions {

    private WebDriver driver;
    protected WebDriverWait wait;

    public Common(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
            driver, Duration.ofSeconds(10)
        );
    }
    
    @Override
    public void clickElement(By locator) {
        wait.until(
            ExpectedConditions.elementToBeClickable(locator)
        ).click();
    }

    @Override
    public void sendKeys(By locator, String text) {
        WebElement element = wait.until(
            ExpectedConditions.visibilityOfElementLocated(locator)
        );
        element.clear();
        element.sendKeys(text);
    }

    protected List<WebElement> waitForVisibilityOfAllElements(
            By locator) {

        return wait.until(
            ExpectedConditions.visibilityOfAllElementsLocatedBy(
                locator
            )
        );
    }
    protected WebElement waitForVisibilityOfElement(By locator) {
        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(locator)
        );
    }
}
