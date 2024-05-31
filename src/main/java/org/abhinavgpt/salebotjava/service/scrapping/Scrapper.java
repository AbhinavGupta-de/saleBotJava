package org.abhinavgpt.salebotjava.service.scrapping;

import java.util.List;

public sealed interface Scrapper permits YouTubeScrapper{
    public List<String> scrap(String url);
}
