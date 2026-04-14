package com.openway.qa;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestAddToCart {
    @Test
    public void testAddToCart() {

        WebDriver driver = new ChromeDriver();

        try {
            // 1. Open website
            driver.get("https://www.periplus.com/");
            driver.manage().window().maximize();

            Thread.sleep(3000);

            // 2. Login (manual locator, mungkin perlu adjust)
            driver.findElement(By.xpath("//a[contains(text(),'Sign In')]")).click();
            Thread.sleep(2000);

            driver.findElement(By.name("email")).sendKeys("qatester2310@gmail.com");
            driver.findElement(By.name("password")).sendKeys("sandi1023");

            Thread.sleep(10000);

            driver.findElement(By.id("button-login")).click();
            
            Thread.sleep(10000);

            // 3. Search product
            driver.findElement(By.name("filter_name")).sendKeys("Harry Potter");
            driver.findElement(By.xpath("//button[@type='submit']")).click();

            Thread.sleep(8000);

            // 4. Click first product
            driver.findElement(By.xpath("(//div[contains(@class,'single-product')]//a)[1]")).click();

            Thread.sleep(3000);

            // 5. Add to cart
            driver.findElement(By.xpath("//button[contains(text(),'Add to Cart')]")).click();

            Thread.sleep(3000);

            // 6. Verify (contoh sederhana: cek cart icon berubah / popup muncul)
            boolean isAdded = driver.getPageSource().contains("added");

            Assert.assertTrue(isAdded, "Product was not added to cart!");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
