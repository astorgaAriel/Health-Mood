package cl.healthmood.Health.Mood.mapper;

import cl.healthmood.Health.Mood.dto.CommentRequest;
import cl.healthmood.Health.Mood.dto.CommentResponse;
import cl.healthmood.Health.Mood.model.Comment;
import cl.healthmood.Health.Mood.model.Customer;
import cl.healthmood.Health.Mood.model.Post;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentMapper {

    public Comment toEntity(CommentRequest commentRequest) {
        if (commentRequest == null) {
            return null;
        }

        return Comment.builder()
                .post(Post.builder().postId(commentRequest.getPostId()).build())
                .customer(Customer.builder().customerId(commentRequest.getCustomerId()).build())
                .commentText(commentRequest.getCommentText())
                .commentDate(commentRequest.getCommentDate())
                .build();
    }

    public CommentResponse toResponse(Comment comment) {
        if (comment == null) {
            return null;
        }

        return CommentResponse.builder()
                .commentId(comment.getCommentId())
                .postId(comment.getPost() != null ? comment.getPost().getPostId() : null)
                .postTitle(comment.getPost() != null ? comment.getPost().getTitle() : null)
                .customerId(comment.getCustomer() != null ? comment.getCustomer().getCustomerId() : null)
                .customerName(comment.getCustomer() != null ? 
                        comment.getCustomer().getFirstName() + " " + comment.getCustomer().getLastName() : null)
                .commentText(comment.getCommentText())
                .commentDate(comment.getCommentDate())
                .build();
    }

    public List<CommentResponse> toResponseList(List<Comment> comments) {
        if (comments == null) {
            return null;
        }
        return comments.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void updateEntityFromRequest(CommentRequest commentRequest, Comment comment) {
        if (commentRequest != null && comment != null) {
            comment.setCommentText(commentRequest.getCommentText());
            comment.setCommentDate(commentRequest.getCommentDate());
        }
    }
}
