package com.example.demo;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class seleniumtest {

    WebDriver driver;

    @BeforeEach
    void setUp() {
        //Chrome WebDriver
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        driver = new ChromeDriver(options);

        // Test Webpage
        driver.get("https://www.scb.se/");
    }

    @AfterEach
    void tearDown() {
        // Close the browser after each test
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void testTitle() {
        // Verify title
        String expectedTitle = "Statistikmyndigheten SCB";
        assertEquals(expectedTitle, driver.getTitle());
    }

    @Test
    void testLink() {
        // Find a link on the page and click it
        WebElement link = driver.findElement(By.linkText("Statistiknyheter"));
        link.click();

        // Verify that the expected URL is opened
        String expectedUrl = "https://www.scb.se/hitta-statistik/statistiknyheter/";
        assertEquals(expectedUrl, driver.getCurrentUrl());
    }
}
