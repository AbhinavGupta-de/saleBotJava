package org.abhinavgpt.salebotjava.service.orchestrator;

import org.abhinavgpt.salebotjava.service.reviews.IReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewOrchestrator {
    private final IReviewService reviewService;

    public ReviewOrchestrator(IReviewService reviewService) {
        this.reviewService = reviewService;
    }

    public List<String> getReviews(String url) {
        return reviewService.getReviews(url);
    }
}
