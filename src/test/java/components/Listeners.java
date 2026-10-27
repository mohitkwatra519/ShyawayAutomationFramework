package components;


import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import Report.ExtentReportNG;





public class Listeners extends BaseTest implements ITestListener {
	ExtentReports extent = ExtentReportNG.getExtentReports();
	ExtentTest test ;
	ThreadLocal<ExtentTest> threadlocal = new ThreadLocal<ExtentTest>(); 
	
	public void onTestStart(ITestResult result) {
	    test = extent.createTest(result.getMethod().getMethodName()); // -> TC name
	    threadlocal.set(test);
	  }
	
	public void onTestSuccess(ITestResult result) {
		threadlocal.get().log(Status.PASS, "Test Passed");
	  }

	public void onTestFailure(ITestResult result) {
		threadlocal.get().fail(result.getThrowable());
	    try {
			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    //Step 1 Take SS
	  
		String filePath = null;
		try {
			filePath = getScreenshot(result.getMethod().getMethodName() , driver);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	    //Step 2 Attach to report
		threadlocal.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
	  }

	  
	public void onTestSkipped(ITestResult result) {
	    
	  }

	  
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	    
	  }

	  
	public void onTestFailedWithTimeout(ITestResult result) {
	    onTestFailure(result);
	  }

	  
	public void onStart(ITestContext context) {
	    
	  }

	public void onFinish(ITestContext context) {
	    extent.flush(); //Without this report won't be generated
	  }
}