package maint;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import maint.adminFunction;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
public class Tests {
	static WebDriver driver;
	private final String USER_AGENT = "Mozilla/5.0";
	String methodName="";
	StringBuilder sb=new StringBuilder(""); 
	String result="";
	@BeforeTest
	public void beforeTest() {

		try
		{
			sb.append("<style type=\"text/css\">table.gridtable {	font-family: verdana,arial,sans-serif;	font-size:11px;	color:#333333;	border-width: 1px;	border-color: #666666;	border-collapse: collapse;}table.gridtable th {	border-width: 1px;	padding: 8px;	border-style: solid;	border-color: #666666;	background-color: #dedede;}table.gridtable td {	border-width: 1px;	padding: 8px;	border-style: solid;	border-color: #666666;	background-color: #ffffff;}</style>");
			sb.append("<p> RESULTS</p>");
			sb.append("<table class=\"gridtable\">");
			sb.append("  <tr>        <td> TestCase Name</td>		<td> Status </td>                <td>ScreenShot</td></tr>");


			adminFunction.setPropertyFile();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		}
	
	@Test
	public void openHomepage() {
		methodName=Thread.currentThread().getStackTrace()[1].getMethodName();;
		String content="https://cafetownsend-angular-rails.herokuapp.com/login";
		try{
		driver=adminFunction.launchBrowser("chrome",content);		
		result=adminFunction.verifyHomePage(driver);
		if (result.equalsIgnoreCase("PASS"))
		{
			adminFunction.passResult(methodName, sb);
		}
		
		else
		{
			adminFunction.failResult(methodName, sb);
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	@Test
	public void LoginUser()
	{
		methodName=Thread.currentThread().getStackTrace()[1].getMethodName();;
		try{
			result=adminFunction.userLogin(driver);
			if (result.equalsIgnoreCase("PASS"))
			{
				adminFunction.passResult(methodName, sb);
			}
			
			else
			{
				adminFunction.failResult(methodName, sb);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	@Test
	public void createUser()
	{
		methodName=Thread.currentThread().getStackTrace()[1].getMethodName();;
		try{
			result=adminFunction.createUser(driver);
			if (result.equalsIgnoreCase("PASS"))
			{
				adminFunction.passResult(methodName, sb);
			}
			
			else
			{
				adminFunction.failResult(methodName, sb);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
		}
		
	}
	
	@Test
	public void editUser()
	{
		methodName=Thread.currentThread().getStackTrace()[1].getMethodName();;
		try{
			
			result=adminFunction.edituser(driver);
			if (result.equalsIgnoreCase("PASS"))
			{
				adminFunction.passResult(methodName, sb);
			}
			
			else
			{
				adminFunction.failResult(methodName, sb);
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	@Test
	public void DeleteUser()
	{
		methodName=Thread.currentThread().getStackTrace()[1].getMethodName();;
		try{
			
			result=adminFunction.deleteuser(driver);
			if (result.equalsIgnoreCase("PASS"))
			{
				adminFunction.passResult(methodName, sb);
			}
			
			else
			{
				adminFunction.failResult(methodName, sb);
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	@AfterTest
	public void afterTest() {

		driver.quit();
		try{
			File file1 = new File(System.getProperty("user.dir")+"\\indexNew.html");

			if (!file1.exists()) {
				file1.createNewFile();}
			FileWriter fw=null;
			fw = new FileWriter(file1.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(sb.toString());

			bw.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
	}
}
