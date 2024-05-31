package org.abhinavgpt.salebotjava.service.scrapping;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public final class YouTubeScrapper implements Scrapper {
    private static final String COMMENT_CONTAINER = "#content-text";
    private static final int MAX_COMMENTS = 1_000_000;
    private WebDriver driver;

    public YouTubeScrapper() {
        System.setProperty("webdriver.chrome.driver", "path_to_chromedriver.exe");
        this.driver = new FirefoxDriver();
    }

    @Override
    public List<String> scrap(String videoUrl) {
        List<String> comments = new ArrayList<>();

        try {
            driver.get(videoUrl);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // Initial scroll to load some comments
            js.executeScript("window.scrollTo(0, document.documentElement.scrollHeight);");
            Thread.sleep(2000); // Wait for comments to load

            int previousCommentsSize = 0;
            int scrollAttempts = 0;

            while (comments.size() < MAX_COMMENTS) {
                List<WebElement> commentElements = driver.findElements(By.cssSelector(COMMENT_CONTAINER));

                // Add new comments to the list
                for (WebElement element : commentElements) {
                    comments.add(element.getText());
                    if (comments.size() >= MAX_COMMENTS) break;
                }

                // If no more comments are loaded or no new comments are added, break out of the loop
                if (commentElements.size() == 0 || comments.size() == previousCommentsSize) {
                    scrollAttempts++;
                    if (scrollAttempts > 3) {
                        // If no new comments are loaded after multiple attempts, break out of the loop
                        break;
                    }
                } else {
                    // Reset the scroll attempts counter if new comments are loaded
                    scrollAttempts = 0;
                }

                // Scroll down to load more comments
                js.executeScript("window.scrollTo(0, document.documentElement.scrollHeight);");
                wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(COMMENT_CONTAINER), comments.size()));
                Thread.sleep(2000); // Wait for comments to load

                previousCommentsSize = comments.size();
            }
        } catch (InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (TimeoutException e) {
            System.out.println("Timeout while waiting for comments to load: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error in scraping YouTube comments: " + e.getMessage());
        } finally {
            driver.quit();
        }

        System.out.println(comments.size());
        return comments;
    }
}
