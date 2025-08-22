package cl.healthmood.Health.Mood.mapper;

import cl.healthmood.Health.Mood.dto.CartItemRequest;
import cl.healthmood.Health.Mood.dto.CartItemResponse;
import cl.healthmood.Health.Mood.model.CartItem;
import cl.healthmood.Health.Mood.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartItemMapper {

    public CartItem toEntity(CartItemRequest cartItemRequest) {
        if (cartItemRequest == null) {
            return null;
        }

        return CartItem.builder()
                .cartId(cartItemRequest.getCartId())
                .product(Product.builder().productId(cartItemRequest.getProductId()).build())
                .quantity(cartItemRequest.getQuantity())
                .build();
    }

    public CartItemResponse toResponse(CartItem cartItem) {
        if (cartItem == null) {
            return null;
        }

        Integer totalPrice = null;
        if (cartItem.getProduct() != null && cartItem.getProduct().getPrice() != null && cartItem.getQuantity() != null) {
            totalPrice = cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }

        return CartItemResponse.builder()
                .cartItemId(cartItem.getCartItemId())
                .cartId(cartItem.getCartId())
                .productId(cartItem.getProduct() != null ? cartItem.getProduct().getProductId() : null)
                .productName(cartItem.getProduct() != null ? cartItem.getProduct().getName() : null)
                .productPrice(cartItem.getProduct() != null ? cartItem.getProduct().getPrice() : null)
                .quantity(cartItem.getQuantity())
                .totalPrice(totalPrice)
                .build();
    }

    public List<CartItemResponse> toResponseList(List<CartItem> cartItems) {
        if (cartItems == null) {
            return null;
        }
        return cartItems.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void updateEntityFromRequest(CartItemRequest cartItemRequest, CartItem cartItem) {
        if (cartItemRequest != null && cartItem != null) {
            cartItem.setQuantity(cartItemRequest.getQuantity());
        }
    }
}
