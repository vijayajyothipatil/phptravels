package testApp;

import org.openqa.selenium.By;
import testReports.TestReports;
import util.TestUtil;
import driver.Driver;

public class Keywords extends Driver{
	
	public static void Login(){
		try{
			driver.findElement(By.xpath(object.getProperty("Account_link"))).click();
			driver.findElement(By.xpath(object.getProperty("Login_link"))).click();
			driver.findElement(By.xpath(object.getProperty("Email_edit"))).sendKeys(TestCase.testcasedata.get("Username"));
			driver.findElement(By.xpath(object.getProperty("Password_edit"))).sendKeys(TestCase.testcasedata.get("Password"));
			driver.findElement(By.xpath(object.getProperty("Login_button"))).click();
			TestReports.teststatus.add("Pass");
			TestReports.screenShotPath.add("");
		}catch(Exception e){
			TestReports.teststatus.add("Fail");
			TestReports.screenShotPath.add(TestUtil.captureScreenshot());
		}
		
	}
}
