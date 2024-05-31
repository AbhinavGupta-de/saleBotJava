package org.abhinavgpt.salebotjava.service.reviews;

import org.abhinavgpt.salebotjava.service.scrapping.Scrapper;
import org.abhinavgpt.salebotjava.service.scrapping.ScrapperDispatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class ReviewService implements IReviewService{

    @Override
    public List<String> getReviews(String url) {
        Scrapper scrapper = new ScrapperDispatcher().getScrapper(url);
        return scrapper.scrap(url);
    }
}
