package com.excilys.webapp.selenium;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import static org.junit.Assert.assertTrue;

public class TestSelenium {
    private WebDriver driver;
    private String baseUrl;
    private final StringBuffer verificationErrors = new StringBuffer();
    private boolean acceptNextAlert = true;

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = "http://localhost:8080/cdb";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testCreateFindDelete() throws Exception {

        // creates a computer with a name of "ComputerTest"

        driver.get(baseUrl + "/login?logout");
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys("admin");
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("admin");
        driver.findElement(By.name("submit")).click();
        driver.findElement(By.id("addComputer")).click();
        driver.findElement(By.id("name")).clear();
        driver.findElement(By.id("name")).sendKeys("testComputer");
        new Select(driver.findElement(By.id("companyId"))).selectByVisibleText("Thinking Machines");
        driver.findElement(By.id("buttonForm")).click();

        // check if it has been added

        driver.findElement(By.id("searchbox")).clear();
        driver.findElement(By.id("searchbox")).sendKeys("testComputer");
        driver.findElement(By.id("searchsubmit")).click();
        final String nbFound = this.driver.findElement(By.id("nbComputers")).getText();

        Assert.assertEquals(nbFound, "1");

        //Update computer

        driver.findElement(By.id("testComputer_name")).click();
        driver.findElement(By.id("name")).clear();
        driver.findElement(By.id("name")).sendKeys("testComputer2");
        driver.findElement(By.id("buttonForm")).click();
        driver.findElement(By.id("searchbox")).clear();
        driver.findElement(By.id("searchbox")).sendKeys("testComputer2");
        driver.findElement(By.id("searchsubmit")).click();

        //Check update

        final String nbFound2 = this.driver.findElement(By.id("nbComputers")).getText();

        Assert.assertEquals(nbFound2, "1");


        // remove it

        driver.findElement(By.id("editComputer")).click();
        driver.findElement(By.id("testComputer2_id")).click();
        driver.findElement(By.xpath("//a[@id='deleteSelected']/i")).click();
        assertTrue(closeAlertAndGetItsText().matches("^Etes vous sur de vouloir supprimer la sélection [\\s\\S]$"));
        driver.findElement(By.id("searchbox")).clear();
        driver.findElement(By.id("searchbox")).sendKeys("testComputer2");
        driver.findElement(By.id("searchsubmit")).click();

        //Check deletion

        final String nbFound3 = this.driver.findElement(By.id("nbComputers")).getText();

        Assert.assertEquals(nbFound3, "0");
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
