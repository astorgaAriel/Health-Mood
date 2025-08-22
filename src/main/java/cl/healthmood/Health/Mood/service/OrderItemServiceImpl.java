package cl.healthmood.Health.Mood.service;

import cl.healthmood.Health.Mood.model.OrderItem;
import cl.healthmood.Health.Mood.repository.OrderItemRepository;
import cl.healthmood.Health.Mood.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Override
    public List<OrderItem> findAll() {
        return orderItemRepository.findAll();
    }

    @Override
    public Optional<OrderItem> findById(Integer id) {
        return orderItemRepository.findById(id);
    }

    @Override
    public OrderItem save(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Override
    public void deleteById(Integer id) {
        orderItemRepository.deleteById(id);
    }
}