package ro.electercastel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class AddtoCartTest {
    WebDriver driver;
    String url = "https://electriccastle.ro/";

    @BeforeTest
    public void setUp(){
        //open page
        driver = new ChromeDriver();
        driver.get(url);
        driver.manage().window().maximize();
    }

    @Test
    public void addToCartTest(){
        // 1. Click allow cookies
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("CybotCookiebotDialogBodyButtonAccept")));

        WebElement allowCookies = driver.findElement(By.id("CybotCookiebotDialogBodyButtonAccept"));
        allowCookies.click();

        //ClickbuyTickets
        //Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a.std-button.std-button--light.cta")));

        WebElement buyTickets = driver.findElement(By.cssSelector("a.std-button.std-button--light.cta"));
        buyTickets.click();

        //3. Add to cart
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("section#GENERAL > div > article:nth-of-type(1)  .tag_add_to_cart.tickets__item__select")));
        WebElement addToCart = driver.findElement(By.cssSelector("section#GENERAL > div > article:nth-of-type(1)  .tag_add_to_cart.tickets__item__select"));
        addToCart.click();

        //4. Verificare
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".modal-title")));
        WebElement addedToCart = driver.findElement(By.cssSelector(".modal-title"));
        Assert.assertTrue(addedToCart.isDisplayed());
        System.out.println(addToCart.getText());
        Assert.assertTrue(addedToCart.getText().toUpperCase().contains("YOU ADDED TO YOUR CART"));


    }

    @AfterTest
    public void tearDown(){
        driver.close();
    }

}
