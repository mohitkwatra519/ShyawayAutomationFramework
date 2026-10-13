
package com.sdet.tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import components.BaseTest;
import shyaway.PageObjects.CartPage;
import shyaway.PageObjects.HomePage;
import shyaway.PageObjects.ProductDetailsPage;
import shyaway.PageObjects.SearchResultsPage;
import shyaway.utils.JsonDataReader;
import io.qameta.allure.Allure;

@Listeners({
    
    components.Listeners.class
})
public class FirstBrowserTest extends BaseTest {

	@Test(dataProvider = "productScenarios" )
	public void verifyShyawayProductSearch(
	        HashMap<String, String> scenario) throws Exception {

       

        HomePage homePage = new HomePage(driver);

        Allure.step("Open Shyaway homepage", () -> homePage.goTo());
        String searchTerm = scenario.get("searchTerm");
        Allure.step(
        	    "Search product: " + searchTerm,
        	    () -> homePage.searchProduct(searchTerm)
        	);
        String currentUrl = driver.getCurrentUrl();
        System.out.println(currentUrl);
        Assert.assertTrue(
        	    currentUrl.contains("q=" + searchTerm),
        	    "Search query is missing. URL: " + currentUrl
        	);
        
        SearchResultsPage resultsPage = new SearchResultsPage(driver);
        resultsPage.printProductNames();
        Assert.assertTrue(resultsPage.getProductCount() > 0 , "No products found in search results" );
        //resultsPage.selectFirstProduct();
        //resultsPage.selectProductByKeyword("Fungi Brown");
        String productKeyword = scenario.get("productKeyword");
        Allure.step(
        	    "Select product: " + productKeyword,
        	    () -> resultsPage.selectProductByKeyword(productKeyword)
        	);
        ProductDetailsPage productPage =
                new ProductDetailsPage(driver);

        String productName = productPage.getProductName();

        System.out.println("Selected Product: " + productName);

        Assert.assertFalse(
                productName.isEmpty(),
                "Product name is missing"
        );
        
//        Assert.assertTrue(
//        	    productName.contains("Fashion Bra"),
//        	    "Unexpected product selected: " + productName
//        	);
        String price = productPage.getProductPrice();

        System.out.println("Product Price: " + price);

        Assert.assertFalse(
            price.isEmpty(),
            "Product price is missing"
        );
        String numericPrice = price.replaceAll("[^0-9.]", "");
        double amount = Double.parseDouble(numericPrice);
        Assert.assertTrue(
        	    amount > 0,
        	    "Selling price must be greater than zero"
        	);
        String mrpText = productPage.getMRP();
        System.out.println("MRP: " + mrpText);
        
        String numericMRP = mrpText.replaceAll("[^0-9.]", "");

        Assert.assertFalse(
            numericMRP.isEmpty(),
            "MRP is missing or invalid"
        );

        double mrp = Double.parseDouble(numericMRP);

        Assert.assertTrue(
            mrp > 0,
            "MRP must be greater than zero"
        );

        Assert.assertTrue(
            amount <= mrp,
            "Selling price (" + amount
                + ") must not exceed MRP (" + mrp + ")"
        );

        System.out.println("Price validation passed!");
        String band = scenario.get("band");
        String expectedSize = scenario.get("size");
        if (productPage.hasBandOptions()) {

            // Wired bra flow
        	Allure.step("Select band: " + band,
        	        () -> productPage.selectBand(band));
        	Allure.step("Select size: " + expectedSize,
        		    () -> productPage.selectSize(expectedSize));
        	//productPage.selectSize(expectedSize);

        } else {

            // Bralette flow
            productPage.selectSize(expectedSize);
        }

        Allure.step("Add product to cart",
        	    () -> productPage.addToCart());
        
        Allure.step("Proceed to cart",
        	    () -> productPage.proceedToCart());
        
        System.out.println("Cart URL: " + driver.getCurrentUrl());
        System.out.println("Cart Page Title: " + driver.getTitle());

		CartPage cartPage = new CartPage(driver);
		
		Assert.assertTrue(
		    driver.getCurrentUrl().contains("/cart"),
		    "Cart page was not opened"
		);
		
		int itemCount = cartPage.getCartItemCount();
		
		Assert.assertTrue(
		    itemCount > 0,
		    "Cart is empty"
		);
		
		System.out.println("Cart item count: " + itemCount);
		
		for (int i = 0; i < itemCount; i++) {
		
		    System.out.println(
		        "Cart Product: " + cartPage.getCartProductName(i)
		    );
		
		    System.out.println(
		        "Selected Size: " + cartPage.getCartProductSize(i)
		    );
		
		    System.out.println(
		        "Selling Price: " + cartPage.getCartProductPrice(i)
		    );
		
		    System.out.println(
		        "MRP: " + cartPage.getCartProductMRP(i)
		    );
}


    }
    
    @DataProvider(name = "productScenarios")
    public Object[][] getData() throws IOException {
    	JsonDataReader reader = new JsonDataReader();
    	
        List<HashMap<String, String>> data =
        		reader.getJsonDataToMap(
                System.getProperty("user.dir")
                + "/src/test/resources/testdata/productScenarios.json"
            );

        Object[][] testData = new Object[data.size()][1];

        for (int i = 0; i < data.size(); i++) {
            testData[i][0] = data.get(i);
        }

        return testData;
    }

	
}
