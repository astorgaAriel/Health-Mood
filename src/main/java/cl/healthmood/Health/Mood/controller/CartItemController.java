package cl.healthmood.Health.Mood.controller;

import cl.healthmood.Health.Mood.dto.CartItemRequest;
import cl.healthmood.Health.Mood.dto.CartItemResponse;
import cl.healthmood.Health.Mood.service.CartItemService;
import cl.healthmood.Health.Mood.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/cart-items")
@RequiredArgsConstructor
@Validated
@Slf4j
public class CartItemController {

    private final CartItemService cartItemService;
    private final CustomerService customerService;

    // Solo ADMIN puede ver todos los cart items
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CartItemResponse>> getAllCartItems() {
        List<CartItemResponse> cartItems = cartItemService.getAllCartItems();
        return ResponseEntity.ok(cartItems);
    }

    // Customer solo puede ver sus propios cart items por cartId
    @GetMapping("/cart/{cartId}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ResponseEntity<List<CartItemResponse>> getCartItemsByCartId(@PathVariable Integer cartId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = auth.getName();
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

        // Si no es admin, verificar que esté accediendo a su propio carrito
        if (!isAdmin) {
            var currentCustomer = customerService.findByEmail(currentUserEmail).orElse(null);
            if (currentCustomer == null || !currentCustomer.getCustomerId().equals(cartId)) {
                log.warn("Customer {} intentó acceder al carrito {}, pero no es propietario", currentUserEmail, cartId);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }

        List<CartItemResponse> cartItems = cartItemService.getCartItemsByCartId(cartId);
        return ResponseEntity.ok(cartItems);
    }

    // Obtener cart items del customer actual
    @GetMapping("/my-cart")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ResponseEntity<List<CartItemResponse>> getMyCartItems() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = auth.getName();
        
        var currentCustomer = customerService.findByEmail(currentUserEmail).orElse(null);
        if (currentCustomer == null) {
            return ResponseEntity.notFound().build();
        }

        // Usar el customerId como cartId (asumiendo que cada customer tiene un carrito)
        List<CartItemResponse> cartItems = cartItemService.getCartItemsByCartId(currentCustomer.getCustomerId());
        return ResponseEntity.ok(cartItems);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ResponseEntity<CartItemResponse> getCartItemById(@PathVariable Integer id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = auth.getName();
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

        var cartItemOpt = cartItemService.getCartItemById(id);
        if (cartItemOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        CartItemResponse cartItem = cartItemOpt.get();

        // Si no es admin, verificar que el cart item pertenezca al customer
        if (!isAdmin) {
            var currentCustomer = customerService.findByEmail(currentUserEmail).orElse(null);
            if (currentCustomer == null || !currentCustomer.getCustomerId().equals(cartItem.getCartId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }

        return ResponseEntity.ok(cartItem);
    }

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ResponseEntity<CartItemResponse> createCartItem(@Valid @RequestBody CartItemRequest cartItemRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = auth.getName();
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

        // Si no es admin, verificar que esté creando un item para su propio carrito
        if (!isAdmin) {
            var currentCustomer = customerService.findByEmail(currentUserEmail).orElse(null);
            if (currentCustomer == null || !currentCustomer.getCustomerId().equals(cartItemRequest.getCartId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .build();
            }
        }

        CartItemResponse savedCartItem = cartItemService.saveCartItem(cartItemRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCartItem);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ResponseEntity<CartItemResponse> updateCartItem(
            @PathVariable Integer id, 
            @Valid @RequestBody CartItemRequest cartItemRequest) {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = auth.getName();
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

        var existingCartItemOpt = cartItemService.getCartItemById(id);
        if (existingCartItemOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        CartItemResponse existingCartItem = existingCartItemOpt.get();

        // Si no es admin, verificar que el cart item pertenezca al customer
        if (!isAdmin) {
            var currentCustomer = customerService.findByEmail(currentUserEmail).orElse(null);
            if (currentCustomer == null || 
                !currentCustomer.getCustomerId().equals(existingCartItem.getCartId()) ||
                !currentCustomer.getCustomerId().equals(cartItemRequest.getCartId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }

        CartItemResponse updatedCartItem = cartItemService.updateCartItem(id, cartItemRequest);
        return ResponseEntity.ok(updatedCartItem);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Integer id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = auth.getName();
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

        var cartItemOpt = cartItemService.getCartItemById(id);
        if (cartItemOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        CartItemResponse cartItem = cartItemOpt.get();

        // Si no es admin, verificar que el cart item pertenezca al customer
        if (!isAdmin) {
            var currentCustomer = customerService.findByEmail(currentUserEmail).orElse(null);
            if (currentCustomer == null || !currentCustomer.getCustomerId().equals(cartItem.getCartId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }

        cartItemService.deleteCartItem(id);
        return ResponseEntity.noContent().build();
    }
}