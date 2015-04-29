package ru.st.selenium;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.testng.*;
import org.testng.annotations.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class TestAddAndDeleteFilm extends ru.st.selenium.pages.TestBase {
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @BeforeMethod
  public void LogIn() {
	  driver.get(baseUrl + "/php4dvd/php4dvd/");
	  driver.findElement(By.id("username")).clear();
	  driver.findElement(By.id("username")).sendKeys("admin");
	  driver.findElement(By.name("password")).clear();
	  driver.findElement(By.name("password")).sendKeys("admin");
	  driver.findElement(By.name("submit")).click();
  }
  
  @Test
  public void testAddFilmCorrectThrowSearch() throws Exception {
	int quantity = driver.findElements(By.cssSelector("div.title")).size();
    driver.findElement(By.cssSelector("img[alt=\"Add movie\"]")).click();
    driver.findElement(By.id("imdbsearch")).clear();
    driver.findElement(By.id("imdbsearch")).sendKeys("Titanik");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    driver.findElement(By.linkText("Titanik")).click();
    driver.findElement(By.cssSelector("img[alt=\"Save\"]")).click();
    driver.findElement(By.linkText("Home")).click();
    assertEquals(driver.findElements(By.cssSelector("div.title")).size(),quantity+1);
  }
  
  @Test
  public void testAddFilmNoCorrectData() throws Exception {
	int quantity = driver.findElements(By.cssSelector("div.title")).size();
	driver.findElement(By.cssSelector("img[alt=\"Add movie\"]")).click();
	driver.findElement(By.name("name")).click();
	driver.findElement(By.name("name")).clear();
	driver.findElement(By.name("name")).sendKeys("Avatar");
	driver.findElement(By.id("submit")).click();
	driver.findElement(By.linkText("Home")).click();
	assertEquals(driver.findElements(By.cssSelector("div.title")).size(),quantity);

  }
  
  @Test
  public void testDeleteFilm() throws Exception {
	int quantity = driver.findElements(By.cssSelector("div.title")).size();
    driver.findElement(By.cssSelector("div.title")).click();
	driver.findElement(By.cssSelector("img[alt=\"Remove\"]")).click();
	assertTrue(closeAlertAndGetItsText().matches("^Are you sure you want to remove this[\\s\\S]$"));
	assertEquals(driver.findElements(By.cssSelector("div.title")).size(),quantity-1);
  }
  
  @AfterMethod
  public void LogOut() {
	  driver.findElement(By.linkText("Log out")).click();
	  assertTrue(closeAlertAndGetItsText().matches("^Are you sure you want to log out[\\s\\S]$"));
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
