package org.abhinavgpt.salebotjava.service.scrapping;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public final class YouTubeScrapper implements Scrapper {
    private static final String COMMENT_CONTAINER = "#content-text";
    private static final int MAX_COMMENTS = 1000000; // Adjust according to your needs
    private WebDriver driver;

    public YouTubeScrapper() {
        System.setProperty("webdriver.chrome.driver", "path_to_chromedriver.exe");
        this.driver = new FirefoxDriver();
        this.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Override
    public List<String> scrap(String videoUrl) {
        List<String> comments = new ArrayList<>();

        try {
            driver.get(videoUrl);

            JavascriptExecutor js = (JavascriptExecutor) driver;

            while (comments.size() < MAX_COMMENTS) {
                // Scroll down to load a large number of comments at once
                js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
                Thread.sleep(5000); // Adjust the sleep time as needed

                // Find all comment elements
                List<WebElement> commentElements = driver.findElements(By.cssSelector(COMMENT_CONTAINER));

//               number of comments print
                System.out.println("Number of comments: " + commentElements.size());

                // Add new comments to the list
                for (WebElement element : commentElements) {
                    comments.add(element.getText());
                    if (comments.size() >= MAX_COMMENTS) break;
                }

                // If no more comments are loaded, break out of the loop
                if (commentElements.size() == 0) break;
            }
        } catch (InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error in scraping YouTube comments: " + e.getMessage());
        } finally {
            driver.quit();
        }

        return comments;
    }
}
