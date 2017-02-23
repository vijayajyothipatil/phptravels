package driver;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import testReports.TestReports;
import util.TestUtil;
import datatable.XlFile_Reader;

public class Driver {
	
	public static Properties config = new Properties();
	public static Properties object = new Properties();
	public static XlFile_Reader driverexcel = null;
	public static XlFile_Reader tdexcel = null;
	public static WebDriver driver = null;
	public static Logger app_logs = Logger.getLogger("appLogger");
	public static Logger selenium_logs = Logger.getLogger("rootLogger");
	/*public static Hashtable<String,String> testcasedata = new Hashtable<String,String>();
	public static String currentTest;*/	
	
	@BeforeSuite
	public static void init(){
		
		System.setProperty("webdriver.gecko.driver", "C:\\vpp\\geckodriver.exe");		
		DesiredCapabilities capabilities = DesiredCapabilities.firefox();
		capabilities.setCapability("marionette", false);
		capabilities.setCapability("firefox_binary","C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
		//Start Testing
		String strDate = TestUtil.getCurrentTimeStamp();
		TestReports.startTesting("C:\\Users\\Vijaya\\workspace\\phptravels\\TestReport\\TestResults_"+strDate+".html", TestUtil.now("MMM.dd.yyyy hh.mm.ss aaa"), "Production", "3");
		if (driver==null){
			//loading the config file 
			try{
				app_logs.info("Loading the config file");
				FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\properties\\Config.properties");
				config.load(fis);
			}catch(Exception e){
				app_logs.info(e.getMessage());
				app_logs.info("Loading config file error");
			}
			//loading the object file
			try{
				app_logs.info("Loading the object properties file");
				FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\properties\\Object.properties");
				object.load(fis);
			}catch(Exception e){
				app_logs.info(e.getMessage());
				app_logs.info("Loading object properties file error");
			}
			//Creating excel driver object
			try{
				app_logs.info("Creating the excel driver object");
				driverexcel = new XlFile_Reader(System.getProperty("user.dir")+"\\src\\main\\java\\properties\\Driver.xlsx");
			}catch(Exception e){
				app_logs.info("Error while creating the excel driver object ");
				app_logs.info(e.getMessage());
			}
			//Creating excel test data object
			try{
				app_logs.info("Creating the excel test data object");
				tdexcel = new XlFile_Reader(System.getProperty("user.dir")+"\\src\\main\\java\\properties\\TestData.xlsx");
			}catch(Exception e){
				app_logs.info("Error while creating the excel test data object ");
				app_logs.info(e.getMessage());
			}
			//Creating the driver object
			app_logs.info("Creating the driver object");
			if(config.getProperty("testBrowser").equals("firefox")){
				driver = new FirefoxDriver(capabilities);
			}else if(config.getProperty("testBrowser").equals("ie")){
				driver = new InternetExplorerDriver();
			}else if(config.getProperty("testBrowser").equals("chrome")){
				driver = new ChromeDriver();
			}
			
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.get(config.getProperty("testSiteURL"));
			driver.manage().window().maximize();
			
		}		
	}
	
	
	
	@AfterSuite
	public static void quitDriver(){
		driver.quit();
		TestReports.endSuite();
		//TestReports.updateEndTime( TestUtil.now("MMM.dd.yyyy hh.mm.ss aaa"));
	}
}
