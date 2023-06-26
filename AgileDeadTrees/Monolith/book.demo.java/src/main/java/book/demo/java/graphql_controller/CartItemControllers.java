package book.demo.java.graphql_controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import book.demo.java.entity.cart.CartItem;
import book.demo.java.service.CartItemService;
import book.demo.java.util.AuthUtil;

@Controller
public class CartItemControllers {
    @Autowired
    private CartItemService cartItemService;

    public CartItemControllers(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @QueryMapping
    public List<CartItem> getCartItemsByReaderId(@Argument Integer readerId) {
        return cartItemService.getCartItemsByReaderId(readerId);
    }

    @QueryMapping
    public List<CartItem> getCartItemsByReaderUsername(@Argument String username) {
        return cartItemService.getCartItemsByReaderUsername(username);
    }

    @MutationMapping
    public CartItem addCartItems(@Argument Integer bookVariantId, @Argument Integer quantity) {
        String username = AuthUtil.getAuthenticatedUsername();
        return cartItemService.addCartItem(bookVariantId, quantity, username);
    }

    @MutationMapping
    public void removeByReaderUsernameAndBookVariantId(@Argument Integer bookVariantId) {
        String username = AuthUtil.getAuthenticatedUsername();
        cartItemService.removeByReaderUsernameAndBookVariantId(username, bookVariantId);
    }


}