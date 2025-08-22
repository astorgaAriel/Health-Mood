package cl.healthmood.Health.Mood.controller;

import cl.healthmood.Health.Mood.dto.PedidoRequest;
import cl.healthmood.Health.Mood.dto.PedidoResponse;
import cl.healthmood.Health.Mood.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
@Validated
@Slf4j
@CrossOrigin(origins = "*")
public class PedidoController {

    private final PedidoService pedidoService;

    // Solo ADMIN puede ver todos los pedidos
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PedidoResponse>> getAllPedidos() {
        log.info("Getting all pedidos (ADMIN only)");
        List<PedidoResponse> pedidos = pedidoService.getAllPedidos();
        return ResponseEntity.ok(pedidos);
    }

    // CUSTOMER puede ver solo sus pedidos, ADMIN puede ver todos
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ResponseEntity<PedidoResponse> getPedidoById(@PathVariable Integer id) {
        log.info("Getting pedido by ID: {}", id);
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = auth.getName();
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        
        try {
            PedidoResponse pedido;
            if (isAdmin) {
                // Admin puede ver cualquier pedido
                pedido = pedidoService.getPedidoById(id);
            } else {
                // Customer solo puede ver sus propios pedidos
                pedido = pedidoService.getPedidoByIdAndCustomerEmail(id, currentUserEmail);
            }
            return ResponseEntity.ok(pedido);
        } catch (RuntimeException e) {
            log.error("Error getting pedido: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para que CUSTOMER vea solo sus pedidos
    @GetMapping("/my-pedidos")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<PedidoResponse>> getMyPedidos() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = auth.getName();
        
        log.info("Getting pedidos for customer: {}", currentUserEmail);
        List<PedidoResponse> pedidos = pedidoService.getPedidosByCustomerEmail(currentUserEmail);
        return ResponseEntity.ok(pedidos);
    }

    // Solo CUSTOMER puede crear pedidos (sus propios pedidos)
    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<PedidoResponse> createPedido(@Valid @RequestBody PedidoRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = auth.getName();
        
        log.info("Creating pedido for customer: {}", currentUserEmail);
        try {
            PedidoResponse createdPedido = pedidoService.createPedidoForCustomer(request, currentUserEmail);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPedido);
        } catch (RuntimeException e) {
            log.error("Error creating pedido: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    // CUSTOMER puede actualizar solo sus pedidos, ADMIN puede actualizar todos
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ResponseEntity<PedidoResponse> updatePedido(@PathVariable Integer id,
                                                       @Valid @RequestBody PedidoRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = auth.getName();
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        
        log.info("Updating pedido ID: {} by user: {}", id, currentUserEmail);
        try {
            PedidoResponse updatedPedido;
            if (isAdmin) {
                // Admin puede actualizar cualquier pedido
                updatedPedido = pedidoService.updatePedido(id, request);
            } else {
                // Customer solo puede actualizar sus propios pedidos
                updatedPedido = pedidoService.updatePedidoForCustomer(id, request, currentUserEmail);
            }
            return ResponseEntity.ok(updatedPedido);
        } catch (RuntimeException e) {
            log.error("Error updating pedido: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    // Solo ADMIN puede eliminar pedidos
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePedido(@PathVariable Integer id) {
        log.info("Deleting pedido ID: {} (ADMIN only)", id);
        try {
            pedidoService.deletePedido(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.error("Error deleting pedido: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}