package com.sath.rough;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.sath.utility.MonitoringMail;
import com.sath.utility.TestConfig;

public class LoggingDemo {

	public static void main(String[] args) throws UnknownHostException, AddressException, MessagingException {
		
		MonitoringMail mail=new MonitoringMail();
		
		String messagebody="http://"+InetAddress.getLocalHost().getHostName()+":8080/job/ProjectDataDrivanProject/HTML_20Extend_20Report/";
		System.out.print(messagebody);
		mail.sendMail(TestConfig.server, TestConfig.from, TestConfig.to, TestConfig.subject, messagebody);
	}

}
