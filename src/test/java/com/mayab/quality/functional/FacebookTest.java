package com.mayab.quality.functional;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.fail;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;

public class FacebookTest {

    private static WebDriver driver;
    private String baseUrl;
    private StringBuffer verificationErrors = new StringBuffer();
    JavascriptExecutor js;

    private void pause(long mils) {
    	  try {
    		  Thread.sleep(mils);
    	  }
    	  catch(Exception e) {
    		  e.printStackTrace();
    	  }
      }
    
    @Before
    public void setUp() throws Exception {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        baseUrl = "https://www.facebook.com/";
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
        js = (JavascriptExecutor) driver;
    }

    @Test
    public void Facebook_Login_Fail() throws Exception {
        driver.get(baseUrl);
        pause(3000);
        driver.findElement(By.id("email")).clear();
        driver.findElement(By.id("email")).sendKeys("coches33");
        driver.findElement(By.id("pass")).clear();
        driver.findElement(By.id("pass")).sendKeys("coches33");
        driver.findElement(By.name("login")).click();
        takeScreenshot("Foto");
        pause(3000);

        String result = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[1]/div/div[2]/div[2]/form/div/div[1]/div[2]")).getText();
        assertThat(result, is("El correo electrónico o número de celular que ingresaste no está conectado a una cuenta. Encuentra tu cuenta e inicia sesión."));
        pause(3000);

    }
    
    public static void takeScreenshot(String fileName) throws IOException {
    	File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    	FileUtils.copyFile(file, new FileOutputStream("src/screenshots/" + fileName + ".jpeg"));
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }
}
