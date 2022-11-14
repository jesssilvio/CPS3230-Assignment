package org.example;

import com.google.gson.Gson;
import org.example.Utils.API;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MarketAlertScraper {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected List<Alert> alerts = new LinkedList<>();

    protected Request request;
    protected String jsonString;

    String userId = "4414c849-bd0e-4f98-9bd4-405c4d6df075";

    String loginXPath = "//div[@class='navbar-collapse collapse d-sm-inline-flex justify-content-between']//li[2]//a";
    String userIdId = "UserId";
    String validLoginXPath = "//main[@class='pb-3']//h1";
    String iconXPath = "//table//tbody//tr[1]//td//h4//img";
    String headingXPath = "//table//tr[1]//h4";
    String descriptionXPath = "//table//tr[3]//td";
    String imageXPath = "//table//tr[2]//td//img";
    String priceXPath = "//table//tr[4]//td";
    String linkXPath = "//table//tr[5]//a";
    String alertsXPath = "//table";

    public boolean result;

    public int resultOne;
    public int resultTwo;
    public int resultThree;
    public int resultFour;
    public int resultFive;
    public int resultSix;
    public int resultAlertCount;

    public String resultIcon;

    // Task 1, Mocks
    Alert electronicsAlert = new Alert(
            6,
            "Apple iPhone 11 64GB 128GB 256GB - Excellent Condition - TWO YEAR WARRANTY",
            "EARLY BLACK FRIDAY SALE 10% OFF WITH CODE EARLYTECH10",
            "https://www.ebay.com/itm/284664852917?_trkparms=5079%3A5000014468",
            "https://i.ebayimg.com/images/g/CkwAAOSwR7ZiFMpT/s-l500.png",
            34665
    );

    Alert electronicsAlert2 = new Alert(
            6,
            "Apple iPhone 13 Pro Max | 256GB Graphite | T-Mobile + Metro | Excellent",
            "Excellent - Refurbished",
            "https://www.ebay.com/itm/265862160850?_trkparms=5079%3A5000014468",
            "https://i.ebayimg.com/images/g/7SIAAOSwLM1jIO4a/s-l500.png",
            94999
    );

    Alert electronicsAlert3 = new Alert(
            6,
            "Google Pixel 5A 5G - G1F8F - 128GB - Black - (Unlocked) – Good",
            "Good - Refurbished",
            "https://www.ebay.com/itm/185390325176?_trkparms=5079%3A5000014468",
            "https://i.ebayimg.com/images/g/4dwAAOSw2cZhpgdF/s-l500.jpg",
            21999
    );

    Alert electronicsAlert4 = new Alert(
            6,
            "Samsung Galaxy S20 FE 5G SM-G781U - 128GB - Navy - (T-Mobile ) - Good",
            "Good - Refurbished",
            "https://www.ebay.com/itm/175368180034?_trkparms=5079%3A5000014468",
            "https://i.ebayimg.com/images/g/Y98AAOSwWHVi4pmR/s-l500.jpg",
            18999
    );

    Alert electronicsAlert5 = new Alert(
            6,
            "Samsung Galaxy Z Fold3 5G SM-F926U - 256GB Black (Unlocked) Very Good",
            "Very Good - Refurbished",
            "https://www.ebay.com/itm/194815512902?_trkparms=5079%3A5000014468",
            "https://i.ebayimg.com/images/g/l1YAAOSw8XFi8t-C/s-l500.jpg",
            62999
    );

    Alert electronicsAlert6 = new Alert(
            6,
            "Apple iPhone 11 64GB 128GB 256GB - Excellent Condition - TWO YEAR WARRANTY",
            "EARLY BLACK FRIDAY SALE 10% OFF WITH CODE EARLYTECH10",
            "https://www.ebay.com/itm/284664852917?_trkparms=5079%3A5000014468",
            "https://i.ebayimg.com/images/g/CkwAAOSwR7ZiFMpT/s-l500.png",
            34645
    );

    public void setupDriver(){
        System.setProperty("webdriver.chrome.driver", "C:/chromedriver/chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void validLogin(){
        driver.get("https://www.marketalertum.com/");
        driver.manage().window().maximize();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(loginXPath)));
        driver.findElement(By.xpath(loginXPath)).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(userIdId)));
        WebElement element =  driver.findElement(By.id(userIdId));
        element.sendKeys(userId);
        element.sendKeys(Keys.ENTER);
        driver.quit();
    }

    public void invalidLogin(){
        driver.get("https://www.marketalertum.com/");
        driver.manage().window().maximize();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(loginXPath)));
        driver.findElement(By.xpath(loginXPath)).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(userIdId)));
        WebElement element =  driver.findElement(By.id(userIdId));
        element.sendKeys("Invalid Login Credentials");
        element.sendKeys(Keys.ENTER);

        if (driver.getCurrentUrl().equals("https://www.marketalertum.com/Alerts/Login")){
            result = true;
        } else {
            result = false;
        }
        driver.quit();
    }
    public void uploadAlerts(int n, API api) throws IOException, InterruptedException {
        switch (n) {

            case 6 -> {

                alerts.add(electronicsAlert);
            }
        }
        request = new Request();
        request.setAPI(api);

        // Delete alerts beforehand
        request.sendDeleteRequestToApi();

        // Upload alerts
        request.sendFiveAlertsToWebsite(alerts);
    }

    public void uploadAlertType(String type, API API) throws IOException, InterruptedException {
        request = new Request();
        request.setAPI(API);

        // Delete alerts beforehand
        request.sendDeleteRequestToApi();

        // Alert type
        switch (type) {
            case "6" -> {
                jsonString = new Gson().toJson(electronicsAlert);
            }
        }
        // Upload specific alert
        request.sendPostRequestToApi(jsonString);
    }

    public void viewListOfAlerts() {
        driver.get("https://www.marketalertum.com/");
        driver.manage().window().maximize();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(loginXPath)));
        driver.findElement(By.xpath(loginXPath)).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(userIdId)));
        WebElement element =  driver.findElement(By.id(userIdId));
        element.sendKeys(userId);
        element.sendKeys(Keys.ENTER);

        // Icons
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(iconXPath)));
        List<WebElement> iconElements = driver.findElements(By.xpath(iconXPath));

        // Headings
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(headingXPath)));
        List<WebElement> headingElements = driver.findElements(By.xpath(headingXPath));
        List<String> headingList = new ArrayList<>();
        for (WebElement headingElement : headingElements) {
            String heading = headingElement.getText();
            if (!heading.equals("")) {
                headingList.add(heading);
            }
        }

        // Descriptions
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(descriptionXPath)));
        List<WebElement> descriptionElements = driver.findElements(By.xpath(descriptionXPath));
        List<String> descriptionList = new ArrayList<>();
        for (WebElement descriptionElement : descriptionElements) {
            String description = descriptionElement.getText();
            if (!description.equals("")) {
                descriptionList.add(description);
            }
        }

        // Images
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(imageXPath)));
        List<WebElement> imageElements = driver.findElements(By.xpath(imageXPath));

        // Price
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(priceXPath)));
        List<WebElement> priceElements = driver.findElements(By.xpath(priceXPath));
        List<String> priceList = new ArrayList<>();
        for (WebElement priceElement : priceElements) {
            String price = priceElement.getText().replace("Price: ", "");
            if (!price.equals("")) {
                priceList.add(price);
            }
        }

        // Link
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(linkXPath)));
        List<WebElement> linkElements = driver.findElements(By.xpath(linkXPath));

        // Alert list
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(alertsXPath)));
        List<WebElement> alertsElements = driver.findElements(By.xpath(alertsXPath));

        // Checking if all lists are of size 3
        resultOne = iconElements.size();

        resultTwo = headingList.size();

        resultThree = descriptionList.size();

        resultFour = imageElements.size();

        resultFive = priceList.size();

        resultSix = linkElements.size();

        // Task 2, Test 4
        resultAlertCount = alertsElements.size();

        // Task 2, Test 5
        resultIcon = iconElements.get(0).getAttribute("src").split("/")[4];

        // Quit driver
        driver.quit();
    }
}
