package com.softserve.edu;

import java.time.Duration;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SelTest {
    private final String BASE_URL = "https://demo.opencart.com/";
    private final Long IMPLICITLY_WAIT_SECONDS = 10L;
    private final Long ONE_SECOND_DELAY = 1000L;
    private final String TIME_TEMPLATE = "yyyy-MM-dd_HH-mm-ss-S";
    private WebDriver driver;

    @Before
    public void beforeClass() {
        //Set webdriver
        System.setProperty("webdriver.chrome.driver", "./lib/chromedriver.exe");
    }

    @After
    public void afterClass() {
        if (driver != null) {
            driver.quit(); // close()
        }
    }

    @Test
    public void checkMac() throws InterruptedException {
        //New webdriver
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICITLY_WAIT_SECONDS)); // 0 by default
        driver.manage().window().maximize();
        //Choose Site
        driver.get(BASE_URL);
        // Choose Currency
        WebElement currency = driver.findElement(By.id("form-currency"));
        System.out.println("*** 1. Currency.isDisplayed() = " + currency.isDisplayed());
        currency.click();
        // Choose Euro
        currency = driver.findElement(By.cssSelector("a[href='EUR']"));
        System.out.println("*** 2. Euro.isDisplayed() ");
        currency.click();
        // Choose Category
        WebElement desktops = driver.findElement(By.xpath("//*[@id='narbar-menu']//a[text()='Desktops']"));
        System.out.println("*** 3. Category \"Desktop\".isDisplayed() = " + desktops.isDisplayed());
        desktops.click();
        WebElement mac = desktops.findElement(By.xpath("//a[@class='nav-link' and contains(text(),'Mac')]"));
        System.out.println("*** 4. Category \"Mac\".isDisplayed() = " + mac.isDisplayed());
        mac.click();
        WebElement iMac = driver.findElement(By.xpath("//a[text()='iMac']/../following-sibling::div[@class='price']/span[@class='price-new']"));
        // Scroll by Action class
        //Actions action = new Actions(driver);
        //action.moveToElement(iMac).perform();
        // Check price
        System.out.println("iMac price.getText() = " + iMac.getText());
        Assert.assertTrue(iMac.getText().contains("111.55€"));
        driver.quit();
        System.out.println("\ndone");
    }
}
