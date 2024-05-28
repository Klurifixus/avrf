package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class buttontest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeEach
    void setup() {
        //Chromedriver
        ChromeOptions options = new ChromeOptions();
        options.addArguments("incognito");
        options.addArguments("--headless=new");
        driver = new ChromeDriver(options);
        driver.get("https://www.avrf.se/");
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
    public void testSearchButton() {
        WebElement searchButton = driver.findElement(By.xpath("//button[contains(.,'Sök')]"));
        Assertions.assertTrue(searchButton.isDisplayed(), "Search button should be visible");
        Assertions.assertTrue(searchButton.getAttribute("class").contains("v-btn--outlined"), "Search button should have the outlined style");
    }

    @Test
    public void testContactButton() {
        WebElement contactButton = driver.findElement(By.xpath("//button[contains(@class, 'ui-btn') and contains(., 'Kontakt')]"));

        Assertions.assertTrue(contactButton.isDisplayed(), "Contact button should be visible");

        Assertions.assertTrue(contactButton.getText().contains("Kontakt"), "Button text should contain 'Kontakt'");

        Assertions.assertTrue(contactButton.getAttribute("class").contains("v-btn--has-bg"), "Button should have background style");

        WebElement icon = contactButton.findElement(By.className("mdi-arrow-right"));
        Assertions.assertTrue(icon.isDisplayed(), "Right arrow icon should be visible in the button");
    }



    @Test
    public void testMenuButton() {
        WebElement menuButton = driver.findElement(By.xpath("//button[contains(.,'Meny')]"));
        Assertions.assertTrue(menuButton.isDisplayed(), "Menu button should be visible");
        Assertions.assertTrue(menuButton.getAttribute("class").contains("v-btn--has-bg"), "Menu button should have a background");
        WebElement icon = menuButton.findElement(By.className("mdi-menu"));
        Assertions.assertTrue(icon.isDisplayed(), "Menu icon should be present within the button");
    }
}
