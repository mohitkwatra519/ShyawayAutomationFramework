package components;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	public WebDriver driver;
	Properties prop ;
	public WebDriver initializeDriver() throws IOException {
		prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\configfile.properties");
		
		prop.load(fis);
		String browserName = System.getProperty("browser") != null
		        ? System.getProperty("browser")
		        : prop.getProperty("browser");
		browserName = browserName.toLowerCase().trim();
		
		if (browserName.contains("chrome")) {

		    ChromeOptions options = new ChromeOptions();

		    boolean headless = Boolean.parseBoolean(
		        System.getProperty("headless", "false")
		    );

		    System.out.println("Browser: " + browserName);
		    System.out.println("Headless mode: " + headless);

		    if (headless) {
		        options.addArguments("--headless=new");
		    }

		    options.addArguments("--window-size=1440,900");

		    driver = new ChromeDriver(options);
		}//full screen
		else if(browserName.equalsIgnoreCase("edge")) {
			driver = new EdgeDriver();
		}
		else if(browserName.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
		}
		else {
	        throw new IllegalArgumentException(
	                "Unsupported browser: " + browserName);
	    }
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
		driver.manage().window().maximize();
		return driver;
		
		
	}
	
	public WebDriver getDriver() {
	    return driver;
	}
	
	@BeforeMethod
	public void setUp() throws IOException {
	    driver = initializeDriver();
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
	    if (driver != null) {
	        driver.quit();
	        driver = null;
	    }
	}
}
