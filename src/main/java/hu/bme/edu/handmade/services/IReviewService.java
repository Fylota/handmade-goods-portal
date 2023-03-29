package hu.bme.edu.handmade.services;

import hu.bme.edu.handmade.web.dto.ReviewDto;

import java.util.List;

public interface IReviewService {
    List<ReviewDto> getReviews(Long productId);

    ReviewDto addReview(Long productId, ReviewDto dto);
}
