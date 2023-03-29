package hu.bme.edu.handmade.mappers;

import hu.bme.edu.handmade.models.Review;
import hu.bme.edu.handmade.web.dto.ReviewDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ReviewMapper {

    ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "userId", source = "user.id")
    ReviewDto toReviewDto(Review review);

    List<ReviewDto> toReviewDtos(List<Review> reviews);

    Review toReviewFromDto(ReviewDto dto);
}
