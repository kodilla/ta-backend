package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.carts.CartDto;
import com.kodilla.ecommercee.domain.orders.Item;
import com.kodilla.ecommercee.mapper.CartMapper;
import com.kodilla.ecommercee.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final CartMapper cartMapper;

    @Autowired
    public CartController(CartService cartService, CartMapper cartMapper) {
        this.cartService = cartService;
        this.cartMapper = cartMapper;
    }

    @GetMapping
    public List<Item> getProductsFromCart(@RequestParam Long cartId) {
        return cartService.getAllProductInCart(cartId);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
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

    @DeleteMapping
    public void deleteItemFromCart(@RequestParam Long itemId, @RequestParam Long cartId) throws com.kodilla.ecommercee.controller.exception.ItemNotFoundException {
        cartService.deleteItemFromCart(itemId, cartId);
    }
}