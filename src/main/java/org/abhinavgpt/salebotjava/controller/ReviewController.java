package org.abhinavgpt.salebotjava.controller;

import org.abhinavgpt.salebotjava.service.orchestrator.ReviewOrchestrator;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {

    private final ReviewOrchestrator reviewOrchestrator;

    public ReviewController(ReviewOrchestrator reviewOrchestrator) {
        this.reviewOrchestrator = reviewOrchestrator;
    }

    @GetMapping
    public List<String> getReview(@RequestParam String url) {
        return reviewOrchestrator.getReviews(url);
    }


}
