package cl.healthmood.Health.Mood.mapper;

import cl.healthmood.Health.Mood.dto.PedidoRequest;
import cl.healthmood.Health.Mood.dto.PedidoResponse;
import cl.healthmood.Health.Mood.model.Pedido;
import cl.healthmood.Health.Mood.model.Customer;
import cl.healthmood.Health.Mood.model.OrderItem;
import cl.healthmood.Health.Mood.model.Payment;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoMapper {

    public Pedido toEntity(PedidoRequest request, Customer customer) {
        return Pedido.builder()
                .customer(customer)
                .orderStatus(request.getOrderStatus())
                .orderDate(request.getOrderDate())
                .requiredDate(request.getRequiredDate())
                .shippedDate(request.getShippedDate())
                .build();
    }

    public PedidoResponse toResponse(Pedido pedido) {
        return PedidoResponse.builder()
                .pedidoId(pedido.getPedidoId())
                .customerId(pedido.getCustomer() != null ? pedido.getCustomer().getCustomerId() : null)
                .customerName(pedido.getCustomer() != null ?
                        pedido.getCustomer().getFirstName() + " " + pedido.getCustomer().getLastName() : null)
                .orderStatus(pedido.getOrderStatus())
                .orderDate(pedido.getOrderDate())
                .requiredDate(pedido.getRequiredDate())
                .shippedDate(pedido.getShippedDate())
                .orderItems(mapOrderItems(pedido.getOrderItems()))
                .payments(mapPayments(pedido.getPayments()))
                .build();
    }

    private List<PedidoResponse.OrderItemResponse> mapOrderItems(List<OrderItem> orderItems) {
        if (orderItems == null || orderItems.isEmpty()) {
            return Collections.emptyList();
        }
        return orderItems.stream()
                .map(this::mapOrderItem)
                .collect(Collectors.toList());
    }

    private PedidoResponse.OrderItemResponse mapOrderItem(OrderItem item) {
        Integer quantity = item.getQuantity();
        Integer listPrice = item.getListPrice();
        Integer discount = item.getDiscount() != null ? item.getDiscount() : 0;

        // Calcular precio unitario después del descuento
        Double unitPrice = listPrice != null ? listPrice.doubleValue() * (1 - discount / 100.0) : null;
        Double totalPrice = (quantity != null && unitPrice != null) ? quantity * unitPrice : null;

        return PedidoResponse.OrderItemResponse.builder()
                .orderItemId(item.getItemId())
                .productId(item.getProduct() != null ? getProductId(item.getProduct()) : null)
                .productName(item.getProduct() != null ? getProductName(item.getProduct()) : null)
                .quantity(quantity)
                .unitPrice(unitPrice)
                .totalPrice(totalPrice)
                .build();
    }

    private List<PedidoResponse.PaymentResponse> mapPayments(List<Payment> payments) {
        if (payments == null || payments.isEmpty()) {
            return Collections.emptyList();
        }
        return payments.stream()
                .map(this::mapPayment)
                .collect(Collectors.toList());
    }

    private PedidoResponse.PaymentResponse mapPayment(Payment payment) {
        return PedidoResponse.PaymentResponse.builder()
                .paymentId(payment.getPaymentId())
                .paymentMethod("N/A") // Payment no tiene método de pago en tu modelo
                .amount(payment.getAmount() != null ? payment.getAmount().doubleValue() : null)
                .paymentDate(payment.getPaymentDate())
                .status("COMPLETED") // Payment no tiene status en tu modelo, asumimos completado
                .build();
    }

    // Métodos auxiliares para Product - necesitarás ajustar según tu modelo Product
    private Integer getProductId(Object product) {
        try {
            // Asumiendo que Product tiene getProductId() - ajustar según tu modelo
            return (Integer) product.getClass().getMethod("getProductId").invoke(product);
        } catch (Exception e) {
            return null;
        }
    }

    private String getProductName(Object product) {
        try {
            // Asumiendo que Product tiene getProductName() - ajustar según tu modelo
            return (String) product.getClass().getMethod("getProductName").invoke(product);
        } catch (Exception e) {
            return "Producto sin nombre";
        }
    }
}