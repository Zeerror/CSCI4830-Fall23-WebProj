package testCases;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import java.io.File;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class MakeReservationTestCase {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  JavascriptExecutor js;
  @Before
  public void setUp() throws Exception {
    System.setProperty("webdriver.chrome.driver", "");
    driver = new ChromeDriver();
    baseUrl = "https://www.google.com/";
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
    js = (JavascriptExecutor) driver;
  }

  @Test
  public void testMakeReservationTestCase() throws Exception {
    driver.get("http://localhost:8080/webproject/landingPage.html");
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Make a Reservation'])[2]/following::a[1]")).click();
    driver.get("http://localhost:8080/webproject/SimpleLogin.html");
    driver.findElement(By.id("username")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("user1");
    driver.findElement(By.id("password")).click();
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("user1_password");
    driver.findElement(By.xpath("//input[@value='LOGIN']")).click();
    driver.get("http://localhost:8080/webproject/dashboard.html");
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Reserve Your Seats'])[2]/following::a[1]")).click();
    driver.get("http://localhost:8080/webproject/makeAReservation.html");
    driver.findElement(By.id("name")).click();
    driver.findElement(By.id("name")).clear();
    driver.findElement(By.id("name")).sendKeys("Benjamin");
    driver.findElement(By.id("numberOfGuests")).click();
    driver.findElement(By.id("numberOfGuests")).clear();
    driver.findElement(By.id("numberOfGuests")).sendKeys("4");
    driver.findElement(By.id("date")).click();
    driver.findElement(By.id("date")).click();
    driver.findElement(By.id("date")).clear();
    driver.findElement(By.id("date")).sendKeys("2023-12-30");
    driver.findElement(By.id("time")).click();
    new Select(driver.findElement(By.id("time"))).selectByVisibleText("01:30 PM");
    driver.findElement(By.xpath("//input[@value='RESERVE']")).click();
    driver.get("http://localhost:8080/webproject/SimpleInsertHB");
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
