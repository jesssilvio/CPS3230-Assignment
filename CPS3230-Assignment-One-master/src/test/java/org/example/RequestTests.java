package org.example;

import com.google.gson.Gson;
import org.example.Utils.*;
import org.example.WebScraper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.Invocation;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class RequestTests {
    Request request;
    Alert alert;
    String jsonString;
    WebScraper webScraper;
    API api;
    List<Alert> alertList = new LinkedList<>();

    @BeforeEach
    public void setup() {
        request = new Request();

        // Mock test alert
        alert = new Alert(
                6,
                "Samsung Galaxy Z Fold3 5G SM-F926U - 256GB Black (Unlocked) Very Good",
                "Very Good - Refurbished",
                "https://www.ebay.com/itm/194815512902?_trkparms=5079%3A5000014468",
                "https://i.ebayimg.com/images/g/l1YAAOSw8XFi8t-C/s-l500.jpg",
                62999
        );
        jsonString = new Gson().toJson(alert);
    }

    @AfterEach
    public void teardown() {
        request = null;
        alert = null;
        jsonString = null;
    }

    @Test
    // Testing the post request when web scraper is initialised
    public void testPostRequestWithJsonBody() throws IOException, InterruptedException {
        // Setup
        api = Mockito.mock(API.class);
        Mockito.when(api.getApi()).thenReturn(api.INITIALISED);
        request.setAPI(api);

        // Exercise
        int result = request.sendPostRequestToApi(jsonString);

        // Verify
        Assertions.assertEquals(201, result);
    }

    @Test
    // Testing the post request when web scraper is not initialised
    public void testPostRequestWithWebScraperNotInitialised() throws IOException, InterruptedException {
        // Setup
        api = Mockito.mock(API.class);
        Mockito.when(api.getApi()).thenReturn(api.NOT_INITIALISED);
        request.setAPI(api);
        // Exercise
        int result = request.sendPostRequestToApi(jsonString);
        // Verify
        Assertions.assertEquals(-1, result);
    }

    @Test
    // Testing a request with an alert type which does not exist (7), therefore test should fail
    public void testPostRequestWithIncorrectAlertType() throws IOException, InterruptedException {
        // Setup
        api = Mockito.mock(API.class);
        Mockito.when(api.getApi()).thenReturn(api.INITIALISED);
        request.setAPI(api);
        alert.setAlertType(7);
        jsonString = new Gson().toJson(alert);
        // Exercise
        int result = request.sendPostRequestToApi(jsonString);
        // Verify
        Assertions.assertEquals(201, result);
    }

    @Test
    // Testing a delete request when web scraper is initialised
    public void testDeleteWhenWebScraperInitialised() throws IOException, InterruptedException {
        // Setup
        api = Mockito.mock(API.class);
        Mockito.when(api.getApi()).thenReturn(api.INITIALISED);
        request.setAPI(api);

        // Exercise
        int result = request.sendDeleteRequestToApi();

        // Verify
        Assertions.assertEquals(200, result);
    }

    @Test
    // Testing a delete request when the web scraper is not initialised
    public void testDeleteWhenWebScraperNotInitialised() throws IOException, InterruptedException {
        // Setup
        api = Mockito.mock(API.class);
        Mockito.when(api.getApi()).thenReturn(api.NOT_INITIALISED);
        request.setAPI(api);

        // Exercise
        int result = request.sendDeleteRequestToApi();

        // Verify
        Assertions.assertEquals(-1, result);
    }

    @Test
    // Testing request to send five alerts with the right data
    public void testPostFiveAlertsToMarketAlertWithRightJsonBody() throws IOException, InterruptedException {
        // Setup
        api = Mockito.mock(API.class);
        Mockito.when(api.getApi()).thenReturn(api.INITIALISED);
        request.setAPI(api);
        for (int i = 0; i < 5; i++) {
            alertList.add(alert);
        }
        // Exercise
        boolean result = request.sendFiveAlertsToWebsite(alertList);
        Collection<Invocation> invocations = Mockito.mockingDetails(api).getInvocations();
        // Verify
        Assertions.assertTrue(result);
        Assertions.assertEquals(5, invocations.size());
    }
}
