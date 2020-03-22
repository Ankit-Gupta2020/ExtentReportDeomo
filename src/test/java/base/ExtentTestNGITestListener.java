package base;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.extentreport.Demo2;

public class ExtentTestNGITestListener implements ITestListener {
	private static ExtentReports extent = ExtentReportManager.getInstance();
	private static ThreadLocal <ExtentTest>extentTest = new ThreadLocal<ExtentTest>();
    
	
    public  void onStart(ITestContext context) {
    	
	}

	public  void onFinish(ITestContext context) {
		extent.flush();
	}
	
	public void onTestStart(ITestResult result) {
		ExtentTest test = extent.createTest(result.getTestClass().getName()+"::"
				+ result.getMethod().getMethodName());
		extentTest.set(test);
	}

	public  void onTestSuccess(ITestResult result) 
	{
	
		String logText="<b>Test Method " + result.getMethod().getMethodName() + " Successfull<b>";
		Markup m = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
		extentTest.get().log(Status.PASS, m);
		//extentTest.get().log(Status.PASS, "Test Method << " +result.getName()+" >> is Passed");
	}

	public void onTestFailure(ITestResult result) 
	{
		String path = null;
		String method=result.getMethod().getMethodName();
		//ITestContext context = result.getTestContext();
		//WebDriver driver = (WebDriver)context.getAttribute("driver");
		try {
			 path=getScreenhot(method);
		} catch (Exception e) {
		
			e.printStackTrace();
		}
		extentTest.get().log(Status.FAIL,  "<b><font color=red> Test Method << " +result.getName()+" >> "
				+ "is Failed" + "</font></b>");
		extentTest.get().fail(result.getThrowable());
		try {
			extentTest.get().fail("<b><font color=red> " + "Screenshot of failure " + "</font></b>", 
					MediaEntityBuilder.createScreenCaptureFromPath(path).build());
		} catch (IOException e) {
		
			e.printStackTrace();
		}
	}

	public void onTestSkipped(ITestResult result) 
	{
		String logText="<b>Test Method " + result.getMethod().getMethodName() + " SKIPPED<b>";
		Markup m = MarkupHelper.createLabel(logText, ExtentColor.ORANGE);
		extentTest.get().log(Status.SKIP, m);
		//extentTest.get().log(Status.SKIP, "Test Method << " +result.getName()+" >> is Skipped");
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		
	}
	
	
	
	public static String getScreenhot(String screenshotName) throws Exception 
	{
		 String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		 TakesScreenshot ts = (TakesScreenshot) Demo2.driver;
		 File source = ts.getScreenshotAs(OutputType.FILE);
		 String destination = System.getProperty("user.dir") + "/FailedTestsScreenshots/"+screenshotName+dateName+".png";
		 File finalDestination = new File(destination);
		 FileUtils.copyFile(source, finalDestination);
		 return destination;
	}
}