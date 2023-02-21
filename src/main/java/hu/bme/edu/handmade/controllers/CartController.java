package hu.bme.edu.handmade.controllers;

import hu.bme.edu.handmade.models.CartProduct;
import hu.bme.edu.handmade.services.impl.CartProductService;
import hu.bme.edu.handmade.web.dto.CartProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins="http://localhost:4200", maxAge=3600)
@RequestMapping("/cart")
public class CartController {
    @Autowired
    CartProductService cartService;

    @GetMapping("/{id}")
    List<CartProduct> getUserCart(@PathVariable("id") long userId) {
        return cartService.getCartProductsByUser(userId);
    }

    @PostMapping()
    CartProduct addCartProduct(@RequestBody CartProductDto cartProductDto) {
        return cartService.addCartProduct(cartProductDto);
    }

    @PutMapping()
    CartProduct updateCartProduct(@RequestBody CartProductDto cartProductDto) {
        return cartService.findCartProductById(Long.parseLong(cartProductDto.getId()))
                .map(product -> cartService.updateCartProduct(cartProductDto))
                .orElseGet(()->cartService.addCartProduct(cartProductDto));
    }

    @DeleteMapping("/{id}")
    void removeCartProduct(@PathVariable("id") Long id) {
        Optional<CartProduct> deletedCartProduct = cartService.findCartProductById(id);
        deletedCartProduct.ifPresent(cartService::deleteCartProduct);
    }
}
