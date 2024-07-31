package com.crewshop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.URL;
import java.time.Duration;

public class ExamClass {
    WebDriver driver;
    String url = "https://www.crewshop.ro/";

    @BeforeTest
    public void setUp() {

        driver = new ChromeDriver();
        driver.get(url);
        driver.manage().window().maximize();
    }

    @Test(priority = 1)
    public void logInTest() {
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(@class, 'cky-btn-accept')]")));
        WebElement allowCookies = driver.findElement(By.xpath("//button[contains(@class, 'cky-btn-accept')]"));
        allowCookies.click();

        WebElement usernameInput = driver.findElement(By.id("UserEmail"));
        usernameInput.sendKeys("ralucaasandor@gmail.com");
        WebElement passwordInput = driver.findElement(By.id("UserPassword"));
        passwordInput.sendKeys("ParolaPentruExamen1");
        WebElement loginButton = driver.findElement(By.xpath("//form[@id='UserLoginActionForm']//input[@label='Login']"));

        loginButton.click();
        String expectedgUrl = "https://www.crewshop.ro/";
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(expectedgUrl, actualUrl);
    }


    @Test(priority = 2)
    public void addToCart() {
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        Actions actions = new Actions(driver);
        WebElement jucarii = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@id='js-centered-navigation-menu']/li[10]/a[@href='#']")));
        actions.moveToElement(jucarii).click().perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@id='js-centered-navigation-menu']/li[10]/ul[@class='submenu submenu_animation_show']//a[@href='/frontend/filters/?filter=57']")));

        WebElement papusi = driver.findElement(By.xpath("//ul[@id='js-centered-navigation-menu']/li[10]/ul[@class='submenu submenu_animation_show']//a[@href='/frontend/filters/?filter=57']"));
        papusi.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='lazyLoad']/div/div[2]//a[@href='/products/view/124705']//img[@alt='Minnie']")));
        WebElement dinseyPapusi = driver.findElement(By.xpath("//div[@id='lazyLoad']/div/div[2]//a[@href='/products/view/124705']//img[@alt='Minnie']"));
        dinseyPapusi.click();
        WebElement adaugaInCos = driver.findElement(By.xpath("//div[@id='content']/div[@class='product-details']//div[@class='prod-add']/button"));
        adaugaInCos.click();
        WebElement cosCumparaturiICon = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//header[@id='fixedElementHeader']/div[@class='header-top']/div[@class='header-top-inside']//a[@title='Cosul meu de cumparaturi']"))); // Adjust this selector as needed
        cosCumparaturiICon.click();

        WebElement cosDeCumparaturiButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//header[@id='fixedElementHeader']//div[@class='modal-cart modal-slide-down show']/div[@class='btn-cartmd-b']/a[@href='/cart']")));
        Assert.assertTrue(cosDeCumparaturiButton.isDisplayed());
        cosDeCumparaturiButton.click();
        String expectedUrl = "https://www.crewshop.ro/cart";
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(expectedUrl, actualUrl);
    }


   @Test(priority = 3)
    public void removeFromCart() {
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement cosDeGunoiIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='cart_container']//tr//a[@class='cart_icontrash_ui icon-trash-wsl pointer remove-cart-product']")));
       cosDeGunoiIcon.click();
       WebElement cartTotal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cart_total")));
       try {
           Thread.sleep(3000);
       } catch (InterruptedException e) {
           throw new RuntimeException(e);
       }
       String expectedTotal = "0";
       String actualTotal = cartTotal.getText();
       Assert.assertEquals(actualTotal, expectedTotal);

    }
}

