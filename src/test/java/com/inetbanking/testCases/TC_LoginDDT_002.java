package com.inetbanking.testCases;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.inetbanking.pageObjects.LoginPage;
import com.inetbanking.utilities.XLUtils;


public class TC_LoginDDT_002 extends BaseClass 
{
	JavascriptExecutor js = (JavascriptExecutor) driver;
	
	@Test(dataProvider="LoginData")
	public void loginDDT (String user, String pwd) throws InterruptedException
	{
		
		LoginPage lp = new LoginPage(driver);
		lp.setUsername(user);
		logger.info("username provided");
		lp.setPassword(pwd);
		logger.info("password provided");
		lp.clickSubmit();
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		if(isAlertPresent()==true)
		{
			driver.switchTo().alert().accept(); //clode alert
			driver.switchTo().parentFrame();
			Assert.assertTrue(false);
			logger.warn("Login failed");
		}
		else
		{
			Assert.assertTrue(true);
			logger.info("Login passed");
			Thread.sleep(3000);
			

			//lp.clickLogout();
			//logger.info("logged out");
			Thread.sleep(3000);
			//driver.switchTo().alert().accept();
			driver.switchTo().parentFrame();
						
		}
		
	}
	
	public boolean isAlertPresent() //user defined method to check alert is present or not
	{
		try
		{
		
		driver.switchTo().alert();
		return true;
		}
		catch (NoAlertPresentException e)
		{
			return false;
		}
	}
	
	@DataProvider(name="LoginData")
	String [][] getData() throws IOException
	{
		String path = System.getProperty("user.dir")+"/src/test/java/com/inetbanking/testData/LoginData.xlsx";
		
		int rownum = XLUtils.getRowCount(path, "Sheet1");
		int colcount = XLUtils.getCellCount(path, "Sheet1", 1);
		
		String logindata [][] = new String[rownum][colcount];
		
		for(int i=1;i<=rownum;i++)
		{
			for(int j=0;j<colcount;j++)
			{
				logindata[i-1][j] = XLUtils.getCellData(path, "Sheet1", 1, j); //1 0
				
				
			}
		}
		
		return logindata;
		
	}
	
}
