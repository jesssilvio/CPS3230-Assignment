package org.example;

import org.example.Utils.API;
import org.example.Utils.InputType;
import org.example.Utils.Ebay;
import org.example.Utils.WebDriverStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;

class WebScraperTests {
    WebScraper webScraper;
    InputType inputType;
    Ebay ebay;
    WebDriverStatus webDriverStatus;
    API api;
    Request request;

    @BeforeEach
    public void setup(){
        webScraper = new WebScraper();
        request = new Request();
    }

    @AfterEach
    public void teardown(){
        webScraper = null;
    }

    @Test
    //Testing Setup Driver with no driver
    public void testSetupDriverWithNoDriver(){
        // Exercise
        boolean result = webScraper.setupDriver();

        // Verify
        Assertions.assertFalse(result);
    }

    @Test
    // Testing setup driver with driver not initialised
    public void testSetUpDriverWithoutInitialisation(){
        // Setup
        webDriverStatus = Mockito.mock(WebDriverStatus.class);
        Mockito.when(webDriverStatus.getDriverStatus()).thenReturn(WebDriverStatus.DRIVER_NOT_INITIALISED);
        webScraper.setWebDriverStatus(webDriverStatus);
        // Exercise
        boolean result =  webScraper.setupDriver();
        // Verify
        Assertions.assertFalse(result);
    }

    @Test
    // Testing setup driver with driver initialisation
    public void testSetUpDriverWithInitialisation(){
        // Setup
        webDriverStatus = Mockito.mock(WebDriverStatus.class);
        Mockito.when(webDriverStatus.getDriverStatus()).thenReturn(WebDriverStatus.DRIVER_INITIALISED);
        webScraper.setWebDriverStatus(webDriverStatus);
        // Exercise
        boolean result = webScraper.setupDriver();
        // Verify
        Assertions.assertTrue(result);
    }

    @Test
    // Testing web scraper with driver not initialised
    public void testWebScraperWithDriverNotInitialised() throws IOException, InterruptedException {
        // Setup
        webDriverStatus = Mockito.mock(WebDriverStatus.class);
        inputType = Mockito.mock(InputType.class);
        Mockito.when(webDriverStatus.getDriverStatus()).thenReturn(WebDriverStatus.DRIVER_NOT_INITIALISED);
        Mockito.when(inputType.userInput()).thenReturn(InputType.CAR);
        webScraper.setWebDriverStatus(webDriverStatus);
        webScraper.setInputType(inputType);
        // Exercise
        boolean result = webScraper.scrapeFiveAlertsAndUploadToWebsite();
        // Verify
        Assertions.assertFalse(result);
    }

    @Test
    // Testing web scraper with Input Type not initialised
    public void testWebScraperWithInputTypeNotInitialised() throws IOException, InterruptedException {
        // Exercise
        boolean result = webScraper.scrapeFiveAlertsAndUploadToWebsite();
        // Verify
        Assertions.assertFalse(result);
    }

    @Test
    // Testing web scraper with the ebay website not initialised
    public void testWebScraperWithEbayWebsiteNotInitialised() throws IOException, InterruptedException {
        // Setup
        inputType = Mockito.mock(InputType.class);
        webDriverStatus = Mockito.mock(WebDriverStatus.class);
        Mockito.when(webDriverStatus.getDriverStatus()).thenReturn(WebDriverStatus.DRIVER_INITIALISED);
        Mockito.when(inputType.userInput()).thenReturn(InputType.ELECTRONICS);
        webScraper.setWebDriverStatus(webDriverStatus);
        webScraper.setInputType(inputType);
        // Exercise
        boolean result = webScraper.scrapeFiveAlertsAndUploadToWebsite();
        // Verify
        Assertions.assertFalse(result);
    }
}
