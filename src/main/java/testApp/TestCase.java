package testApp;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.testng.SkipException;
import org.testng.annotations.Test;

import testReports.TestReports;
import util.TestUtil;

public class TestCase {
	public static Logger app_logs = Logger.getLogger("appLogger");
	public static Hashtable<String,String> testcasedata = new Hashtable<String,String>();
	public static String currentTest;	

	@Test
	public void ExecuteTests(){
		ArrayList<String> keyword = new ArrayList<String>();
		//Get driver data
		Object[][] driverData = TestUtil.getDriverData();
		TestReports.startSuite("TestCase");
		//loop thru driver data
		for(int i=1;i<driverData.length;i++){
			if(driverData[i][2].equals("Y")){
				currentTest = driverData[i][0].toString();
				String startTestTime = TestUtil.now("MMM.dd.yyyy hh.mm.ss aaa");
				//get test data 
				Object[][] testData = TestUtil.getTestData(driverData[i][0].toString());
				//loop thru each test data
				for(int j=1;j<testData.length;j++){
					testcasedata.clear();
					int cols = testData[0].length-1;
					for(int c=0;c<cols;c++){
						testcasedata.put(testData[0][c].toString(), testData[j][c].toString());	
					}
					//loop thru all the keywords
					app_logs.info("Executing test case "+driverData[i][0].toString());
					keyword = TestUtil.getKeys("BusinessFlow", driverData[i][0].toString());
					TestReports.keyword = keyword;
					try{
						for(String key:keyword){						
						 Method method = Keywords.class.getDeclaredMethod(key);
						 method.invoke(method);
						 System.out.println("Invoked Method "+key);						
						}
						TestReports.addTestCase(currentTest, startTestTime, TestUtil.now("MMM.dd.yyyy hh.mm.ss aaa"), "Pass");
					}catch(Exception e){
						TestReports.addTestCase(currentTest, startTestTime, TestUtil.now("MMM.dd.yyyy hh.mm.ss aaa"), "Fail");
					}
				}
			}else{
				app_logs.info("Skipping the test case "+driverData[i][1].toString());
				throw new SkipException("Skipped test "+driverData[i][1].toString());				
			}
		}
		
	}
}
