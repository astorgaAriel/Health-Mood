package cl.healthmood.Health.Mood.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponse {

    private Integer commentId;
    private Integer postId;
    private String postTitle;
    private Integer customerId;
    private String customerName;
    private String commentText;
    private LocalDateTime commentDate;
}
