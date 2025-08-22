package cl.healthmood.Health.Mood.service;

import cl.healthmood.Health.Mood.model.OrderItem;

import java.util.List;
import java.util.Optional;

public interface OrderItemService {
    List<OrderItem> findAll();
    Optional<OrderItem> findById(Integer id);
    OrderItem save(OrderItem orderItem);
    void deleteById(Integer id);
}