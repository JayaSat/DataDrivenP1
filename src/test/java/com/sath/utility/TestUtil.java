package com.sath.utility;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Hashtable;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;

import com.sath.base.TestBase;

public class TestUtil extends TestBase {
	public static String screenshotName;
	public static String screenshotPath;
	
	public static void takeScreenshots() throws IOException {
		
		
		Date d=new Date();
		
		screenshotName= (d.toString().replace(":", "_").replace(" ", "_")+".jpg");
		
	File scfile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	FileUtils.copyFile(scfile, new File(System.getProperty("user.dir")+"\\target\\surefire-reports\\html\\"+screenshotName));
	
	}
	@DataProvider(name="dp")
	public Object[][] getData(Method m){
		String sheetName=m.getName();
		log.debug(m.getName()+"getting sss");
		int rows =excel.getRowCount(sheetName);
		int cols =excel.getColumnCount(sheetName);
		//4dc4c76e717442b6b5d5f12aa376872c
		Object[][] data=new Object[rows-1][1];
		Hashtable<String,String> table=null;
		
		for (int rowNum=2;rowNum<=rows;rowNum++)
		{
			table=new Hashtable<String,String>();
			for(int colNum=0;colNum<cols; colNum++) {
				table.put(excel.getCellData(sheetName, colNum, 1), excel.getCellData(sheetName, colNum, rowNum));
				data[rowNum-2][0]=table;
				
			}
			 
		}
		return data;
			
		
	
		
	}
public static boolean isTestRunnable(String testName, ExcelReader excel) {
	
	String sheetName="test_suite";
	int row=excel.getRowCount(sheetName);
	for (int rNum=2; rNum<=row; rNum++) {
		
		String testcase=excel.getCellData(sheetName, "TCID", rNum);
		
		if (testcase.equalsIgnoreCase(testName)) {
			String testEx=excel.getCellData(sheetName, "runmode", rNum);
			if(testEx.equalsIgnoreCase("y"))
			return true;
			else
			return false;
		}
	
	}
	return false;
	
	
	
	
}

}
