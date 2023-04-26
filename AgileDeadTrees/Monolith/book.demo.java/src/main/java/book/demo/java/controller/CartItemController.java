/**
 * This is the controller class for handling HTTP requests related to the CartItem entity.
 * After authentication and authorization checks, the controller methods manipulates CartItem entities
 * stored in the database.
 * <p>
 * Endpoints:
 * GET /api/cart: Get all Cart items for the logged in reader.
 * GET /api/cart/{readerId}: Get all Cart items by reader id.
 * POST /api/cart/add: Reader adds book (book variant) to cart or updates its quantity in cart.
 * DELETE /api/cart/remove: Delete CartItem by Reader id and BookVariant id.
 *
 * @author Tong
 * @see AuthUtil
 * @see PredefinedRole
 */

package book.demo.java.controller;

import book.demo.java.entity.cart.CartItem;
import book.demo.java.service.CartItemService;
import book.demo.java.util.AuthUtil;
import book.demo.java.util.PredefinedRole;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/cart")
public class CartItemController {
    @Autowired
    private CartItemService cartService;

    /**
     * Retrieve all CartItem objects for the currently authenticated reader.
     *
     * @return A ResponseEntity containing a list of CartItem objects and an HTTP status code.
     */
    @Operation(summary = "Get all Cart items for logged in reader.")
    @GetMapping
    @RequiresRoles(PredefinedRole.READER_ROLE)
    public ResponseEntity<List<CartItem>> listCartItemForAuthenticatedReader() {
        String username = AuthUtil.getAuthenticatedUsername();
        List<CartItem> cartItems = cartService.getCartItemsByReaderUsername(username);
        if (cartItems.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(cartItems, HttpStatus.OK);
    }

    /**
     * Retrieve all CartItem objects by a reader id.
     *
     * @param readerId the id of the reader whose cartItems are retrieved for.
     * @return A ResponseEntity containing a list of CartItem objects and an HTTP status code.
     */
    @Operation(summary = "Get all Cart items by reader id.")
    @GetMapping("/{readerId}")
    public ResponseEntity<List<CartItem>> listCartItem(@PathVariable int readerId) {
        List<CartItem> cartItems = cartService.getCartItemsByReaderId(readerId);
        if (cartItems.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(cartItems, HttpStatus.OK);
    }

    /**
     * Add a book (book variant of a specific book format) to cart or updates its quantity in cart
     * if the item already existed in cart.
     *
     * @param bookVariantId the id of the BookVariant object to be added.
     * @param quantity      the quantity of the BookVariant object to be added.
     * @return A ResponseEntity containing a CartItem objects and an HTTP status code.
     */
    @Operation(summary = "Reader adds book to cart.")
    @PostMapping("/add")
    @RequiresRoles(PredefinedRole.READER_ROLE)
    public ResponseEntity<CartItem> addBookToCart(@RequestParam int bookVariantId,
                                                  @RequestParam @Positive int quantity) {
        String username = AuthUtil.getAuthenticatedUsername();
        CartItem savedCartItem = cartService.addCartItem(bookVariantId, quantity, username);
        return new ResponseEntity<>(savedCartItem, HttpStatus.CREATED);
    }

    /**
     * Delete a CartItem by Reader id and BookVariant id.
     *
     * @param bookVariantId the id of the BookVariant object to be deleted.
     * @return a ResponseEntity containing an HTTP status code.
     */
    @Operation(summary = "Delete CartItem by Reader id and BookVariant id.")
    @DeleteMapping("/remove")
    @RequiresRoles(PredefinedRole.READER_ROLE)
    public ResponseEntity<HttpStatus> deleteBookInCart(@RequestParam("bookVariantId") int bookVariantId) {
        String username = AuthUtil.getAuthenticatedUsername();
        cartService.removeByReaderIdAndBookVariantId(username, bookVariantId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
