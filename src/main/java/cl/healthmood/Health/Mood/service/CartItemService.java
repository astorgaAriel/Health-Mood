package cl.healthmood.Health.Mood.service;

import cl.healthmood.Health.Mood.dto.CartItemRequest;
import cl.healthmood.Health.Mood.dto.CartItemResponse;
import java.util.List;
import java.util.Optional;

public interface CartItemService {

    List<CartItemResponse> getAllCartItems();
    
    List<CartItemResponse> getCartItemsByCartId(Integer cartId);

    Optional<CartItemResponse> getCartItemById(Integer id);

    CartItemResponse saveCartItem(CartItemRequest cartItemRequest);
    
    CartItemResponse updateCartItem(Integer id, CartItemRequest cartItemRequest);

    void deleteCartItem(Integer id);

}