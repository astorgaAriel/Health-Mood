package cl.healthmood.Health.Mood.service;

import cl.healthmood.Health.Mood.model.Payment;
import java.util.List;
import java.util.Optional;

public interface PaymentService {

    List<Payment> getAllPayments();

    Optional<Payment> getPaymentById(Integer id);

    Payment savePayment(Payment payment);

    void deletePayment(Integer id);

}