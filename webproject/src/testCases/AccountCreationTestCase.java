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
public class AccountCreationTestCase {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  JavascriptExecutor js;
  @Before
  public void setUp() throws Exception {
	System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");

    driver = new ChromeDriver();
    baseUrl = "https://www.google.com/";
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
    js = (JavascriptExecutor) driver;
  }

  @Test
  public void testAccountCreationTestCase() throws Exception {
    driver.get("http://localhost:8080/webproject/landingPage.html");
    driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Login'])[2]/following::a[1]")).click();
    driver.get("http://localhost:8080/webproject/createAccount.html");
    driver.findElement(By.id("newUsername")).click();
    driver.findElement(By.id("newUsername")).clear();
    driver.findElement(By.id("newUsername")).sendKeys("bschultz");
    driver.findElement(By.id("newEmail")).click();
    driver.findElement(By.id("newEmail")).clear();
    driver.findElement(By.id("newEmail")).sendKeys("benschultz@gmail.com");
    driver.findElement(By.id("newPassword")).click();
    driver.findElement(By.id("newPassword")).clear();
    driver.findElement(By.id("newPassword")).sendKeys("Incorrect.");
    driver.findElement(By.id("confirmPassword")).click();
    driver.findElement(By.id("confirmPassword")).clear();
    driver.findElement(By.id("confirmPassword")).sendKeys("Incorrect.");
    driver.findElement(By.xpath("//input[@value='Create Account']")).click();
    driver.get("http://localhost:8080/webproject/dashboard.html");
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
