package cl.healthmood.Health.Mood.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentRequest {

    @NotNull(message = "Post ID is required")
    private Integer postId;

    @NotNull(message = "Customer ID is required")
    private Integer customerId;

    @NotBlank(message = "Comment text is required")
    private String commentText;

    private LocalDateTime commentDate;
}
