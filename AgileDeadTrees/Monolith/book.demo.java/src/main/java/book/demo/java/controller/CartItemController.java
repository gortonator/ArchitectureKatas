package book.demo.java.controller;

import book.demo.java.exception.ExceptionUtil;
import book.demo.java.model.CartItem;
import book.demo.java.service.CartItemService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartItemController {
    @Autowired
    private CartItemService cartService;

    //attention: what parameter to use to get the reader
    @Operation(summary = "Get all Cart items by reader id.")
    @GetMapping("/{readerId}")
    public ResponseEntity<List<CartItem>> listCartItem(@PathVariable int readerId) {
        try {
            List<CartItem> cartItems = cartService.getCartItemsByReaderId(readerId);
            if (cartItems.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(cartItems, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(null,
                    ExceptionUtil.getHeaderForException(e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
