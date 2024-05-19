package com.robertciotoiu.imocrawler.publi24;

import io.github.bonigarcia.wdm.WebDriverManager;
import jakarta.annotation.PreDestroy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import static com.robertciotoiu.imocrawler.scheduler.NaturalDelay.naturalAwait;

public class Publi24Controller {
    // Create a new ChromeDriver instance with desired capabilities
    private final WebDriver driver;
    private final Actions actions;
    private final String rootUrl;

    public Publi24Controller(String rootUrl) {
        this.rootUrl = rootUrl;
        // Configure Chrome options
        // chromeOptions.addArguments("--user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 13_5_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36");
        driver = WebDriverManager.chromedriver().create();
        this.actions = new Actions(driver);
    }

    @PreDestroy
    private void destroy() {
        // Close the WebDriver when done
        driver.quit();
    }

    public String openPage() throws InterruptedException {
        // Navigate to a webpage
        driver.get(rootUrl);
        naturalAwait();

        // Maximize window
        driver.manage().window().maximize();

        // Use WebDriver to find and extract data from the page
        return driver.getPageSource();
    }

    public String getHtml() {
        return driver.getPageSource();
    }

    public boolean nextPage() throws InterruptedException {
        WebElement nextPageButton = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div/div[2]/ul/ul/div[3]/li/a"));
        String hrefValue = nextPageButton.getAttribute("href");
        if (hrefValue != null && !hrefValue.isEmpty()) {
            actions.moveToElement(nextPageButton);
            naturalAwait();
            nextPageButton.click();
            naturalAwait();
            return true;
        } else {
            return false;
        }
    }

    public void solveCookies() throws InterruptedException {
        // Click first button to personalize cookies
        var gestioneazaOptiunileButton = driver.findElement(By.xpath("//*[@id=\"cl-consent\"]/div[1]/div[1]/div[3]/a[1]"));
        if (gestioneazaOptiunileButton != null) {
            gestioneazaOptiunileButton.click();
        }
        // Wait for JS to load
        naturalAwait();

        var acceptaSelectateButton = driver.findElement(By.xpath("//*[@id=\"cl-consent\"]/div[1]/div[2]/div[2]/a[2]"));
        if (acceptaSelectateButton != null) {
            acceptaSelectateButton.click();
        }
        naturalAwait();
    }

    public String getUrl() {
        return driver.getCurrentUrl();
    }
}
