package hu.bme.edu.handmade.services.impl;

import hu.bme.edu.handmade.mappers.CartProductMapper;
import hu.bme.edu.handmade.models.CartProduct;
import hu.bme.edu.handmade.repositories.CartProductRepository;
import hu.bme.edu.handmade.services.ICartProductService;
import hu.bme.edu.handmade.services.IProductService;
import hu.bme.edu.handmade.services.IUserService;
import hu.bme.edu.handmade.web.dto.CartProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartProductService implements ICartProductService {
    @Autowired
    CartProductRepository cartProductRepository;

    @Autowired
    IProductService productService;

    @Autowired
    IUserService userService;

    @Override
    public CartProduct addCartProduct(CartProductDto cartProductDto) {
        CartProduct cartProduct = CartProductMapper.INSTANCE.toCartProduct(cartProductDto);
        productService.findProductById(Long.parseLong(cartProductDto.getProductId())).ifPresent(cartProduct::setProduct);
        userService.getUserByID(Long.parseLong(cartProductDto.getUserId())).ifPresent(cartProduct::setUser);
        return cartProductRepository.save(cartProduct);
    }

    @Override
    public List<CartProduct> getCartProductsByUser(long userId) {
        return cartProductRepository.findCartProductsByUserId(userId);
    }

    @Override
    public CartProduct updateCartProduct(CartProductDto cartProductDto) {
        CartProduct cartProduct = CartProductMapper.INSTANCE.toCartProduct(cartProductDto);
        return cartProductRepository.save(cartProduct);
    }

    @Override
    public void deleteCartProduct(CartProduct cartProduct) {
        cartProductRepository.findById(cartProduct.getId()).ifPresent(cp ->
            cartProductRepository.delete(cp)
        );
    }

    @Override
    public Optional<CartProduct> findCartProductById(Long id) {
        return cartProductRepository.findById(id);
    }

}
