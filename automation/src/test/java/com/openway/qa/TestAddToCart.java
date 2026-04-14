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

            // 4. Click login
            WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.id("button-login")
            ));
            loginBtn.click();
            
            // Wait for the preloader process (explicit wait)
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.className("preloader")
            ));

            // Wait for login and preloader process to be done
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("filter_name")));

            // 5. Search product
            driver.findElement(By.name("filter_name")).sendKeys("Harry Potter");
            driver.findElement(By.xpath("//button[@type='submit']")).click();

            // Wait for the preloader process (explicit wait)(explicit wait)
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.className("preloader")
            ));

            // OLD 6. Click first product
            // wait.until(ExpectedConditions.elementToBeClickable(
            //         By.xpath("(//div[contains(@class,'single-product')]//a)[1]")
            // )).click();

            // 6. Click the first product
            WebElement firstProduct = driver.findElement(By.xpath("(//div[contains(@class,'single-product')]//a)[1]"));
            String productLink = firstProduct.getAttribute("href"); // used for getting the unique product ID (Item ID Extraction)

            firstProduct.click();

            // Wait for the preloader process (explicit wait)
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.className("preloader")
            ));

            // 7. Click Add to Cart
            WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(),'Add to Cart')]")
            ));
            addToCartBtn.click();

            // observe the success notification and wait for about 6s
            Thread.sleep(6000);

            // 8. Extract the unique ID of the added product (continuation of step 6)
            String productId = productLink.split("/p/")[1].split("/")[0];

            // 9. Go to cart page
            driver.get("https://www.periplus.com/index.php?route=checkout/cart");

            System.out.println("Current URL: " + driver.getCurrentUrl());

            Thread.sleep(6000); // to observe the cart page

            // 10. Verify product exists in cart by checking the ID extracted from the product URL
            boolean isProductInCart = driver.findElements(
                By.xpath("//a[contains(@href,'" + productId + "')]")
            ).size() > 0;

            Assert.assertTrue(isProductInCart, "Product was not added to cart!");

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Test failed due to exception");
        } finally {
            driver.quit();
        }
    }
}
