package book.demo.java.controller;

import book.demo.java.exception.ExceptionUtil;
import book.demo.java.model.Cart;
import book.demo.java.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @Operation(summary = "Get the cart by readerId.")
    @GetMapping("/{readerId}")
    ResponseEntity<Cart> getCartByReaderId(int readerId) {
        try {
            Cart cart = cartService.getCartByReaderId(readerId);
            return new ResponseEntity<>(cart, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    null,
                    ExceptionUtil.getHeaderForException(e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
