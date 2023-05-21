package hu.bme.edu.handmade.services.impl;

import hu.bme.edu.handmade.mappers.ReviewMapper;
import hu.bme.edu.handmade.models.Product;
import hu.bme.edu.handmade.models.Review;
import hu.bme.edu.handmade.models.User;
import hu.bme.edu.handmade.repositories.ReviewRepository;
import hu.bme.edu.handmade.services.IReviewService;
import hu.bme.edu.handmade.web.dto.ReviewDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ReviewService implements IReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductService productService;
    private final UserService userService;

    public ReviewService(ReviewRepository reviewRepository, ProductService productService, UserService userService) {
        this.reviewRepository = reviewRepository;
        this.productService = productService;
        this.userService = userService;
    }

    @Override
    public List<ReviewDto> getReviews(Long productId) {
        return ReviewMapper.INSTANCE.toReviewDtos(reviewRepository.findAllByProductId(productId));
    }

    @Override
    public ReviewDto addReview(Long productId, ReviewDto dto) {
        Product foundProduct = productService.findProductById(productId).orElseThrow();
        User foundUser = userService.getUserByID(dto.getUserId());
        Review newReview = ReviewMapper.INSTANCE.toReviewFromDto(dto);
        newReview.setProduct(foundProduct);
        newReview.setUser(foundUser);
        return ReviewMapper.INSTANCE.toReviewDto(reviewRepository.save(newReview));
    }


}
