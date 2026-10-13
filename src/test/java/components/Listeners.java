package components;


import java.io.ByteArrayInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import components.BaseTest;
import io.qameta.allure.Allure;
import io.qameta.allure.AttachmentOptions;

public class Listeners implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {

        StringBuilder details = new StringBuilder();

        details.append("Test: ")
               .append(result.getMethod().getMethodName())
               .append("\n");

        details.append("Parameters: ")
               .append(Arrays.deepToString(result.getParameters()))
               .append("\n");

        Throwable error = result.getThrowable();

        if (error != null) {
            details.append("Exception: ")
                   .append(error.getClass().getName())
                   .append("\nMessage: ")
                   .append(error.getMessage())
                   .append("\n");

            StringWriter stackTrace = new StringWriter();

            error.printStackTrace(new PrintWriter(stackTrace));

            details.append("\nStack trace:\n")
                   .append(stackTrace);
        }

        WebDriver driver = null;

        try {
            Object instance = result.getInstance();

            if (instance instanceof BaseTest) {
                driver = ((BaseTest) instance).getDriver();
            }

            if (driver != null) {
                details.append("\nCurrent URL: ")
                       .append(driver.getCurrentUrl());

                details.append("\nPage title: ")
                       .append(driver.getTitle());
            }
        } catch (Exception e) {
            details.append("\nBrowser details unavailable: ")
                   .append(e.getMessage());
        }

        // Attach diagnostic details to Allure
        try {
            Allure.attachment(
                "Failure Details",
                "text/plain",
                details.toString()
            );
        } catch (Exception e) {
            System.err.println(
                "Could not attach failure details: " + e.getMessage()
            );
        }

        // Capture screenshot before browser teardown
        if (driver != null && driver instanceof TakesScreenshot) {
            try {
                byte[] screenshot =
                    ((TakesScreenshot) driver)
                        .getScreenshotAs(OutputType.BYTES);

                Allure.attachment(
                    "Failure Screenshot",
                    "image/png",
                    new ByteArrayInputStream(screenshot),
                    AttachmentOptions.withFileExtension(".png")
                );

            } catch (Exception e) {
                System.err.println(
                    "Could not capture screenshot: " + e.getMessage()
                );
            }
        }
    }
}
