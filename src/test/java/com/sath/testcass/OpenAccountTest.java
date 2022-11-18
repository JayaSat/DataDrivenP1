package com.sath.testcass;

import static org.testng.Assert.assertEquals;

import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.sath.base.TestBase;
import com.sath.utility.TestUtil;

public class OpenAccountTest extends TestBase {

	
	 @Test(dataProviderClass=TestUtil.class, dataProvider ="dp") public void
	  openAccountTest(Hashtable<String, String> data) throws InterruptedException {
		 if (!data.get("runmode").equalsIgnoreCase("y")) {
				throw new SkipException(" Skiped the test");
			}
		 click("xopenAccount");
		 select("xuserSelect", data.get("userName"));
		 select("xcurrencySelect", data.get("currency"));
		 click("xcussub");
		 Alert alert=wait.until(ExpectedConditions.alertIsPresent());
		 Assert.assertTrue(alert.getText().contains(data.get("alertAccount")));
		
		 alert.accept();
		 
	  }
	 
	
		
	
}
