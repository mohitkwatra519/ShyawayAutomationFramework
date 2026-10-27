
package AbstractComponents;

import java.io.IOException;

import org.openqa.selenium.By;

public interface ElementActions {

    void clickElement(By locator);

    void sendKeys(By locator, String text) throws IOException;
}
