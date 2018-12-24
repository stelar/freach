package maint;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import maint.adminFunction;

public class Keywords {
	public By getLocator(String object)  {

		HashMap<String, String> normaliseLocatorString = normaliseLocatorString(object);
		String locatorName = normaliseLocatorString.get("LocatorName");
		System.out.println(locatorName);
		String locatorValue = normaliseLocatorString.get("LocatorValue");
		System.out.println(locatorValue);
		Method byMethods[];
		byMethods = org.openqa.selenium.By.class.getMethods();
		By elementFindBy = null;
		for (int i = 0; i < byMethods.length; i++) {
			if (byMethods[i].getName().equals(locatorName)) {
				try {
					System.out.println("Found element");
					elementFindBy = (By) byMethods[i].invoke(org.openqa.selenium.By.class, locatorValue);
				} catch (Exception e) {
					//Do nothing
					System.out.println("notfound");
				}
				break;
			}
		}      


		return elementFindBy;

	}
	public void NavigateUrl(WebDriver driver,String url)
	{
		System.out.println("Navigating to "+url);

		driver.get(url);

	}
	public String waitForElement(WebDriver driver,String object, long time) throws InterruptedException, IOException
	{	for(int i = 0;i<time;i++){
		try{
			driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
			driver.findElement(getLocator(object));
			System.out.println("Final i value = " + i);
			driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
			
			return "pass";
		}catch(Exception e){
			System.out.print(i + "  ");
			Thread.sleep(1000);
			//log.error( "failed! = "+object, e );



		}	

	}
	return "pass";
	}

	public String waitForNOElement(WebDriver driver,String object, long time) throws InterruptedException
	{	for(int i = 0;i<time;i++){
		try{
			driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
			driver.findElement(getLocator(object));
			System.out.println("Final i value = " + i);
			driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
			return "pass";
		}catch(Exception e){
			System.out.print(i + "  ");
			Thread.sleep(1000);
			

			return "fail";

		}	

	}
	return "pass";
	}




	public String click(WebDriver driver,String object) throws IOException
	{	
		System.out.println("clicking "+object);
		try
		{
			driver.findElement(getLocator(object)).click();

			return "pass";
		}
		catch (Exception e)
		{	


			System.out.println(e.getStackTrace());			
			return "Fail"+e.getStackTrace();
		}

	}

	public String getText(WebDriver driver,String object) throws IOException
	{	
		System.out.println("texting "+object);
		try
		{
			String x=driver.findElement(getLocator(object)).getText();

			return x;
		}
		catch (Exception e)
		{	


			System.out.println(e.getStackTrace());			
			return "Fail"+e.getStackTrace();
		}

	}
	
	public List<WebElement> ListOfElements(WebDriver driver,String object) throws IOException
	{	
		System.out.println("clicking "+object);
		List<WebElement> elem=null;
		try
		{
			elem=driver.findElements(By.xpath(object));
			System.out.println(elem.size());
			System.out.println("+++++++++++++++++++++++++");
			return elem;
		}
		catch (Exception e)
		{	


			System.out.println(e.getStackTrace());			
			return elem;
		}

	}
	private HashMap<String, String> normaliseLocatorString(String locatorString) {
		System.out.println("Executing NormaliseLocatorString ");

		HashMap<String, String> locatorHashMap = new HashMap<String, String>();
		// SubString BEFORE (=)
		String locatorName = locatorString.substring(0, locatorString.indexOf("="));

		// SubString (=) AFTER
		String afterEqualTo = locatorString.substring(locatorString.indexOf("=") + 1);


		System.out.println(locatorName);
		System.out.println(afterEqualTo);
		locatorHashMap.put("LocatorName", locatorName);
		locatorHashMap.put("LocatorValue", afterEqualTo);

		return locatorHashMap;

	}
	public String sendText(WebDriver driver,String object, String data) throws IOException
	{try
	{
		String url = driver.getCurrentUrl();
		System.out.println(url);
		System.out.println("Send text");
		driver.findElement(getLocator(object)).clear();
		driver.findElement(getLocator(object)).sendKeys(data);
		return "pass";
	}
	catch(Exception e)
	{
		
		e.printStackTrace();
		return "fail";
	}
	}
}
