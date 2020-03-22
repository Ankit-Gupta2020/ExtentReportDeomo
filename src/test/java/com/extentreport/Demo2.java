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
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class Demo2 
{

	
	
	public static WebDriver driver;
	
	@BeforeSuite
	public void beforeSuit()
	{
		System.setProperty("webdriver.chrome.driver", "F:\\libs\\chromedriver_win32\\chromedriver.exe");
		driver=new ChromeDriver();
		
	}
	
	@Test(priority = 0)
	public void testSuccess()
	{
		driver.get("https://www.letskodeit.com");
		
		
	}
	
	@Test(priority = 1)
	public void testFailed()
	{
		Assert.fail("Executing Failed Test Case");
		
	}
	@Test(priority = 2)
	public void testSkipped()
	{
		throw new SkipException("Executing Skipped Test Case");
		
	}

	@AfterSuite(alwaysRun = true)
	public void tearDown()
	
	{
		driver.quit();
	}
	
}
