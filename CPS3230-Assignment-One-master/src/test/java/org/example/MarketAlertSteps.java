package org.example;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.Utils.API;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import java.io.IOException;

public class MarketAlertSteps {
    MarketAlertScraper marketAlertScraper;
    API API;
    Request request;

    @Given("I am a user of marketalertum")
    public void iAmAUserOfMarketalertum() {
        marketAlertScraper = new MarketAlertScraper();
        marketAlertScraper.setupDriver();
    }

    // Task 2, Test 1
    @When("I login using valid credentials")
    public void iLoginUsingValidCredentials() {
        marketAlertScraper.validLogin();
    }

    @Then("I should see my alerts")
    public void iShouldSeeMyAlerts() {
        Assertions.assertTrue(marketAlertScraper.result);
    }

    // Task 2, Test 2
    @When("I login using invalid credentials")
    public void iLoginUsingInvalidCredentials() {
        marketAlertScraper.invalidLogin();
    }

    @Then("I should see the login screen again")
    public void iShouldSeeTheLoginScreenAgain() {
        Assertions.assertTrue(marketAlertScraper.result);
    }

    // Task 2, Test 3
    @Given("I am an administrator of the website and I upload 3 alerts")
    public void iAmAnAdministratorOfTheWebsiteAndIUploadAlerts(int arg0) throws IOException, InterruptedException {
        API = Mockito.mock(API.class);
        Mockito.when(API.getApi()).thenReturn(API.INITIALISED);
        marketAlertScraper.uploadAlerts(arg0, API);
    }

    @When("I view a list of alerts")
    public void iViewAListOfAlerts() {
        marketAlertScraper.viewListOfAlerts();
    }

    @Then("and each alert should contain an icon")
    public void eachAlertShouldContainAnIcon() {
        Assertions.assertEquals(3, marketAlertScraper.resultOne);
    }

    @And("and each alert should contain a heading")
    public void eachAlertShouldContainAHeading() {
        Assertions.assertEquals(3, marketAlertScraper.resultTwo);
    }

    @And("and each alert should contain a description")
    public void eachAlertShouldContainADescription() {
        Assertions.assertEquals(3, marketAlertScraper.resultThree);
    }

    @And("and each alert should contain an image")
    public void eachAlertShouldContainAnImage() {
        Assertions.assertEquals(3, marketAlertScraper.resultFour);
    }

    @And("and each alert should contain a price")
    public void eachAlertShouldContainAPrice() {
        Assertions.assertEquals(3, marketAlertScraper.resultFive);
    }

    @And("and each alert should contain a link to the original product website")
    public void eachAlertShouldContainALinkToTheOriginalProductWebsite() {
        Assertions.assertEquals(3, marketAlertScraper.resultSix);
    }

    // Task 2, Test 4
    @Given("I am an administrator of the website and I upload more than 5 alerts")
    public void iAmAnAdministratorOfTheWebsiteAndIUploadMoreThanAlerts(int arg0) throws IOException, InterruptedException {
        API = Mockito.mock(API.class);
        Mockito.when(API.getApi()).thenReturn(API.INITIALISED);
        marketAlertScraper.uploadAlerts(arg0 + 1, API);
    }

    // Task 2, Test 5
    @Given("I am an administrator of the website and I upload an alert of type <alert-type>")
    public void iAmAnAdministratorOfTheWebsiteAndIUploadAnAlertOfType(String arg0) throws IOException, InterruptedException {
        API = Mockito.mock(API.class);
        Mockito.when(API.getApi()).thenReturn(API.INITIALISED);
        marketAlertScraper.uploadAlertType(arg0, API);
    }

    @And("the icon displayed should be <icon file name>")
    public void theIconDisplayedShouldBe(String arg0) {
        Assertions.assertEquals(arg0, marketAlertScraper.resultIcon);
    }
}
