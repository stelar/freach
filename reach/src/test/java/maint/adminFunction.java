package maint;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import maint.Keywords;

public class adminFunction {
	static WebDriver driver = null;
	static String result="";
	static Keywords key=new Keywords();
	public static Properties propAdminPaths = new Properties();
	public static Properties propAdminInput = new Properties();

	public static void setPropertyFile() throws FileNotFoundException, IOException {
		propAdminPaths.load(new FileInputStream(System.getProperty("user.dir")+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"paym"+File.separator+"or.properties"));
		propAdminInput.load(new FileInputStream(System.getProperty("user.dir")+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"paym"+File.separator+"input.properties"));
	}

	public static Properties getAdminUIPaths(){
		return propAdminPaths;
	}

	public static Properties getAdminUIInput(){
		return propAdminInput;
	}
	public static WebDriver launchBrowser(String browser,String url) throws FileNotFoundException, IOException, InterruptedException {
		if (browser.equalsIgnoreCase("Chrome")) { 

			System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+File.separator+"Drivers"+File.separator+"chromedriver.exe");
			Map<String, Object> prefs = new HashMap<String, Object>(); 
			prefs.put("profile.default_content_settings.popups", 0);
				
			prefs.put("download.prompt_for_download", "false");
			ChromeOptions options = new ChromeOptions();		

			options.addArguments("ignore-certificate-errors");
			//				options.setExperimentalOption("prefs",prefs);
			//				options.addArguments("test-type");
							options.addArguments("start-maximized");
			//				options.addArguments("headless");
			//				options.addArguments("disable-gpu");
//							options.addArguments("window-size=1980,960");
							options.addArguments("screenshot");
			//				DesiredCapabilities caps = DesiredCapabilities.chrome();
			//				caps.setCapability(ChromeOptions.CAPABILITY, options);
			DesiredCapabilities handlSSLErr = DesiredCapabilities.chrome ();       
			handlSSLErr.setCapability (CapabilityType.ACCEPT_SSL_CERTS, false);
			handlSSLErr.setCapability (CapabilityType.ACCEPT_INSECURE_CERTS, true);
			handlSSLErr.setCapability(ChromeOptions.CAPABILITY, options);
			driver = new ChromeDriver(handlSSLErr);   
			driver.manage().timeouts().implicitlyWait(3,TimeUnit.SECONDS) ;
			driver.get(url);
			Thread.sleep(5000);
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			String fileLocation= System.getProperty("user.dir")+"\\";
			FileUtils.copyFile(scrFile, new File(fileLocation+"name.png"));
			System.out.println("here");


		}

		return driver;
	}
	public static void passResult(String methodName,StringBuilder sb) throws IOException

	{
			sb.append("<tr><td>"+methodName+"</td><td style=\"background-color: #00FF00\">Pass</td></tr>");
	}
	public static void TakeScreenshot(String name) throws IOException
	{
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String fileLocation= System.getProperty("user.dir")+"\\";
		FileUtils.copyFile(scrFile, new File(fileLocation+name+".png"));
	}
	public static void failResult(String methodName,StringBuilder sb) throws IOException

	{
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String screenShotName=(dateFormat.format(date));
		TakeScreenshot(screenShotName);
		sb.append("<tr><td>"+methodName+"</td><td style=\"background-color: #FF0000\">FAIL</td><td>"+"<a href="+screenShotName+".png>ToImage</a></td></tr>");
	}

	public static String verifyHomePage(WebDriver driver) {
		// TODO Auto-generated method stub
		String loginForm=adminFunction.getAdminUIPaths().getProperty("loginForm");
		try {
			result=key.waitForElement(driver, loginForm, 5);
			if(!result.equalsIgnoreCase("pass"))
				return "fail";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return "fail";
		}
		
		return "pass";
		
	}
	
	public static String userLogin(WebDriver driver){
		String user=adminFunction.getAdminUIPaths().getProperty("user");
		String pass=adminFunction.getAdminUIPaths().getProperty("pass");
		String loginButton=adminFunction.getAdminUIPaths().getProperty("loginButton");
		String greetings=adminFunction.getAdminUIPaths().getProperty("greetings");
		try {
			result=key.waitForElement(driver, user, 5);
			if(!result.equalsIgnoreCase("pass"))
				return "fail";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return "fail";
		}
		try {
			result=key.sendText(driver, user, "Luke");
			if(!result.equalsIgnoreCase("pass"))
				return "fail";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return "fail";
		}
		try {
			result=key.sendText(driver, pass, "Skywalker");
			if(!result.equalsIgnoreCase("pass"))
				return "fail";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return "fail";
		}
		
		try {
			result=key.click(driver, loginButton);
			if(!result.equalsIgnoreCase("pass"))
				return "fail";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return "fail";
		}
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			result=key.waitForElement(driver, greetings, 10);
			if(!result.equalsIgnoreCase("pass"))
				return "fail";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return "fail";
		}
		return "pass";
	}

	public static String createUser(WebDriver driver) {
		// TODO Auto-generated method stub
		
		Random rand = new Random();
		String fname = String.format("%04d", rand.nextInt(10000));
		String lname = String.format("%04d", rand.nextInt(10000));
		String startDate="2012-10-10";
		String email=String.format("%04d", rand.nextInt(10000))+"@gmail.com";
		String fnamePath=adminFunction.getAdminUIPaths().getProperty("fnamePath");
		String lnamePath=adminFunction.getAdminUIPaths().getProperty("lnamePath");
		String datePath=adminFunction.getAdminUIPaths().getProperty("datePath");
		String emailPath=adminFunction.getAdminUIPaths().getProperty("emailPath");
		String add=adminFunction.getAdminUIPaths().getProperty("add");
		String create=adminFunction.getAdminUIPaths().getProperty("createButton");
		String empList=adminFunction.getAdminUIPaths().getProperty("empList");
		try {
			result=key.waitForElement(driver, create, 10);
			if(!result.equalsIgnoreCase("pass"))
				return "fail";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return "fail";
		}
		
		int initCount=0;
		//initial count
		List<WebElement> elems=null;
		try {
			elems = key.ListOfElements(driver, empList);
			System.out.println("Number of elements:" +elems.size());
			initCount=elems.size();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 
		try {
			result=key.click(driver, create);
			if(!result.equalsIgnoreCase("pass"))
				return "fail";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return "fail";
		}
		try {
			result=key.waitForElement(driver, fnamePath, 10);
			if(!result.equalsIgnoreCase("pass"))
				return "fail";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return "fail";
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			result=key.sendText(driver, fnamePath, fname);
			if(!result.equalsIgnoreCase("pass"))
				return "fail";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return "fail";
		}
		
		try {
			result=key.sendText(driver, lnamePath, lname);
			if(!result.equalsIgnoreCase("pass"))
				return "fail";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return "fail";
		}
		
		try {
			result=key.sendText(driver, datePath, startDate);
			if(!result.equalsIgnoreCase("pass"))
				return "fail";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return "fail";
		}
		try {
			result=key.sendText(driver, emailPath, email);
			if(!result.equalsIgnoreCase("pass"))
				return "fail";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return "fail";
		}
		try {
			result=key.click(driver, add);
			if(!result.equalsIgnoreCase("pass"))
				return "fail";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return "fail";
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			result=key.waitForElement(driver, create, 10);
			if(!result.equalsIgnoreCase("pass"))
				return "fail";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return "fail";
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int FinCount=0;
		//initial count
		
		try {
			elems = key.ListOfElements(driver, empList);
			System.out.println("Number of elements:" +elems.size());
			FinCount=elems.size();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (FinCount <= initCount)
		{
			System.out.println("No User Added");
			return "fail";
		}
		
		
		return "pass";
	}

	public static String edituser(WebDriver driver) {
	
		String Firstele="xpath=//ul[@id='employee-list']//li[1]";
		String edit=adminFunction.getAdminUIPaths().getProperty("editButton");
		try {
			result=key.click(driver, Firstele);
			if(!result.equalsIgnoreCase("pass"))
				return "fail";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return "fail";
		}
		
		try {
			result=key.getText(driver, Firstele);
			if(result.equalsIgnoreCase("fail"))
				return "fail";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return "fail";
		}
		String fullName=result;
		System.out.println(fullName);
		String[] parts = fullName.split(" ");
		String part1 = parts[0]; // 004
		String part2 = parts[1];
		System.out.println(part1);
		System.out.println(part2);
		Random rand = new Random();
		String fname = String.format("%04d", rand.nextInt(10000));

		String add=adminFunction.getAdminUIPaths().getProperty("add");
		String create=adminFunction.getAdminUIPaths().getProperty("createButton");
		part1=part1+fname;
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			result=key.waitForElement(driver, edit, 10);
			if(!result.equalsIgnoreCase("pass"))
				return "fail";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return "fail";
		}
		
		
		try {
			result=key.click(driver, edit);
			if(!result.equalsIgnoreCase("pass"))
				return "fail";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return "fail";
		}
		String fnamePath=adminFunction.getAdminUIPaths().getProperty("fnamePath");
		try {
			result=key.waitForElement(driver, fnamePath, 10);
			if(!result.equalsIgnoreCase("pass"))
				return "fail";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return "fail";
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			result=key.sendText(driver, fnamePath, part1);
			if(!result.equalsIgnoreCase("pass"))
				return "fail";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return "fail";
		}
		
		try {
			result=key.click(driver, add);
			if(!result.equalsIgnoreCase("pass"))
				return "fail";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return "fail";
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			result=key.waitForElement(driver, create, 10);
			if(!result.equalsIgnoreCase("pass"))
				return "fail";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return "fail";
		}
		
		String user="xpath=//ul[@id='employee-list']//li[contains(.,'"+part1+" "+part2+"')]";
		try {
			result=key.waitForElement(driver, user, 10);
			if(!result.equalsIgnoreCase("pass"))
				return "fail";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return "fail";
		}
				
		
		return "pass";
	}
	public static String deleteuser(WebDriver driver) {
		// TODO Auto-generated method stub
		String Firstele="xpath=//ul[@id='employee-list']//li[1]";
		String empList=adminFunction.getAdminUIPaths().getProperty("empList");
		String deleteButton=adminFunction.getAdminUIPaths().getProperty("deleteButton");
		int initCount=0;
		//initial count
		List<WebElement> elems=null;
		try {
			elems = key.ListOfElements(driver, empList);
			System.out.println("Number of elements:" +elems.size());
			initCount=elems.size();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		try {
			result=key.click(driver, Firstele);
			if(!result.equalsIgnoreCase("pass"))
				return "fail";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return "fail";
		}
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			result=key.waitForElement(driver, deleteButton, 10);
			if(!result.equalsIgnoreCase("pass"))
				return "fail";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return "fail";
		}
		
		
		try {
			result=key.click(driver, deleteButton);
			if(!result.equalsIgnoreCase("pass"))
				return "fail";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return "fail";
		}
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.switchTo().alert().accept();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		int FinCount=0;
		//initial count
		
		try {
			elems = key.ListOfElements(driver, empList);
			System.out.println("Number of elements:" +elems.size());
			FinCount=elems.size();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (FinCount > (initCount*2))
		{
			
			System.out.println("No User Deleted");
			System.out.println("Fin "+FinCount);
			System.out.println("init "+initCount*2);
			return "fail";
		}
		
		
		return "pass";
	}
	
	
}
