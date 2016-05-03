package com.excilys.cdb.selenium;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestSelenium {
    private WebDriver driver;
    private String baseUrl;
    private final StringBuffer verificationErrors = new StringBuffer();
    private boolean acceptNextAlert = true;

    @Before
    public void setUp() throws Exception {
        this.driver = new FirefoxDriver();
        this.baseUrl = "http://localhost:8080/";
        this.driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
    }

    @Test
    public void testPagination() throws Exception {
        this.driver.get(this.baseUrl + "/cdb/dashboard");
        try {
            System.out.println(this.driver.findElement(By.id("prevjhjhjhjious")).getText());
            Assert.assertTrue(false);
        } catch (final Exception e) {
            Assert.assertTrue(true);
        }
        this.driver.findElement(By.id("previous")).click();
    }

    @Test
    public void testCreateFindDelete() throws Exception {

        // creates a computer with a name of "ComputerTest"

        this.driver.get(this.baseUrl + "/cdb/dashboard");
        this.driver.findElement(By.id("addComputer")).click();
        this.driver.findElement(By.id("computerName")).clear();
        this.driver.findElement(By.id("computerName")).sendKeys("ComputerTest");
        this.driver.findElement(By.id("buttonForm")).click();

        // check if it has been added

        this.driver.get(this.baseUrl + "/cdb/dashboard");
        this.driver.findElement(By.id("searchbox")).sendKeys("ComputerTest");
        this.driver.findElement(By.id("searchsubmit")).click();
        final String nbFound = this.driver.findElement(By.id("nbComputers")).getText();
        System.out.println(nbFound);
        Assert.assertTrue(nbFound.equals("1"));

        // remove it

        this.driver.findElement(By.id("editComputer")).click();
        this.driver.findElement(By.id("selectall")).click();
        this.driver.findElement(By.id("deleteSelected")).click();
        assertTrue(this.closeAlertAndGetItsText()
                .matches("^Are you sure you want to delete the selected computers[\\s\\S]$"));

        // check if it has been removed

        this.driver.get(this.baseUrl + "/cdb/dashboard");
        this.driver.findElement(By.id("searchbox")).sendKeys("ComputerTest");
        this.driver.findElement(By.id("searchsubmit")).click();
        final String nbFound2 = this.driver.findElement(By.id("nbComputers")).getText();
        System.out.println(nbFound2);
        Assert.assertTrue(nbFound2.equals("0"));
    }

    @After
    public void tearDown() throws Exception {
        this.driver.quit();
        final String verificationErrorString = this.verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            Assert.fail(verificationErrorString);
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            final Alert alert = this.driver.switchTo().alert();
            final String alertText = alert.getText();
            if (this.acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            this.acceptNextAlert = true;
        }
    }
}
