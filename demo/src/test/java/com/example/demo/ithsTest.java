package com.example.demo;

import io.cucumber.java.After;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.*;

public class ithsTest {

    WebDriver driver = new ChromeDriver();

    @Given("the user is on the IT-Högskolan homepage")
    public void the_user_is_on_the_it_högskolan_homepage() {
        driver.get("https://www.iths.se/");
        throw new io.cucumber.java.PendingException();
    }
    @When("the user clicks on the {string} link")
    public void the_user_clicks_on_the_link(String string) {
        WebElement button = driver.findElement(By.xpath("//a[@href='https://apply.yh-antagning.se/to/iths/ht2024']/button"));
        button.click();
        throw new io.cucumber.java.PendingException();
    }
    @When("the user clicks on the specific button")
    public void the_user_clicks_on_the_specific_button() {
        WebElement button = driver.findElement(By.cssSelector("button.your-button-class"));
        button.click();
        throw new io.cucumber.java.PendingException();
    }
    @Then("the user should be redirected to the application form page")
    public void the_user_should_be_redirected_to_the_application_form_page() {
        String expectedUrl = "https://apply.yh-antagning.se/Application/Initiate?educationProviderId=baa22a70-f4d1-4e5a-ab9d-90050cf58489&admissionRoundId=3da8eb6b-3ff9-47e5-b32a-d3d02528747d";
        assertTrue(driver.getCurrentUrl().contains(expectedUrl));
        throw new io.cucumber.java.PendingException();
    }

    @After
    public void closeBrowser() {
        driver.quit();
    }
}
