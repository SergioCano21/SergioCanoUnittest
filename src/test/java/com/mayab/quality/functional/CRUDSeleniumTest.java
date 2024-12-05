package com.mayab.quality.functional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.fail;

import org.junit.jupiter.api.Test;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@TestMethodOrder(OrderAnnotation.class)
public class CRUDSeleniumTest {

	private static WebDriver driver;
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();
	JavascriptExecutor js;
  
	private void pause(long milis) {
		try {
			Thread.sleep(milis);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
  
	@BeforeEach
	public void setUp() throws Exception {		
		WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        driver = new ChromeDriver(options);
        baseUrl = "https://www.google.com/";
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
        js = (JavascriptExecutor) driver;
	}
	
	public void takeScreenshot(String fileName) throws IOException {
    	File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    	FileUtils.copyFile(file, new FileOutputStream("src/screenshots/" + fileName + ".jpeg"));
    }
  
	@Test
	@Order(1)
	public void Add_User_Correct() throws Exception {
		driver.get(baseUrl + "chrome://newtab/");
        driver.get("https://mern-crud-mpfr.onrender.com");
		driver.findElement(By.xpath("//div[@id='root']/div/div[2]/button")).click();
	    driver.findElement(By.name("name")).click();
	    driver.findElement(By.name("name")).clear();
	    driver.findElement(By.name("name")).sendKeys("Sergio Cano");
	    driver.findElement(By.name("email")).click();
	    driver.findElement(By.name("email")).clear();
	    driver.findElement(By.name("email")).sendKeys("sergio.cano@gmail.com");
	    driver.findElement(By.name("age")).click();
	    driver.findElement(By.name("age")).clear();
	    driver.findElement(By.name("age")).sendKeys("22");
	    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Gender'])[2]/following::div[2]")).click();
	    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Male'])[1]/following::div[2]")).click();
	    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Woah!'])[1]/following::button[1]")).click();
	    pause(1000);
	    takeScreenshot("Add_User_Correct");
	    String result = driver.findElement(By.xpath("/html/body/div[3]/div/div[2]/form/div[4]/div/p")).getText();
	    assertEquals(result, "Successfully added!error");
	    driver.findElement(By.xpath("//i")).click();	
	}
	
	@Test
	@Order(2)
	public void Add_User__Not_Correct() throws Exception {
		driver.get(baseUrl + "chrome://newtab/");
        driver.get("https://mern-crud-mpfr.onrender.com");
		driver.findElement(By.xpath("//div[@id='root']/div/div[2]/button")).click();
	    driver.findElement(By.name("name")).click();
	    driver.findElement(By.name("name")).clear();
	    driver.findElement(By.name("name")).sendKeys("Sergio Cano");
	    driver.findElement(By.name("email")).click();
	    driver.findElement(By.name("email")).clear();
	    driver.findElement(By.name("email")).sendKeys("sergio.cano@gmail.com");
	    driver.findElement(By.name("age")).click();
	    driver.findElement(By.name("age")).clear();
	    driver.findElement(By.name("age")).sendKeys("22");
	    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Gender'])[2]/following::div[2]")).click();
	    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Male'])[1]/following::div[2]")).click();
	    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Woah!'])[1]/following::button[1]")).click();
	    pause(1000);
	    takeScreenshot("Add_User__Not_Correct");
	    String result = driver.findElement(By.xpath("/html/body/div[3]/div/div[2]/form/div[5]/div/p")).getText();
	    assertEquals(result, "That email is already taken.");
	    driver.findElement(By.xpath("//i")).click();
	}

	@Test
	@Order(3)
	public void Update_User_Age() throws Exception {
		driver.get(baseUrl + "chrome://newtab/");
        driver.get("https://mern-crud-mpfr.onrender.com");
	    driver.findElement(By.xpath("//div[@id='root']/div/div[2]/table/tbody/tr/td[5]/button")).click();
	    pause(1000);
	    driver.findElement(By.name("age")).click();
	    driver.findElement(By.name("age")).clear();
	    driver.findElement(By.name("age")).sendKeys("30");
	    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Woah!'])[1]/following::button[1]")).click();
	    pause(1000);
	    takeScreenshot("Update_User_Age");
	    String result = driver.findElement(By.xpath("/html/body/div[3]/div/div[2]/form/div[4]/div/p")).getText();
	    assertEquals(result, "Successfully updated!");
	    driver.findElement(By.xpath("//i")).click();
	}
	
	@Test
	@Order(6)
	public void Delete_User() throws Exception {
		driver.get(baseUrl + "chrome://newtab/");
        driver.get("https://mern-crud-mpfr.onrender.com");
		driver.findElement(By.xpath("//div[@id='root']/div/div[2]/table/tbody/tr/td[5]/button[2]")).click();
		pause(1000);
		takeScreenshot("Delete_User");
	    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Sergio Cano'])[2]/following::button[1]")).click();
	}
	
	@Test
	@Order(4)
	public void Find_User_By_Name() {
		driver.get(baseUrl + "chrome://newtab/");
        driver.get("https://mern-crud-mpfr.onrender.com");
		WebElement tbody = driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/table/tbody"));
		List<WebElement> rows = tbody.findElements(By.tagName("tr"));
		List<List<String>> tableData = new ArrayList<>();
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            List<String> rowData = new ArrayList<>();
            for (WebElement cell : cells) {
                rowData.add(cell.getText());
            }
            tableData.add(rowData);
        }
        
        boolean result = false;
        List<String> user_data = new ArrayList<>();
        for (List<String> list: tableData) {
        	if (list.contains("Sergio Cano")) {
                result = true;
                user_data = list;
                break;
            }
        }
        assertTrue(result);
        
        System.out.println("------------------------");
        System.out.println(user_data);
        System.out.println("------------------------");
	}
	
	@Test
	@Order(5)
	public void Find_All_Users() {
		driver.get(baseUrl + "chrome://newtab/");
        driver.get("https://mern-crud-mpfr.onrender.com");
		WebElement tbody = driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/table/tbody"));
		List<WebElement> rows = tbody.findElements(By.tagName("tr"));
		List<List<String>> tableData = new ArrayList<>();
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            List<String> rowData = new ArrayList<>();
            for (WebElement cell : cells) {
                rowData.add(cell.getText());
            }
            tableData.add(rowData);
        }
        
        System.out.println("------------------------");
        System.out.println(tableData);
        System.out.println("------------------------");
	}

	@AfterEach
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
}
