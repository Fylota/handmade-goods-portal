package hu.bme.edu.handmade.services.impl;

import hu.bme.edu.handmade.mappers.ProductMapper;
import hu.bme.edu.handmade.models.Product;
import hu.bme.edu.handmade.models.User;
import hu.bme.edu.handmade.models.Wishlist;
import hu.bme.edu.handmade.repositories.ProductRepository;
import hu.bme.edu.handmade.repositories.UserRepository;
import hu.bme.edu.handmade.repositories.WishListRepository;
import hu.bme.edu.handmade.services.IWishListService;
import hu.bme.edu.handmade.web.dto.ProductDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class WishListService implements IWishListService {
    private final WishListRepository wishListRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public WishListService(WishListRepository wishListRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.wishListRepository = wishListRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDto> getWishListItemsByUserId(Long userId) {
        User foundUser = userRepository.findById(userId).orElseThrow();
        return wishListRepository.findByUser_Id(userId)
            .map( wishlist -> ProductMapper.INSTANCE.toProductDtos(wishlist.getProducts()))
                .orElseGet(() -> {
                Wishlist wl = new Wishlist(foundUser);
                wl.setUser(foundUser);
                wishListRepository.save(wl);
                return Collections.emptyList() ;
            });
    }

    @Override
    public ProductDto addToWishlist(Long userId, Long productId) {
        User foundUser = userRepository.findById(userId).orElseThrow();
        Product foundProduct = productRepository.findById(productId).orElseThrow();
        return wishListRepository.findByUser_Id(userId)
                .map( wishlist -> {
                    wishlist.addProduct(foundProduct);
                    return ProductMapper.INSTANCE.toProductDto(foundProduct);
                })
                .orElseGet(() -> {
                    Wishlist wl = new Wishlist(foundUser);
                    wl.setUser(foundUser);
                    wl.addProduct(foundProduct);
                    wishListRepository.save(wl);
                    return ProductMapper.INSTANCE.toProductDto(foundProduct);
                });
    }

    @Override
    public void removeFromWishList(Long userId, Long productId) {
        Product foundProduct = productRepository.findById(productId).orElseThrow();
        wishListRepository.findByUser_Id(userId).ifPresent(wishlist -> wishlist.removeProduct(foundProduct));
    }
}
