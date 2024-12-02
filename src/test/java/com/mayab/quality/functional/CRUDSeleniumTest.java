package com.mayab.quality.functional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.fail;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

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
		driver = new ChromeDriver();
		baseUrl = "https://mern-crud-mpfr.onrender.com/";
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		js = (JavascriptExecutor) driver;
	}
  
	@Test
	@Order(1)
	public void Add_User_Correct() throws Exception {
		driver.get(baseUrl);
		pause(1000);
		driver.findElement(By.xpath("//div[@id='root']/div/div[2]/button")).click();
	    driver.findElement(By.name("name")).click();
	    driver.findElement(By.name("name")).clear();
	    driver.findElement(By.name("name")).sendKeys("Sergio Cano");
	    pause(1000);
	    driver.findElement(By.name("email")).click();
	    driver.findElement(By.name("email")).clear();
	    driver.findElement(By.name("email")).sendKeys("sergio.cano@gmail.com");
	    pause(1000);
	    driver.findElement(By.name("age")).click();
	    driver.findElement(By.name("age")).clear();
	    driver.findElement(By.name("age")).sendKeys("22");
	    pause(1000);
	    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Gender'])[2]/following::div[2]")).click();
	    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Male'])[1]/following::div[2]")).click();
	    pause(1000);
	    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Woah!'])[1]/following::button[1]")).click();
	    pause(1000);
	    String result = driver.findElement(By.xpath("/html/body/div[3]/div/div[2]/form/div[4]/div/p")).getText();
	    assertThat(result, is("Successfully added!"));
	    driver.findElement(By.xpath("//i")).click();
	    pause(1000);
	}
	
	@Test
	@Order(2)
	public void Add_User__Not_Correct() throws Exception {
		driver.get(baseUrl);
		pause(1000);
		driver.findElement(By.xpath("//div[@id='root']/div/div[2]/button")).click();
	    driver.findElement(By.name("name")).click();
	    driver.findElement(By.name("name")).clear();
	    driver.findElement(By.name("name")).sendKeys("Sergio Cano");
	    pause(1000);
	    driver.findElement(By.name("email")).click();
	    driver.findElement(By.name("email")).clear();
	    driver.findElement(By.name("email")).sendKeys("sergio.cano@gmail.com");
	    pause(1000);
	    driver.findElement(By.name("age")).click();
	    driver.findElement(By.name("age")).clear();
	    driver.findElement(By.name("age")).sendKeys("22");
	    pause(1000);
	    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Gender'])[2]/following::div[2]")).click();
	    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Male'])[1]/following::div[2]")).click();
	    pause(1000);
	    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Woah!'])[1]/following::button[1]")).click();
	    pause(1000);
	    
	    String result = driver.findElement(By.xpath("/html/body/div[3]/div/div[2]/form/div[5]/div/p")).getText();
	    assertThat(result, is("That email is already taken."));
	    driver.findElement(By.xpath("//i")).click();
	    pause(1000);
	}

	@Test
	@Order(3)
	public void Update_User_Age() {
		driver.get(baseUrl);
	    driver.findElement(By.xpath("//div[@id='root']/div/div[2]/table/tbody/tr/td[5]/button")).click();
	    driver.findElement(By.name("age")).click();
	    pause(1000);
	    driver.findElement(By.name("age")).clear();
	    driver.findElement(By.name("age")).sendKeys("30");
	    pause(1000);
	    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Woah!'])[1]/following::button[1]")).click();
	    pause(1000);
	    String result = driver.findElement(By.xpath("/html/body/div[3]/div/div[2]/form/div[4]/div/p")).getText();
	    assertThat(result, is("Successfully updated!"));
	    driver.findElement(By.xpath("//i")).click();
	    pause(1000);
	}
	
	@Test
	@Order(6)
	public void Delete_User() {
		driver.get(baseUrl);
		driver.findElement(By.xpath("//div[@id='root']/div/div[2]/table/tbody/tr/td[5]/button[2]")).click();
		pause(1000);
	    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Sergio Cano'])[2]/following::button[1]")).click();
	    pause(1000);
	}
	
	@Test
	@Order(4)
	public void Find_User_By_Name() {
		driver.get(baseUrl);
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
        assertThat(result, is(true));
        
        System.out.println("------------------------");
        System.out.println(user_data);
        System.out.println("------------------------");
	}
	
	@Test
	@Order(5)
	public void Find_All_Users() {
		driver.get(baseUrl);
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
        
        /*boolean result = false;
        List<String> user_data = new ArrayList<>();
        for (List<String> list: tableData) {
        	if (list.contains("Sergio Cano")) {
                result = true;
                user_data = list;
                break;
            }
        }*/
        //assertThat(result, is(true));
        
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
