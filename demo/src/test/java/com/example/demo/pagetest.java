package com.example.demo;

import org.junit.jupiter.api.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.openqa.selenium.By;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class pagetest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeEach
    void setup() {
        //Chromedriver
        ChromeOptions options = new ChromeOptions();
        options.addArguments("incognito");
        options.addArguments("--headless=new");
        driver = new ChromeDriver(options);
        driver.get("https://avrf.se");
        driver.manage().window().maximize();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement cookieAcceptButton = driver.findElement(By.cssSelector(".v-btn.v-btn--outlined.theme--light.v-size--default.white--text"));
        cookieAcceptButton.click();
    }

    @Test
    void checkTitle() {
        assertEquals("AVRF - AVRF", driver.getTitle());
    }

    @Test
    void homePageTitleTest() {
        driver.get("https://avrf.se/");
        String expectedTitle = "AVRF - AVRF";
        String actualTitle = driver.getTitle();
        Assertions.assertEquals(expectedTitle, actualTitle, "The title should be matched and the same!");
    }

    @Test
    void mobileMenu() {
        Dimension newSize = new Dimension(357, 667);
        driver.manage().window().setSize(newSize);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        boolean searchMenuVisibility = driver.findElement(By.className("SearchMenu")).isDisplayed();
        assertFalse(searchMenuVisibility);

    }


    @AfterEach
    public void tearDown() {
        driver.quit();
    }

}




