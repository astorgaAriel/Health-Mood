package cl.healthmood.Health.Mood.service;

import cl.healthmood.Health.Mood.dto.PedidoRequest;
import cl.healthmood.Health.Mood.dto.PedidoResponse;
import cl.healthmood.Health.Mood.mapper.PedidoMapper;
import cl.healthmood.Health.Mood.model.Customer;
import cl.healthmood.Health.Mood.model.Pedido;
import cl.healthmood.Health.Mood.repository.CustomerRepository;
import cl.healthmood.Health.Mood.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final CustomerRepository customerRepository;
    private final PedidoMapper pedidoMapper;

    public PedidoServiceImpl(PedidoRepository pedidoRepository,
                             CustomerRepository customerRepository,
                             PedidoMapper pedidoMapper) {
        this.pedidoRepository = pedidoRepository;
        this.customerRepository = customerRepository;
        this.pedidoMapper = pedidoMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoResponse> getAllPedidos() {
        return pedidoRepository.findAll()
                .stream()
                .map(pedidoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PedidoResponse getPedidoById(Integer id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + id));
        return pedidoMapper.toResponse(pedido);
    }

    @Override
    public PedidoResponse createPedido(PedidoRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + request.getCustomerId()));

        Pedido pedido = pedidoMapper.toEntity(request, customer);
        Pedido savedPedido = pedidoRepository.save(pedido);
        return pedidoMapper.toResponse(savedPedido);
    }

    @Override
    public PedidoResponse updatePedido(Integer id, PedidoRequest request) {
        Pedido existingPedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + id));

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + request.getCustomerId()));

        existingPedido.setCustomer(customer);
        existingPedido.setOrderStatus(request.getOrderStatus());
        existingPedido.setOrderDate(request.getOrderDate());
        existingPedido.setRequiredDate(request.getRequiredDate());
        existingPedido.setShippedDate(request.getShippedDate());

        Pedido updatedPedido = pedidoRepository.save(existingPedido);
        return pedidoMapper.toResponse(updatedPedido);
    }

    @Override
    public void deletePedido(Integer id) {
        if (!pedidoRepository.existsById(id)) {
            throw new RuntimeException("Pedido no encontrado con ID: " + id);
        }
        pedidoRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public PedidoResponse getPedidoByIdAndCustomerEmail(Integer id, String customerEmail) {
        Customer customer = customerRepository.findByEmail(customerEmail)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con email: " + customerEmail));
        
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + id));
        
        // Verificar que el pedido pertenezca al customer
        if (!pedido.getCustomer().getCustomerId().equals(customer.getCustomerId())) {
            throw new RuntimeException("No tienes permiso para ver este pedido");
        }
        
        return pedidoMapper.toResponse(pedido);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoResponse> getPedidosByCustomerEmail(String customerEmail) {
        Customer customer = customerRepository.findByEmail(customerEmail)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con email: " + customerEmail));
        
        return pedidoRepository.findByCustomer(customer)
                .stream()
                .map(pedidoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PedidoResponse createPedidoForCustomer(PedidoRequest request, String customerEmail) {
        Customer customer = customerRepository.findByEmail(customerEmail)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con email: " + customerEmail));

        // Asegurar que el pedido se cree para el customer autenticado
        PedidoRequest customerRequest = PedidoRequest.builder()
                .customerId(customer.getCustomerId())
                .orderStatus(request.getOrderStatus())
                .orderDate(request.getOrderDate())
                .requiredDate(request.getRequiredDate())
                .shippedDate(request.getShippedDate())
                .build();

        Pedido pedido = pedidoMapper.toEntity(customerRequest, customer);
        Pedido savedPedido = pedidoRepository.save(pedido);
        return pedidoMapper.toResponse(savedPedido);
    }

    @Override
    public PedidoResponse updatePedidoForCustomer(Integer id, PedidoRequest request, String customerEmail) {
        Customer customer = customerRepository.findByEmail(customerEmail)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con email: " + customerEmail));
        
        Pedido existingPedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + id));

        // Verificar que el pedido pertenezca al customer
        if (!existingPedido.getCustomer().getCustomerId().equals(customer.getCustomerId())) {
            throw new RuntimeException("No tienes permiso para modificar este pedido");
        }

        existingPedido.setOrderStatus(request.getOrderStatus());
        existingPedido.setOrderDate(request.getOrderDate());
        existingPedido.setRequiredDate(request.getRequiredDate());
        existingPedido.setShippedDate(request.getShippedDate());

        Pedido updatedPedido = pedidoRepository.save(existingPedido);
        return pedidoMapper.toResponse(updatedPedido);
    }
}