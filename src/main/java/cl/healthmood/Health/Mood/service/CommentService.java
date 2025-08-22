package cl.healthmood.Health.Mood.service;

import cl.healthmood.Health.Mood.model.Comment;
import java.util.List;
import java.util.Optional;

public interface CommentService {

    List<Comment> getAllComments();

    Optional<Comment> getCommentById(Integer id);

    Comment saveComment(Comment comment);

    void deleteComment(Integer id);

}