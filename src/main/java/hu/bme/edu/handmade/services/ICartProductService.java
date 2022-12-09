package hu.bme.edu.handmade.services;

import hu.bme.edu.handmade.models.CartProduct;
import hu.bme.edu.handmade.web.dto.CartProductDto;

import java.util.List;

public interface ICartProductService {

    CartProduct addCartProduct(CartProductDto cartProductDto);

    List<CartProduct> getCartProductsByUser(long userId);

    CartProduct updateCartProduct(CartProductDto cartProductDto);

    void deleteCartProduct(CartProductDto cartProductDto);

}
