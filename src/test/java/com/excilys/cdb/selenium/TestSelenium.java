package com.excilys.cdb.selenium;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestSelenium {
    private WebDriver driver;
    private String baseUrl;
    private final StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        this.driver = new FirefoxDriver();
        this.baseUrl = "http://localhost:8080/";
        this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void test() throws Exception {
        this.driver.get(this.baseUrl + "/cdb/dashboard");
        this.driver.findElement(By.cssSelector("a.navbar-brand")).click();
        this.driver.findElement(By.linkText("2")).click();
        this.driver.findElement(By.xpath("//li[11]/a/span")).click();
        this.driver.findElement(By.xpath("//li[12]/a/span")).click();
        this.driver.findElement(By.linkText("58")).click();

    }

    @After
    public void tearDown() throws Exception {
        this.driver.quit();
        final String verificationErrorString = this.verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            Assert.fail(verificationErrorString);
        }
    }
}
