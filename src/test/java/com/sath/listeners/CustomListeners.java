   package com.sath.listeners;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;

import com.relevantcodes.extentreports.LogStatus;
import com.sath.base.TestBase;
import com.sath.utility.MonitoringMail;
import com.sath.utility.TestConfig;
import com.sath.utility.TestUtil;

public class CustomListeners extends TestBase implements ITestListener {

	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		
		System.out.println(TestUtil.isTestRunnable(result.getName(), excel));
		test =rep.startTest(result.getName().toUpperCase());
		if (!TestUtil.isTestRunnable(result.getName(), excel)) {
			throw new SkipException(" Skiped the test");
		}
		
		
	}

	public void onTestSuccess(ITestResult result) {
		TestUtil sc=new TestUtil();
		try {
			sc.takeScreenshots();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		test.log(LogStatus.PASS, result.getName().toUpperCase()+" System is Pass");
		
		// TODO Auto-generated method stub
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		Reporter.log("Login successfully");
		test.log(LogStatus.PASS, test.addScreenCapture(TestUtil.screenshotName));
		
	
		Reporter.log("<a target=blank href="+TestUtil.screenshotName+"> <img src="+TestUtil.screenshotName+" height=200 width=200></a>");
		Reporter.log("<a href="+TestUtil.screenshotName+"> screenshot</a>");
		rep.endTest(test);
		rep.flush();
	}

	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		TestUtil sc=new TestUtil();
		try {
			sc.takeScreenshots();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		test.log(LogStatus.FAIL, result.getName().toUpperCase()+" System is Fail"+" "+result.getThrowable());
		test.log(LogStatus.FAIL, TestUtil.screenshotName);
		test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));
		
		rep.endTest(test);
		rep.flush();
	}

	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
		test.log(LogStatus.SKIP, result.getName().toUpperCase()+" Is Skiped");
		rep.endTest(test);
		rep.flush();
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	
	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub

		//System.out.print(messagebody);
		
		
	}

}
