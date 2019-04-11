package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.carts.CartDto;
import com.kodilla.ecommercee.domain.orders.Item;
import com.kodilla.ecommercee.mapper.CartMapper;
import com.kodilla.ecommercee.service.CartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/cart")
public class CartController {
    private final CartService cartService;
    private final CartMapper cartMapper;

    public CartController(CartService cartService, CartMapper cartMapper) {
        this.cartService = cartService;
        this.cartMapper = cartMapper;
    }

    @GetMapping(value = "getProductsFromCart")
    public List<Item> getProductsFromCart(@RequestParam Long cartId) {
        return cartService.getAllItemInCart(cartId);
    }

    @PostMapping(value = "createCart", consumes = APPLICATION_JSON_VALUE)
    public void createCart(@RequestBody CartDto cartDto) {
        cartService.saveCart(cartMapper.mapToCart(cartDto));
    }

    @PostMapping(value = "createOrder", consumes = APPLICATION_JSON_VALUE)
    public void createOrder(@RequestParam Long userId) {
        cartService.createOrderBaseOnCart(userId);
    }

    @PostMapping(value = "addItemToCart", consumes = APPLICATION_JSON_VALUE)
    public void addItemToCart(@RequestParam Long productId, @RequestParam Long cartId, @RequestParam Integer quantity) {
        cartService.addItemToCart(productId, cartId, quantity);
    }

    @DeleteMapping(value = "deleteProductFromCart")
    public void deleteProductFromCart(@RequestParam Long itemId, @RequestParam Long cartId) {
        cartService.deleteItemFromCart(itemId, cartId);
    }
}