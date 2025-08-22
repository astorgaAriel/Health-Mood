package cl.healthmood.Health.Mood.repository;

import cl.healthmood.Health.Mood.model.ChatbotLog;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ChatbotLogRepository extends JpaRepository<ChatbotLog, Integer> {

    List<ChatbotLog> findByCustomerCustomerId(Integer customerId);

    List<ChatbotLog> findByTimestampBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT cl FROM ChatbotLog cl WHERE cl.customer.customerId = :customerId ORDER BY cl.timestamp DESC")
    List<ChatbotLog> findByCustomerOrderByTimestampDesc(@Param("customerId") Integer customerId);

    @Query("SELECT COUNT(cl) FROM ChatbotLog cl WHERE cl.customer.customerId = :customerId")
    Long countLogsByCustomerId(@Param("customerId") Integer customerId);

    @Query("SELECT COUNT(cl) FROM ChatbotLog cl WHERE DATE(cl.timestamp) = CURRENT_DATE")
    Long countTodayLogs();
}

