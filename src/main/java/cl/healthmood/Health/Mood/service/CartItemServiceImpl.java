package cl.healthmood.Health.Mood.service;

import cl.healthmood.Health.Mood.dto.CartItemRequest;
import cl.healthmood.Health.Mood.dto.CartItemResponse;
import cl.healthmood.Health.Mood.mapper.CartItemMapper;
import cl.healthmood.Health.Mood.model.CartItem;
import cl.healthmood.Health.Mood.repository.CartItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;

    public CartItemServiceImpl(CartItemRepository cartItemRepository, CartItemMapper cartItemMapper) {
        this.cartItemRepository = cartItemRepository;
        this.cartItemMapper = cartItemMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CartItemResponse> getAllCartItems() {
        log.debug("Obteniendo todos los cart items");
        List<CartItem> cartItems = cartItemRepository.findAll();
        return cartItemMapper.toResponseList(cartItems);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CartItemResponse> getCartItemsByCartId(Integer cartId) {
        log.debug("Obteniendo cart items para cartId: {}", cartId);
        List<CartItem> cartItems = cartItemRepository.findByCartId(cartId);
        return cartItemMapper.toResponseList(cartItems);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CartItemResponse> getCartItemById(Integer id) {
        log.debug("Buscando cart item por ID: {}", id);
        return cartItemRepository.findById(id)
                .map(cartItemMapper::toResponse);
    }

    @Override
    @Transactional
    public CartItemResponse saveCartItem(CartItemRequest cartItemRequest) {
        log.debug("Guardando cart item para cartId: {}", cartItemRequest.getCartId());
        validateCartItemRequest(cartItemRequest);

        CartItem cartItem = cartItemMapper.toEntity(cartItemRequest);
        
        // Validar cantidad mínima
        if (cartItem.getQuantity() == null || cartItem.getQuantity() < 1) {
            cartItem.setQuantity(1);
        }

        CartItem savedCartItem = cartItemRepository.save(cartItem);
        return cartItemMapper.toResponse(savedCartItem);
    }

    @Override
    @Transactional
    public CartItemResponse updateCartItem(Integer id, CartItemRequest cartItemRequest) {
        log.debug("Actualizando cart item ID: {}", id);
        
        CartItem existingCartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart item no encontrado con ID: " + id));

        validateCartItemRequest(cartItemRequest);
        
        // Actualizar los campos
        cartItemMapper.updateEntityFromRequest(cartItemRequest, existingCartItem);
        
        // Validar cantidad mínima
        if (existingCartItem.getQuantity() == null || existingCartItem.getQuantity() < 1) {
            existingCartItem.setQuantity(1);
        }

        CartItem updatedCartItem = cartItemRepository.save(existingCartItem);
        return cartItemMapper.toResponse(updatedCartItem);
    }

    @Override
    @Transactional
    public void deleteCartItem(Integer id) {
        log.debug("Eliminando cart item ID: {}", id);
        if (!cartItemRepository.existsById(id)) {
            throw new RuntimeException("Cart item no encontrado con ID: " + id);
        }
        cartItemRepository.deleteById(id);
    }

    private void validateCartItemRequest(CartItemRequest cartItemRequest) {
        if (cartItemRequest.getCartId() == null) {
            throw new IllegalArgumentException("Cart ID es obligatorio");
        }
        if (cartItemRequest.getProductId() == null) {
            throw new IllegalArgumentException("Product ID es obligatorio");
        }
        if (cartItemRequest.getQuantity() == null || cartItemRequest.getQuantity() < 1) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }
    }
}