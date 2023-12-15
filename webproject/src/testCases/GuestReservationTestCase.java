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
public class GuestReservationTestCase {
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
  public void testGuestReservationTestCase() throws Exception {
    driver.get("http://localhost:8080/webproject/landingPage.html");
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Reserve Your Seats'])[2]/following::a[1]")).click();
    driver.get("http://localhost:8080/webproject/simpleInsertHB.html");
    driver.findElement(By.id("name")).click();
    driver.findElement(By.id("name")).clear();
    driver.findElement(By.id("name")).sendKeys("Benjamin");
    driver.findElement(By.id("numberOfGuests")).click();
    driver.findElement(By.id("numberOfGuests")).clear();
    driver.findElement(By.id("numberOfGuests")).sendKeys("4");
    driver.findElement(By.id("date")).click();
    driver.findElement(By.id("date")).click();
    driver.findElement(By.id("date")).clear();
    driver.findElement(By.id("date")).sendKeys("2024-01-25");
    driver.findElement(By.id("time")).click();
    new Select(driver.findElement(By.id("time"))).selectByVisibleText("10:30 AM");
    driver.findElement(By.xpath("//input[@value='RESERVE']")).click();
    driver.get("http://localhost:8080/webproject/landingPage.html");
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
