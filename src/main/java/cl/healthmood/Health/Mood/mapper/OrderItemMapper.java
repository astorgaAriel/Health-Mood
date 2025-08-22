package cl.healthmood.Health.Mood.mapper;

import cl.healthmood.Health.Mood.dto.OrderItemRequest;
import cl.healthmood.Health.Mood.dto.OrderItemResponse;
import cl.healthmood.Health.Mood.model.OrderItem;
import cl.healthmood.Health.Mood.model.Pedido;
import cl.healthmood.Health.Mood.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderItemMapper {

    public OrderItem toEntity(OrderItemRequest orderItemRequest) {
        if (orderItemRequest == null) {
            return null;
        }

        return OrderItem.builder()
                .pedido(Pedido.builder().pedidoId(orderItemRequest.getPedidoId()).build())
                .product(Product.builder().productId(orderItemRequest.getProductId()).build())
                .quantity(orderItemRequest.getQuantity())
                .listPrice(orderItemRequest.getListPrice())
                .discount(orderItemRequest.getDiscount() != null ? orderItemRequest.getDiscount() : 0)
                .build();
    }

    public OrderItemResponse toResponse(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        }

        Integer finalPrice = null;
        if (orderItem.getListPrice() != null && orderItem.getDiscount() != null) {
            finalPrice = orderItem.getListPrice() - orderItem.getDiscount();
        }

        return OrderItemResponse.builder()
                .itemId(orderItem.getItemId())
                .pedidoId(orderItem.getPedido() != null ? orderItem.getPedido().getPedidoId() : null)
                .productId(orderItem.getProduct() != null ? orderItem.getProduct().getProductId() : null)
                .productName(orderItem.getProduct() != null ? orderItem.getProduct().getName() : null)
                .quantity(orderItem.getQuantity())
                .listPrice(orderItem.getListPrice())
                .discount(orderItem.getDiscount())
                .finalPrice(finalPrice)
                .build();
    }

    public List<OrderItemResponse> toResponseList(List<OrderItem> orderItems) {
        if (orderItems == null) {
            return null;
        }
        return orderItems.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void updateEntityFromRequest(OrderItemRequest orderItemRequest, OrderItem orderItem) {
        if (orderItemRequest != null && orderItem != null) {
            orderItem.setQuantity(orderItemRequest.getQuantity());
            orderItem.setListPrice(orderItemRequest.getListPrice());
            orderItem.setDiscount(orderItemRequest.getDiscount() != null ? orderItemRequest.getDiscount() : 0);
        }
    }
}
