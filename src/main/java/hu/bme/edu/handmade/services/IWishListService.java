package hu.bme.edu.handmade.services;

import hu.bme.edu.handmade.web.dto.ProductDto;

import java.util.List;

public interface IWishListService {
    List<ProductDto> getWishListItemsByUserId(Long userId);
    ProductDto addToWishlist(Long userId, Long productId);
    void removeFromWishList(Long userId, Long productId);
}
