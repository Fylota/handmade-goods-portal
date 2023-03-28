package hu.bme.edu.handmade.services.impl;

import hu.bme.edu.handmade.mappers.CartProductMapper;
import hu.bme.edu.handmade.models.CartProduct;
import hu.bme.edu.handmade.models.Product;
import hu.bme.edu.handmade.models.User;
import hu.bme.edu.handmade.repositories.CartProductRepository;
import hu.bme.edu.handmade.services.ICartProductService;
import hu.bme.edu.handmade.services.IProductService;
import hu.bme.edu.handmade.services.IUserService;
import hu.bme.edu.handmade.web.dto.CartProductDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartProductService implements ICartProductService {
    private final CartProductRepository cartProductRepository;
    private final IProductService productService;
    private final IUserService userService;

    CartProductService(CartProductRepository cartProductRepository, IProductService productService, IUserService userService) {
        this.cartProductRepository = cartProductRepository;
        this.productService = productService;
        this.userService = userService;
    }

    @Override
    public CartProduct addCartProduct(CartProductDto cartProductDto, long userId) {
        User foundUser = userService.getUserByID(userId).orElseThrow();
        Product foundProduct = productService.findProductById(cartProductDto.getProductId()).orElseThrow();
        CartProduct cartProduct = CartProductMapper.INSTANCE.toCartProduct(cartProductDto);
        cartProduct.setProduct(foundProduct);
        cartProduct.setUser(foundUser);
        return cartProductRepository.save(cartProduct);
    }

    @Override
    public List<CartProduct> getCartProductsByUser(long userId) {
        return cartProductRepository.findCartProductsByUserId(userId);
    }

    @Override
    public CartProduct updateCartProduct(CartProductDto cartProductDto, long cartProdId) {
        CartProduct foundCartProd = cartProductRepository.findById(cartProdId).orElseThrow();
        CartProductMapper.INSTANCE.updateCartProductFromDto(cartProductDto, foundCartProd);
        return cartProductRepository.save(foundCartProd);
    }

    @Override
    public void deleteCartProduct(long id) {
        cartProductRepository.deleteById(id);
    }

    @Override
    public Optional<CartProduct> findCartProductById(Long id) {
        return cartProductRepository.findById(id);
    }

}
