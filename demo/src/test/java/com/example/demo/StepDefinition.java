package com.example.demo;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.junit.jupiter.api.Assertions;
import io.cucumber.datatable.DataTable;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;

public class StepDefinition {
    public static WebDriver driver;
    public static WebDriverWait wait;
    public static JavascriptExecutor js;
    public static Logger logger = Logger.getLogger(StepDefinition.class.getName());

    @Before
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("incognito", "--headless");
        driver = new ChromeDriver(options);
        driver.manage().window().fullscreen();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        js = (JavascriptExecutor) driver;
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("att jag är på hemsidan {string}")
    public void att_jag_ar_pa_hemsidan(String url) {
        driver.get(url);
    }

    @When("jag accepterar cookies")
    public void jag_accepterar_cookies() {
        clickElement(By.id("CybotCookiebotDialogBodyLevelButtonLevelOptinAllowAll"));
    }

    @Then("jag ska se IT-Högskolan logotypen på hemsidan")
    public void jag_ska_se_IT_Hogskolan_logotypen_pa_hemsidan() {
        WebElement logo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class='brand']/img[contains(@src, 'logo.svg')]")));
        Assertions.assertTrue(logo.isDisplayed(), "Logo is not visible on the homepage");
    }

    @Then("skall titeln vara {string}")
    public void skall_titeln_vara(String expectedTitle) {
        String actualTitle = driver.getTitle();
        Assertions.assertEquals(expectedTitle, actualTitle);
    }

    @Then("ska favicon vara närvarande med den förväntade länken")
    public void ska_favicon_vara_närvarande_med_den_förväntade_länken() {
        WebElement favicon = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//link[@rel='icon']")));
        String actualHref = favicon.getAttribute("href");
        String expectedHref = "https://www.iths.se/wp-content/themes/stella/favicon-32x32.png";
        Assertions.assertEquals(expectedHref, actualHref, "Favicon-länken är inte korrekt");
    }

    @When("upplösningen är {int}x{int}")
    public void upplosningen_ar(int width, int height) {
        driver.manage().window().setSize(new Dimension(width, height));
    }

    @Then("sidan ska korrekt anpassa sig till upplösningen {int}x{int}")
    public void sidan_ska_korrekt_anpassa_sig_till_upplosningen(int width, int height) {
        Dimension dimension = driver.manage().window().getSize();
        Assertions.assertEquals(width, dimension.getWidth());
        Assertions.assertEquals(height, dimension.getHeight());
    }

    @When("jag scrollar till footer-sektionen")
    public void jag_scrollar_till_footer_sektionen() {
        scrollToElement(By.tagName("footer"));
    }

    @Then("borde jag se {string} sektionen i footern")
    public void borde_jag_se_sektionen_i_footern(String stad) {
        logger.info("Looking for section: " + stad);
        WebElement section = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[contains(text(), '" + stad + "')]")));
        Assertions.assertTrue(section.isDisplayed(), "Sektionen för " + stad + " är inte synlig");
        logger.info("Section for " + stad + " is visible.");
    }

    @Then("emailadressen borde vara {string} i {string} sektionen")
    public void emailadressen_borde_vara_i_sektionen(String email, String stad) {
        String xpath = "//h5[contains(text(), '" + stad + "')]/following-sibling::div//a[contains(@href, 'mailto')]";
        WebElement emailElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        Assertions.assertEquals(email, emailElement.getText(), "Emailadressen stämmer inte i " + stad + " sektionen");
    }

    @Then("telefonnumret borde vara {string} i {string} sektionen")
    public void telefonnumret_borde_vara_i_sektionen(String telefon, String stad) {
        String xpath = "//h5[contains(text(), '" + stad + "')]/following-sibling::div//a[contains(@href, 'callto')]";
        WebElement phoneElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        Assertions.assertTrue(phoneElement.getText().contains(telefon), "Telefonnumret stämmer inte i " + stad + " sektionen");
    }

    @Then("adressen borde vara {string} i {string} sektionen")
    public void adressen_borde_vara_i_sektionen(String address, String stad) {
        String xpath = "//h5[contains(text(), '" + stad + "')]/following-sibling::div//a[contains(@href, 'https')]";
        WebElement addressElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        Assertions.assertTrue(addressElement.getText().contains(address), "Adressen stämmer inte i " + stad + " sektionen");
    }

    @When("i hero sektionen hittar jag knappen {string}")
    public void i_hero_sektionen_hittar_jag_knappen(String buttonText) {
        logger.info("Looking for button with text: " + buttonText);
        WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(), '" + buttonText + "')]")));
        Assertions.assertTrue(button.isDisplayed(), "Knappen med text '" + buttonText + "' är inte synlig");
        logger.info("Button with text '" + buttonText + "' is visible.");
    }

    @When("jag dubbelklickar på {string}")
    public void jag_dubbelklickar_pa(String buttonText) {
        logger.info("Attempting to double-click the button with text: " + buttonText);
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(), '" + buttonText + "')]")));
        Actions actions = new Actions(driver);
        actions.doubleClick(button).perform();
        logger.info("Double-clicked the button with text: " + buttonText);

        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("hamnar jag på sidan {string}")
    public void hamnar_pa_sidan(String expectedUrl) {
        logger.info("Waiting for URL to contain: " + expectedUrl);
        WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(30));

        try {
            boolean urlContains = longWait.until(ExpectedConditions.urlContains(expectedUrl));
            Assertions.assertTrue(urlContains, "Webbläsaren navigerade inte till den förväntade URLen.");
            logger.info("Navigation to the expected URL is successful: " + expectedUrl);
        } catch (TimeoutException e) {
            logger.warning("Timed out waiting for URL to contain: " + expectedUrl);
            throw new AssertionError("Timed out waiting for the expected URL.", e);
        }
    }

    @When("skrollar ner tills jag ser knappen {string}")
    public void skrollar_ner_tills_jag_ser_knappen(String buttonText) {
        logger.info("Scrolling down to button with text: " + buttonText);
        scrollToElement(By.xpath("//a[contains(text(), '" + buttonText + "')]"));
        logger.info("Button with text '" + buttonText + "' är synlig.");
    }

    @When("jag klickar på knappen {string}")
    public void jag_klickar_pa_knappen(String buttonText) {
        logger.info("Attempting to click the button with text: " + buttonText);
        closePopupIfPresent();
        String originalWindow = driver.getWindowHandle();
        boolean clicked = false;
        int attempts = 0;
        while (!clicked && attempts < 3) {
            try {
                clickElement(By.xpath("//a[contains(text(), '" + buttonText + "')]"));
                clicked = true;
                logger.info("Clicked the button with text: " + buttonText);
            } catch (ElementClickInterceptedException e) {
                attempts++;
                logger.warning("Element click intercepted, attempt " + attempts + " to close popup and click again.");
                closePopupIfPresent();
            }
        }
        if (!clicked) {
            throw new RuntimeException("Failed to click the element after multiple attempts");
        }

        Set<String> windowHandles = driver.getWindowHandles();
        for (String windowHandle : windowHandles) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
    }

    @Then("hamnar jag på sidan som innehåller {string} med texten {string}")
    public void hamnar_pa_sidan_som_innehaller_med_texten(String partialUrl, String text) {
        try {
            wait.until(ExpectedConditions.urlContains(partialUrl));
            logger.info("The URL contains the expected partial text: " + partialUrl);

            String currentUrl = driver.getCurrentUrl();
            if (currentUrl.contains(partialUrl)) {
                logger.info("Current URL contains the expected partial URL.");
            } else {
                throw new AssertionError("Current URL does not contain the expected partial URL.");
            }

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), '" + text + "')]")));
            logger.info("The page contains the expected text: " + text);
        } catch (TimeoutException e) {
            logger.severe("Timed out waiting for the expected partial URL or text.");
            throw new AssertionError("Timed out waiting for the expected partial URL or text.", e);
        }
    }

    @Then("jag svarar på frågorna i quizet")
    public void jag_svarar_pa_fragorna_i_quizet(DataTable dataTable) {
        List<Map<String, String>> questions = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> question : questions) {
            String questionText = question.get("fråga");
            String answerText = question.get("svar");

            logger.info("Svarar på fråga: " + questionText + " med svar: " + answerText);

            boolean answered = false;
            int attempts = 0;

            while (!answered && attempts < 3) {
                try {
                    // Logga aktuell URL
                    String currentUrl = driver.getCurrentUrl();
                    logger.info("Aktuell URL: " + currentUrl);

                    // Bekräfta att frågan finns på sidan
                    WebElement questionElement = driver.findElement(By.xpath("//div[contains(@class, 'qp_qi') and text()='" + questionText + "']"));
                    logger.info("Hittade frågan: " + questionElement.getText());

                    // Hitta alla svarsalternativ för frågan
                    List<WebElement> answerOptions = driver.findElements(By.xpath("//div[contains(@class, 'qp_qi') and text()='" + questionText + "']/following-sibling::div[contains(@class, 'qp_ao')]//span[@class='qp_t']"));
                    logger.info("Antal hittade svarsalternativ: " + answerOptions.size());

                    // Kontrollera att det finns exakt 4 svarsalternativ
                    Assertions.assertEquals(4, answerOptions.size(), "Antalet svarsalternativ är inte 4 för frågan: " + questionText);

                    boolean optionFound = false;
                    for (WebElement option : answerOptions) {
                        logger.info("Kontrollerar svarsalternativ: " + option.getText());
                        if (option.getText().equals(answerText)) {
                            optionFound = true;
                            WebElement inputElement = option.findElement(By.xpath("./preceding-sibling::input"));
                            logger.info("Hittade motsvarande input-element");

                            // Använd JavaScript för att klicka på elementet
                            js.executeScript("arguments[0].click();", inputElement);
                            logger.info("Klickade på input-elementet med JavaScript");

                            // Bekräfta att svarsalternativet är valt
                            Boolean isSelected = (Boolean) js.executeScript("return arguments[0].checked;", inputElement);
                            Assertions.assertTrue(isSelected, "Svarsalternativet är inte valt korrekt");

                            // Vänta på att nästa fråga ska laddas
                            try {
                                Thread.sleep(2000); // Väntetid för att låta nästa fråga ladda
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                                logger.severe("Tråden avbröts under väntetid: " + e.getMessage());
                                throw new RuntimeException(e);
                            }

                            // Bekräfta att vi har navigerat till nästa fråga genom att kontrollera att frågetexten har ändrats
                            WebElement nextQuestionElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'qp_qi')]")));
                            String nextQuestionText = nextQuestionElement.getText();
                            logger.info("Nästa fråga laddad: " + nextQuestionText);
                            Assertions.assertNotEquals(questionText, nextQuestionText, "Nästa fråga laddades inte korrekt");

                            answered = true;
                            logger.info("Fråga besvarad: " + questionText);
                            break;
                        }
                    }

                    if (!optionFound) {
                        throw new NoSuchElementException("Kunde inte hitta svarsalternativet med text: " + answerText);
                    }
                } catch (TimeoutException e) {
                    attempts++;
                    logger.warning("TimeoutException vid försök " + attempts + " för frågan: " + questionText + ". Meddelande: " + e.getMessage());
                    closePopupIfPresent();
                } catch (NoSuchElementException e) {
                    attempts++;
                    logger.warning("NoSuchElementException vid försök " + attempts + " för frågan: " + questionText + ". Meddelande: " + e.getMessage());
                    closePopupIfPresent();
                }
            }

            if (!answered) {
                throw new RuntimeException("Misslyckades med att svara på frågan efter flera försök: " + questionText);
            }
        }
    }

    @And("jag skriver in en mailaddress {string}")
    public void jag_skriver_in_en_mailaddress(String email) {

        WebElement emailInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@type='email']")));
        emailInput.sendKeys(email);
    }

    @And("jag klickar på formulärknappen {string}")
    public void jag_klickar_pa_formularknappen(String buttonText) {

        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='" + buttonText + "']")));
        button.click();
    }

    @Then("jag ser resultaten av quizet")
    public void jag_ser_resultaten_av_quizet() {

        WebElement resultElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".qp_b[results='1']")));
        Assertions.assertTrue(resultElement.isDisplayed(), "Resultaten visas inte.");
    }

    @Then("meta description ska vara närvarande")
    public void meta_description_ska_vara_narvarande() {
        WebElement metaDesc = driver.findElement(By.xpath("//meta[@name='description']"));
        Assertions.assertNotNull(metaDesc, "Meta description är inte närvarande");
    }

    @Then("meta robots tag ska vara närvarande")
    public void meta_robots_tag_ska_vara_narvarande() {
        WebElement metaRobots = driver.findElement(By.xpath("//meta[@name='robots']"));
        Assertions.assertNotNull(metaRobots, "Meta robots tag är inte närvarande");
    }

    @Then("canonical link ska vara närvarande")
    public void canonical_link_ska_vara_narvarande() {
        WebElement canonicalLink = driver.findElement(By.xpath("//link[@rel='canonical']"));
        Assertions.assertNotNull(canonicalLink, "Canonical link är inte närvarande");
    }

    @When("jag navigerar till sidan {string}")
    public void jag_navigerar_till_sidan(String url) {
        driver.get(url);
    }

    @Then("ska jag se en lista med kurser")
    public void ska_jag_se_en_lista_med_kurser() {
        List<WebElement> courses = driver.findElements(By.cssSelector(".course-list-item"));
        Assertions.assertTrue(courses.size() > 0, "Kurslistan är tom");
    }

    @When("jag skrollar ner till videolänken")
    public void jag_skrollar_ner_till_videolanken() {
        WebElement videoLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".page-item__hero-content .video-link")));
        js.executeScript("arguments[0].scrollIntoView(true);", videoLink);
    }

    @And("jag klickar på play-knappen")
    public void jag_klickar_pa_play_knappen() {
        WebElement playButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".page-item__hero-content .video-link .fa-play-circle")));
        playButton.click();
    }

    @Then("ska videon spelas upp")
    public void ska_videon_spelas_upp() {
        WebElement videoContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".html5-video-container .video-stream")));
        boolean isVideoPlaying = (Boolean) js.executeScript("return arguments[0].paused === false;", videoContainer);
        Assertions.assertTrue(isVideoPlaying, "Videon spelas inte upp korrekt.");
    }

    @When("jag skrollar ner till {string}")
    public void jag_skrollar_ner_till(String email) {
        WebElement emailLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@href, 'mailto:" + email + "')]")));
        js.executeScript("arguments[0].scrollIntoView(true);", emailLink);
    }

    @Then("ska länken öppna mailto-klienten")
    public void ska_lanken_oppna_mailto_klienten() {
        WebElement emailLink = driver.findElement(By.xpath("//a[contains(@href, 'mailto:info@iths.se')]"));
        String hrefValue = emailLink.getAttribute("href");
        Assertions.assertTrue(hrefValue.startsWith("mailto:"), "Länken öppnar inte mailto-klienten korrekt.");
    }

    @When("jag klickar på menyalternativet {string}")
    public void jag_klickar_pa_menyalternativet(String menuOption) {
        closePopupIfPresent();
        clickElement(By.linkText(menuOption));
    }

    @Then("ska jag navigeras till sidan {string}")
    public void ska_jag_navigeras_till_sidan(String expectedUrl) {
        wait.until(ExpectedConditions.urlToBe(expectedUrl));
        String actualUrl = driver.getCurrentUrl();
        Assertions.assertEquals(expectedUrl, actualUrl, "Webbläsaren navigerade inte till den förväntade URLen.");
    }

    @Then("titeln ska vara {string}")
    public void titeln_ska_vara(String expectedTitle) {
        String actualTitle = driver.getTitle();
        Assertions.assertEquals(expectedTitle, actualTitle, "Titeln stämmer inte överens med förväntad titel.");
    }

    // Helper Methods

    public void clickElement(By by) {
        for (int i = 0; i < 3; i++) {
            try {
                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(by));
                js.executeScript("arguments[0].click();", element);
                return;
            } catch (ElementClickInterceptedException e) {
                logger.warning("Element click intercepted, försöker stänga popup och klicka igen.");
                closePopupIfPresent();
            }
        }
        throw new RuntimeException("Failed to click the element after multiple attempts");
    }

    public void scrollToElement(By by) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void closePopupIfPresent() {
        try {
            WebElement cookieBotButton = driver.findElement(By.cssSelector(".CybotCookiebotDialogBodyButton"));
            if (cookieBotButton.isDisplayed()) {
                cookieBotButton.click();
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".CybotCookiebotDialogBodyButton")));
                logger.info("Stängde CookieBot popup-fönstret");
            }
        } catch (NoSuchElementException ignored) {
            logger.info("Ingen CookieBot popup hittades att stänga");
        }

        try {
            WebElement closeButton = driver.findElement(By.cssSelector(".pum-container .pum-close"));
            if (closeButton.isDisplayed()) {
                closeButton.click();
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".pum-container .pum-close")));
                logger.info("Stängde Pum-container popup-fönstret");
            }
        } catch (NoSuchElementException ignored) {
            logger.info("Ingen Pum-container popup hittades att stänga");
        }
    }

}