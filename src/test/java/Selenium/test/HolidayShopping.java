package Selenium.test;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.TestResultsSummary;
import com.applitools.eyes.selenium.BrowserType;
import com.applitools.eyes.selenium.Configuration;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.fluent.Target;
import com.applitools.eyes.visualgrid.model.DeviceName;
import com.applitools.eyes.visualgrid.model.ScreenOrientation;
import com.applitools.eyes.visualgrid.services.VisualGridRunner;

import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class HolidayShopping {
	static WebDriver driver;
	static VisualGridRunner runner;
	static Eyes eyes;
	static Configuration config; 
	static Properties prop;
	
	@BeforeTest
	public static void initialSetup() {
		try {
		
		prop = new Properties();
		FileInputStream fil = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\util\\config.properties");
		prop.load(fil);
		 driver = new ChromeDriver();
		 runner = new VisualGridRunner(1);
		 eyes = new Eyes(runner);
		 config = new Configuration();
		  
		 config.setApiKey(prop.getProperty("APIkey"));
		 config.setBatch(new BatchInfo("Holiday Shopping"));
		 config.addBrowser(1200, 800, BrowserType.CHROME);	 

		 config.addBrowser(1200, 800, BrowserType.FIREFOX);
		 config.addBrowser(1200, 800, BrowserType.EDGE_CHROMIUM);
		 config.addBrowser(1200, 800, BrowserType.SAFARI);


		config.addDeviceEmulation(DeviceName.iPhone_X, ScreenOrientation.PORTRAIT);
		 
		 
		 eyes.setConfiguration(config);
		
	}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(priority=1)
	public static void mainPageTest() {
		try {
		
			driver.get(prop.getProperty("url"));
				
			eyes.open(driver, "AppliFashion", "Test 1");
			eyes.check(Target.window().fully().withName("main page"));
			eyes.closeAsync();

		} finally {
			eyes.abortAsync();
		}

	}
	
	@Test(priority=2)		
	
	public static void filterByColorTest() {
		try {
		
			eyes.open(driver, "AppliFashion", "Test 2");	
			

			driver.findElement(By.xpath(prop.getProperty("blackLabel_xpath"))).click();
			driver.findElement(By.xpath(prop.getProperty("filterBtn_xpath"))).click();			
	
			
			WebElement shoeRegion= driver.findElement(By.xpath(prop.getProperty("productsGrid_xpath")));
			eyes.checkRegion(shoeRegion,"filter by color");			
			eyes.closeAsync();
			
			List<WebElement> products= driver.findElements(By.xpath(prop.getProperty("productGridList_xpath")));		
			Assert.assertEquals(products.size(), 2 , " More than 2 products displayed");

		} finally {
			eyes.abortAsync();
		}

	}
	
	
	@Test(priority=3)		
	
	public static void produceDetailsTest() {
		try {
		
			eyes.open(driver, "AppliFashion", "Test 3");		
			
			driver.findElement(By.xpath(prop.getProperty("shoes_xpath"))).click();
			
			eyes.check(Target.window().fully().withName("product details"));
			
			eyes.closeAsync();

		} finally {
			eyes.abortAsync();
		}

	}
	
	
	
	@AfterTest
	
	public static void tearDown() {

		driver.quit();
		
		TestResultsSummary allTestResults = runner.getAllTestResults(false);
		System.out.println(allTestResults);
		
		eyes.abortAsync();
	}
	
	

}































