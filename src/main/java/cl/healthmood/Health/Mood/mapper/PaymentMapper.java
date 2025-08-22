package cl.healthmood.Health.Mood.mapper;

import cl.healthmood.Health.Mood.dto.PaymentRequest;
import cl.healthmood.Health.Mood.dto.PaymentResponse;
import cl.healthmood.Health.Mood.model.Customer;
import cl.healthmood.Health.Mood.model.Payment;
import cl.healthmood.Health.Mood.model.Pedido;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PaymentMapper {

    public Payment toEntity(PaymentRequest paymentRequest) {
        if (paymentRequest == null) {
            return null;
        }

        return Payment.builder()
                .customer(Customer.builder().customerId(paymentRequest.getCustomerId()).build())
                .pedido(Pedido.builder().pedidoId(paymentRequest.getPedidoId()).build())
                .paymentDate(paymentRequest.getPaymentDate())
                .amount(paymentRequest.getAmount())
                .build();
    }

    public PaymentResponse toResponse(Payment payment) {
        if (payment == null) {
            return null;
        }

        return PaymentResponse.builder()
                .paymentId(payment.getPaymentId())
                .customerId(payment.getCustomer() != null ? payment.getCustomer().getCustomerId() : null)
                .customerName(payment.getCustomer() != null ? 
                        payment.getCustomer().getFirstName() + " " + payment.getCustomer().getLastName() : null)
                .pedidoId(payment.getPedido() != null ? payment.getPedido().getPedidoId() : null)
                .paymentDate(payment.getPaymentDate())
                .amount(payment.getAmount())
                .build();
    }

    public List<PaymentResponse> toResponseList(List<Payment> payments) {
        if (payments == null) {
            return null;
        }
        return payments.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void updateEntityFromRequest(PaymentRequest paymentRequest, Payment payment) {
        if (paymentRequest != null && payment != null) {
            payment.setPaymentDate(paymentRequest.getPaymentDate());
            payment.setAmount(paymentRequest.getAmount());
        }
    }
}
