package book.demo.java.controller;

import book.demo.java.exception.ExceptionUtil;
import book.demo.java.model.CartItem;
import book.demo.java.service.CartItemService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/cart")
public class CartItemController {
    @Autowired
    private CartItemService cartService;

    @Operation(summary = "Get all Cart items by reader id.")
    @GetMapping("/{readerId}")
    public ResponseEntity<List<CartItem>> listCartItem(@PathVariable int readerId) {
        try {
            List<CartItem> cartItems = cartService.getCartItemsByReaderId(readerId);
            if (cartItems.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(cartItems, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null,
                    ExceptionUtil.getHeaderForException(e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Add book to cart.")
    @PostMapping("/add/{readerId}")
    public ResponseEntity<CartItem> addBookToCart(
            @PathVariable("readerId") int readerId,
            @RequestParam int bookId,
            @RequestParam @Min(value = 1, message = "Book quantity must be greater than 0") int quantity) {
        try {
            List<CartItem> cartItems = cartService.getCartItemsByReaderId(readerId);
            if (cartItems.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(
                    cartService.addCartItem(bookId, quantity, readerId),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null,
                    ExceptionUtil.getHeaderForException(e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Delete CartItem by Reader id and Book id.")
    @DeleteMapping("/delete/{readerId}/{bookId}")
    public ResponseEntity<HttpStatus> deleteBookInCart(
            @PathVariable("readerId") int readerId,
            @PathVariable("bookId") int bookId
    ) {
        try {
            cartService.removeByReaderIdAndBookId(readerId, bookId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null,
                    ExceptionUtil.getHeaderForException(e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Clear CartItems by Reader id.")
    @DeleteMapping("/delete/{readerId}")
    public ResponseEntity<HttpStatus> clearCart(
            @PathVariable("readerId") int readerId
    ) {
        try {
            cartService.clearCartItemByReaderId(readerId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null,
                    ExceptionUtil.getHeaderForException(e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
