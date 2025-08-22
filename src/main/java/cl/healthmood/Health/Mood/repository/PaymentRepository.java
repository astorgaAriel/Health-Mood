package cl.healthmood.Health.Mood.repository;

import cl.healthmood.Health.Mood.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

}