package com.sath.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.sath.utility.ExcelReader;
import com.sath.utility.ExtentManger;
import com.sath.utility.MonitoringMail;
import com.sath.utility.TestConfig;
import com.sath.utility.TestUtil;

public class TestBase {

	/*
	 * Webdriver Properties logs ExtentReports DB Excel Mail
	 * 
	 */
	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties or = new Properties();
	public static FileInputStream fis;
	public static Logger log=Logger.getLogger("devpinoyLogger");
	public static ExcelReader excel=new ExcelReader(System.getProperty("user.dir") + "\\src\\test\\resources\\excel\\testdata.xlsx");
	public static WebDriverWait wait;
	public ExtentReports rep= ExtentManger.getInstance();
	public static ExtentTest test;
	public static String browser;
	public String messagebody;
	@BeforeSuite
	public void setUp() throws IOException, InterruptedException {

		if (driver == null) {
			//PropertyConfigurator.configure(System.getProperty("user.dir") + "\\src\\test\\java\\log4j.properties");
			
			try {
				fis = new FileInputStream(
						System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\Config.properties");
				
				
				config.load(fis);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				fis = new FileInputStream(
						System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\OR.properties");
				or.load(fis);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		if(System.getenv("browser")!=null&&!System.getenv("browser").isEmpty()) {
			browser=System.getenv("browser");
		}else {
			browser=config.getProperty("browser");
		}
		config.setProperty("browser", browser);
		
		if (config.getProperty("browser").equalsIgnoreCase("firefox")) {

			driver = new FirefoxDriver();
			log.debug("Browser firfox invoked");
		} else if (config.getProperty("browser").equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\chromedriver.exe");
			driver = new ChromeDriver();
			log.debug("chrome invoked successfuly");
		} else if (config.getProperty("browser").equalsIgnoreCase("edge")) {
			System.setProperty("webdriver.edge.driver",
					System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\msedgedriver.exe");
			driver = new EdgeDriver();
			log.debug("Browser edge browser invoked");
		}
		driver.get(config.getProperty("bankingURL"));
		driver.manage().window().maximize();
		//Thread.sleep(2000);
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")),
				TimeUnit.SECONDS);
		wait=new WebDriverWait(driver,5);
		log.debug("Browser launced successfuly");
	}

	
	public void click(String click) {
		
			
			if (click.startsWith("x")) {
				driver.findElement(By.xpath(or.getProperty(click))).click();
				log.debug("clicked the "+click+" successfully");	
				test.log(LogStatus.INFO,"Clicked to the "+ click);
			}
			else if (click.startsWith("css")) {
				driver.findElement(By.cssSelector(or.getProperty(click))).click();
				log.debug("clicked the "+click+" successfully");	
				test.log(LogStatus.INFO,"Clicked to the "+ click);
					
				}
				else {
					log.debug("The "+click+" incorrctly added");	
					test.log(LogStatus.FAIL,"The "+click+" incorrctly added");
				}
		
	}

public void sendkeys(String sendkey, String value) {
		
	if (sendkey.startsWith("x")) {
		driver.findElement(By.xpath(or.getProperty(sendkey))).sendKeys(value);
		log.debug("The "+sendkey+" and entered  "+value+" successfully");
		test.log(LogStatus.INFO,"Typing "+value+" to the "+ sendkey);
	}else if (sendkey.startsWith("css")) {
		driver.findElement(By.cssSelector(or.getProperty(sendkey))).sendKeys(value);
		log.debug("The "+sendkey+" entered and entered "+value+" successfully");
		test.log(LogStatus.INFO,"Typing "+value+" to the "+ sendkey);
	}else {
		log.debug("The "+sendkey+" entered incorrectly");
		test.log(LogStatus.FAIL,"The "+sendkey+" entered incorrectly");
	}
		
	}
public static WebElement dropdown;
public void select(String sendkey, String value) {
	Select select;
	if (sendkey.startsWith("x")) {
		dropdown=driver.findElement(By.xpath(or.getProperty(sendkey)));
		select=new Select(dropdown);
		select.selectByVisibleText(value);
		log.debug("The "+sendkey+" and entered  "+value+" successfully");
		test.log(LogStatus.INFO,"Typing "+value+" to the "+ sendkey);
	}else if (sendkey.startsWith("css")) {
		dropdown=driver.findElement(By.cssSelector(or.getProperty(sendkey)));
		select=new Select(dropdown);
		select.selectByVisibleText(value);
		log.debug("The "+sendkey+" and entered  "+value+" successfully");
		test.log(LogStatus.INFO,"Typing "+value+" to the "+ sendkey);
	}else {
		log.debug("The "+sendkey+" entered incorrectly");
		test.log(LogStatus.FAIL,"The "+sendkey+" entered incorrectly");
	}
		
	}

public boolean isElementPresant(By by) {
	
	try {
		driver.findElement(by);
		return true;
	}catch (NoSuchElementException e) {
		return false;
	}
	
}
	public static void VerifyEquals(String value1, String value2) throws IOException {
		try {
			Assert.assertEquals(value1, value2);
		}catch(Throwable t) {
			TestUtil.takeScreenshots();
			//extent reports
			test.log(LogStatus.FAIL, value1+" is not equal to "+value2+ "  "+t);
			test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));
			//Test NG reports
			System.setProperty("org.uncommons.reportng.escape-output", "false");
			Reporter.log(value1+" is not equal to "+value2+"   "+t);
			Reporter.log("<a target=blank href="+TestUtil.screenshotName+"> <img src="+TestUtil.screenshotName+" height=200 width=200></a>");
			
		}
	}
	
	@AfterSuite
	public void tearDown() {

		
		if(driver!=null) {
			
			driver.quit();
			
			log.debug("completed the test successfully");
			MonitoringMail mail=new MonitoringMail();
			
			
			try {
				messagebody = "http://"+InetAddress.getLocalHost().getHostAddress()+":8080/job/ProjectDataDrivanProject/HTML_20Extend_20Report/";
			
				try {
					mail.sendMail(TestConfig.server, TestConfig.from, TestConfig.to, TestConfig.subject, messagebody);
				} catch (AddressException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
			
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

}
