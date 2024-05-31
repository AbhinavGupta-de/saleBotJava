package org.abhinavgpt.salebotjava.service.reviews;

import java.util.List;

public sealed interface IReviewService permits ReviewService {
    List<String> getReviews(String url);
}
