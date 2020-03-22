package com.extentreport;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class Demo1 
{

	public ExtentHtmlReporter htmlreporter;
	public ExtentReports extent;
	public ExtentTest extentTest;
	public static WebDriver driver;
	
	@BeforeClass
	public void beforeClass()
	{
		
		htmlreporter= new ExtentHtmlReporter("extent.html");
		htmlreporter.config().setEncoding("utf-8");
		htmlreporter.config().setDocumentTitle("AutomationReport");
		htmlreporter.config().setReportName("Kiodex UI Extent Report");
		htmlreporter.config().setTheme(Theme.STANDARD);
		
		extent=new ExtentReports();
		extent.setSystemInfo("Organization", "Lets Kode IT");
		extent.setSystemInfo("Browser", "Chrome");
		extent.attachReporter(htmlreporter);
		System.setProperty("webdriver.chrome.driver", "F:\\libs\\chromedriver_win32\\chromedriver.exe");
		driver=new ChromeDriver();
		
	}
	
	@Test(priority = 0)
	public void testSuccess()
	{
		
		
		driver.get("https://www.letskodeit.com");
		extentTest=extent.createTest("Sussfull Test");
		
	}
	
	@Test(priority = 1)
	public void testFailed()
	{
		extentTest=extent.createTest("Failed Test");
		Assert.fail("Executing Failed Test Case");
		
	}
	@Test(priority = 2)
	public void testSkipped()
	{
		extentTest=extent.createTest("Skipped Test");
		throw new SkipException("Executing Skipped Test Case");
		
	}
	
	@AfterMethod
	public void afterMethod(ITestResult result) throws Exception
	{
		if(result.getStatus()==ITestResult.FAILURE)
		{
			extentTest.fail("Test Method << " +result.getName()+" >> is Failed");
			extentTest.fail(result.getThrowable());
			extentTest.fail((Markup) extentTest.addScreenCaptureFromPath(getScreenhot(result.getName())));
			//extentTest.log(Status.FAIL, 
				//	(Markup) extentTest.addScreenCaptureFromPath(getScreenhot(result.getName())));
			
		}
		else if(result.getStatus()==ITestResult.SUCCESS)
		{
			extentTest.log(Status.PASS, "Test Method << " +result.getName()+" >> is Passed" );
			
		}
		else if(result.getStatus()==ITestResult.SKIP)
		{
			extentTest.skip( "Test Method << "+result.getName()+" >> is Skipped");
			extentTest.log(Status.SKIP, "Test Method << "+result.getName()+" >> is Skipped" );
			
		}
		
	}
	
	public static String getScreenhot(String screenshotName) throws Exception 
	{
		 String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		 TakesScreenshot ts = (TakesScreenshot) driver;
		 File source = ts.getScreenshotAs(OutputType.FILE);
		 String destination = System.getProperty("user.dir") + "/FailedTestsScreenshots/"+screenshotName+dateName+".png";
		 File finalDestination = new File(destination);
		 FileUtils.copyFile(source, finalDestination);
		 return destination;
	}
	
	@AfterClass(alwaysRun = true)
	public void tearDown()
	
	{
		driver.quit();
		extent.flush();
	}
	
}
