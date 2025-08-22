package cl.healthmood.Health.Mood.repository;

import cl.healthmood.Health.Mood.model.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ContactMessageRepository extends JpaRepository<ContactMessage, Integer> {

    List<ContactMessage> findByEmail(String email);

    List<ContactMessage> findByName(String name);

    List<ContactMessage> findBySubjectContainingIgnoreCase(String subject);

    List<ContactMessage> findBySentAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT cm FROM ContactMessage cm WHERE cm.message LIKE %:keyword% OR cm.subject LIKE %:keyword%")
    List<ContactMessage> searchByKeyword(@Param("keyword") String keyword);

    @Query("SELECT cm FROM ContactMessage cm ORDER BY cm.sentAt DESC")
    List<ContactMessage> findAllOrderBySentAtDesc();

    @Query("SELECT COUNT(cm) FROM ContactMessage cm WHERE DATE(cm.sentAt) = CURRENT_DATE")
    Long countTodayMessages();

}