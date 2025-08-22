package cl.healthmood.Health.Mood.repository;

import cl.healthmood.Health.Mood.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findByPostPostId(Integer postId);

    List<Comment> findByCustomerCustomerId(Integer customerId);

    List<Comment> findByCommentDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT c FROM Comment c WHERE c.post.postId = :postId ORDER BY c.commentDate DESC")
    List<Comment> findByPostOrderByDateDesc(@Param("postId") Integer postId);

    @Query("SELECT c FROM Comment c WHERE c.commentText LIKE %:keyword%")
    List<Comment> searchByKeyword(@Param("keyword") String keyword);

    @Query("SELECT COUNT(c) FROM Comment c WHERE c.customer.customerId = :customerId")
    Long countCommentsByCustomerId(@Param("customerId") Integer customerId);

}