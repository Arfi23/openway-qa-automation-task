package com.openway.qa;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class TestAddToCart {
    @Test
    public void testAddToCart() {

        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
            // 1. Open website
            driver.get("https://www.periplus.com/");
            driver.manage().window().maximize();

            // 2. Click Sign In
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(text(),'Sign In')]")
            )).click();


            // 3. Input login
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")))
                    .sendKeys("qatester2310@gmail.com");

            driver.findElement(By.name("password"))
                    .sendKeys("sandi1023");

            Thread.sleep(8000);

            // 4. Click login
            driver.findElement(By.id("button-login")).click();
            

            // Tunggu login selesai (misalnya tunggu search bar muncul)
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("filter_name")));

            // 5. Search product
            driver.findElement(By.name("filter_name")).sendKeys("Harry Potter");
            driver.findElement(By.xpath("//button[@type='submit']")).click();

            // 6. Click first product
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("(//div[contains(@class,'single-product')]//a)[1]")
            )).click();

            // 7. Click Add to Cart
            WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.className("addtocart")
            ));
            addToCartBtn.click();

            // 8. Verify success notification
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'added')]")
            ));

            // 9. Go to cart page
            driver.get("https://www.periplus.com/index.php?route=checkout/cart");

            // 10. Verify product exists in cart
            boolean productExists = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'Harry Potter')]")
            )).isDisplayed();

            Assert.assertTrue(productExists, "Product was not added to cart!");

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Test failed due to exception");
        } finally {
            driver.quit();
        }
    }
}
