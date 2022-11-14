package org.example;

import org.example.Utils.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

public class WebScraper {
    public WebScraper() {
    }

    protected InputType inputType;
    protected Ebay ebay;
    protected WebDriverStatus webDriverStatus;
    protected API api;
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected List<WebElement> items;
    protected List<Alert> alerts = new LinkedList<>();
    protected Utils utils;


    // Initialise XPaths here
    protected String itemsOnPageXPath = "//div[contains(@class, 'ui') and contains(@class, 'items') and contains(@class, 'listings') and contains(@class, 'classifieds') and contains(@class, 'clearfix') and contains(@class, 'gridview')]//div[contains(@class, 'item')]//div[contains(@class, 'content')]//a";
    protected String headingXPath = "//div[contains(@class, 'details-heading') and contains(@class, 'clearfix') and contains(@class, 'tablet-visible')]//h1[contains(@class, 'top-title')]//span";
    protected String descriptionXPath = "//div[contains(@class, 'ui') and contains(@class, 'segment') and contains(@class, 'whitebg') and contains(@class, 'shadowbox')]//div[contains(@class, 'readmore-wrapper')]";
    protected String imageUrlXPath = "//div[contains(@class, 'slick-track')]//a[contains(@class, 'fancybox')]//img";
    protected String priceXPath = "//h1[contains(@class, 'top-price')]";

    public void setInputProvider(InputType inputType) {
        this.inputType = inputType;
    }

    public void setEbay(Ebay ebay) {
        this.ebay = ebay;
    }

    public void setWebDriverStatus(WebDriverStatus webDriverStatus) {
        this.webDriverStatus = webDriverStatus;
    }

    public void setAPI(API api) {
        this.api = api;
    }
    public void setInputType(InputType inputType) {this.inputType = inputType;}

    protected boolean setupDriver() {
        if (webDriverStatus == null) {
            return false;
        }

        // Setup Chrome Driver
        if (webDriverStatus.getDriverStatus() == WebDriverStatus.DRIVER_NOT_INITIALISED) {
            return false;
        } else {
            System.setProperty("webdriver.chrome.driver", "C:/chromedriver/chromedriver.exe");
            driver = new ChromeDriver();
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        }
        return true;
    }

    public boolean scrapeFiveAlertsAndUploadToWebsite() throws IOException, InterruptedException {
        // Check that the input provider is initialised
        if (inputType == null) {
            return false;
        }

        utils = new Utils();
        String searchText = "";

        int input = inputType.userInput();
        ;

        boolean setup = setupDriver();
        if (!setup) {
            return false;
        }

        if (ebay == null) {
            driver.quit();
            return false;
        }

        if (ebay.getEbay() == ebay.WEBSITE_UNAVAILABLE) {
            driver.quit();
            return false;
        } else {
            driver.get("https://www.ebay.com/globaldeals/tech/cell-phones");
            driver.manage().window().maximize();


            WebElement searchField = driver.findElement(By.id("search"));
            searchField.sendKeys(searchText);
            searchField.sendKeys(Keys.ENTER);

            int scrapedItems = 0;

            try {
                wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(itemsOnPageXPath)));
                items = driver.findElements(By.xpath(itemsOnPageXPath));
            } catch (Exception e) {
                System.out.println("Exception while scraping items from page: " + e);
                driver.quit();
                return false;
            }

            // Loop for 25 times just in case there is something wrong with an element
            // Example - Element missing price -> element is skipped
            // Example - Element missing image -> image is skipped
            for (int i = 0; i < 25; i++) {
                // Retry because DOM refreshes
                try {
                    wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(itemsOnPageXPath)));
                    items = driver.findElements(By.xpath(itemsOnPageXPath));
                } catch (Exception e) {
                    System.out.println("Exception while scraping items from page: " + e);
                    driver.quit();
                    return false;
                }

                if (items.size() == 0) {
                    driver.quit();
                    return false;
                }

                driver.navigate().to(items.get(i).getAttribute("href"));

                boolean faultInItem = false;
                String heading = "";
                String description = "";
                String url = "";
                String imageUrl = "";
                int priceInCents = 0;

                // Heading
                try {
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(headingXPath)));
                    heading = driver.findElement(By.xpath(headingXPath)).getText();
                } catch (Exception e) {
                    faultInItem = true;
                }

                // Description
                try {
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(descriptionXPath)));
                    description = driver.findElement(By.xpath(descriptionXPath)).getText();
                } catch (Exception e) {
                    faultInItem = true;
                }

                // Url
                url = driver.getCurrentUrl();

                // ImageUrl
                try {
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(imageUrlXPath)));
                    imageUrl = driver.findElement(By.xpath(imageUrlXPath)).getAttribute("src");
                } catch (Exception e) {
                    faultInItem = true;
                }

                // PriceInCents
                try {
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(priceXPath)));
                    priceInCents = utils.parsePrice(driver.findElement(By.xpath(priceXPath)).getText());
                } catch (Exception e) {
                    faultInItem = true;
                }

                // Check if there was something wrong with one of the items scraped - missing price, missing image, etcetera
                if (!faultInItem) {
                    // Add scraped item count
                    scrapedItems++;

                    // Add alert
                    alerts.add(new Alert(input, heading, description, url, imageUrl, priceInCents));
                }

                // Break out of the loop after 5 scraped items
                if (scrapedItems == 5) {
                    break;
                }

                driver.navigate().back();
            }

            Request request = new Request();

            if (ebay == null) {
                driver.quit();
                return false;
            }

            // Set webScraper for the request class
            request.setAPI(api);

            boolean requests = request.sendFiveAlertsToWebsite(alerts);

            if (!requests) {
                driver.quit();
                return false;
            }

            driver.quit();
            return true;
        }
    }
}
