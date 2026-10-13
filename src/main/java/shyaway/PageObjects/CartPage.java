
package shyaway.PageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import AbstractComponents.Common;

public class CartPage extends Common {

    private By cartItems =
        By.cssSelector("li[class*='product-root']");

    private By productLink =
        By.cssSelector("div[class*='product-name'] a");

    private By sellingPrice =
        By.cssSelector("span[class*='price-finalPrice']");

    private By mrpPrice =
        By.cssSelector("span[class*='price-oldPrice']");

    private By dropdownButtons =
        By.cssSelector("div[class*='product-dropDownActionsBtn']");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    private List<WebElement> getCartItems() {
        return waitForVisibilityOfAllElements(cartItems);
    }

    public int getCartItemCount() {
        return getCartItems().size();
    }

    public String getCartProductName(int index) {
        return getCartItems()
                .get(index)
                .findElement(productLink)
                .getText()
                .replaceAll("\\s+", " ")
                .trim();
    }

    public String getCartProductPrice(int index) {
        return getCartItems()
                .get(index)
                .findElement(sellingPrice)
                .getText()
                .trim();
    }

    public String getCartProductMRP(int index) {
        return getCartItems()
                .get(index)
                .findElement(mrpPrice)
                .getText()
                .trim();
    }

    public String getCartProductSize(int index) {
        List<WebElement> options = getCartItems()
                .get(index)
                .findElements(dropdownButtons);

        return options.get(0).getText().trim();
    }
}
