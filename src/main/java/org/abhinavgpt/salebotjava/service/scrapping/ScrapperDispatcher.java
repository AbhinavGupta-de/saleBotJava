package org.abhinavgpt.salebotjava.service.scrapping;

public class ScrapperDispatcher {
    public Scrapper getScrapper(String url) {
        if (url.contains("youtube")) {
            return new YouTubeScrapper();
        }
        return null;
    }
}
