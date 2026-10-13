
package AbstractComponents;

import org.openqa.selenium.By;

public interface ElementActions {

    void clickElement(By locator);

    void sendKeys(By locator, String text);
}
