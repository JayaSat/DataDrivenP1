package com.sath.testcass;

import java.io.IOException;
import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.sath.base.TestBase;
import com.sath.utility.TestUtil;

public class addCustTest extends TestBase {

	@Test(dataProviderClass=TestUtil.class, dataProvider ="dp")
	public void addCusttest(Hashtable<String,String> data) throws InterruptedException, IOException {
		
	
		
		 if (!data.get("runmode").equalsIgnoreCase("y")) { throw new
		  SkipException(" Skiped the test"); }
		
		click("xaddCust");
		sendkeys("xfname", data.get("fname"));
		sendkeys("xlname", data.get("lname"));
		sendkeys("xpcode", data.get("post code"));
		//VerifyEquals("a", "fe");
	
		click("xcussub");
		
	Alert alert=wait.until(ExpectedConditions.alertIsPresent());
	
	Assert.assertTrue(alert.getText().contains(data.get("alert")));
	//Assert.
	
	//Thread.sleep(2000);
	alert.accept();
		
	}
	
		
	
}
