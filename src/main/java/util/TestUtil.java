package util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import testApp.TestCase;
import driver.Driver;

public class TestUtil extends Driver{	
	
	public static Calendar cal = new GregorianCalendar();
	public static  int month = cal.get(Calendar.MONTH);
	public static int year = cal.get(Calendar.YEAR);
	public static  int sec =cal.get(Calendar.SECOND);
	public static  int min =cal.get(Calendar.MINUTE);
	public static  int date = cal.get(Calendar.DATE);
	public static  int day =cal.get(Calendar.HOUR_OF_DAY);
	
	public static Object[][] getDriverData(){
		return driverexcel.getCellData("Driver");
	}
	
	public static Object[][] getTestData(String sheetname){		
		return tdexcel.getCellData(sheetname);
	}
	
	public static ArrayList<String> getKeys(String sheetname,String testcase){
		return tdexcel.getKeyWords(sheetname, testcase);
	}
	
	public static String now(String dateFormat) {
	    Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
	    return sdf.format(cal.getTime());
	    
	}
	
	public static String getCurrentTimeStamp()
    { 
          SimpleDateFormat CurrentDate = new SimpleDateFormat("MM-dd-yyyy"+"_"+"HH-mm-ss");
          Date now = new Date(); 
          String CDate = CurrentDate.format(now); 
          return CDate; 
    }
	
public static String captureScreenshot(){
	String imageName=null;
		try{
			Calendar cal = new GregorianCalendar();
			  int month = cal.get(Calendar.MONTH);
			  int year = cal.get(Calendar.YEAR);
			  int sec =cal.get(Calendar.SECOND);
			  int min =cal.get(Calendar.MINUTE);
			  int date = cal.get(Calendar.DATE);
			  int day =cal.get(Calendar.HOUR_OF_DAY);
		     String ImagePath = "C:\\Users\\Vijaya\\workspace\\phptravels\\TestReport\\Screenshot\\";
			
			  imageName = ImagePath+TestCase.currentTest+"_"+year+"_"+date+"_"+(month)+"_"+day+"_"+min+"_" +sec;
			  
			  File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE); 
			  FileUtils.copyFile(scrFile, new File(imageName+ ".jpeg"));
		}catch(Exception e){
			System.out.println("Error while taking screen shot");
			System.out.println(e.getStackTrace());
		}
		return imageName;	      	      
	}

}
