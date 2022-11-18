package com.sath.testcass;

import static org.testng.Assert.assertFalse;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.sath.base.TestBase;

public class loginTest extends TestBase {

	@Test
	public void logintest() {
		//driver.findElement(By.xpath(or.getProperty("Xloginbtn"))).click();
		
		click("xbankmlogin");
		
				Assert.assertTrue(isElementPresant(By.xpath(or.getProperty("xaddCust"))), "Login not success");
								
	}
}
