package cl.healthmood.Health.Mood.repository;

import cl.healthmood.Health.Mood.model.Customer;
import cl.healthmood.Health.Mood.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    
    // Buscar pedidos por customer
    List<Pedido> findByCustomer(Customer customer);
    
    // Buscar pedidos por customer ID
    List<Pedido> findByCustomerCustomerId(Integer customerId);
}