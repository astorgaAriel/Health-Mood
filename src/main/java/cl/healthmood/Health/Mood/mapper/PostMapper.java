package cl.healthmood.Health.Mood.mapper;

import cl.healthmood.Health.Mood.dto.PostRequest;
import cl.healthmood.Health.Mood.dto.PostResponse;
import cl.healthmood.Health.Mood.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostMapper {

    @Autowired
    private CommentMapper commentMapper;

    public Post toEntity(PostRequest postRequest) {
        if (postRequest == null) {
            return null;
        }

        return Post.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .author(postRequest.getAuthor())
                .publishedDate(postRequest.getPublishedDate())
                .build();
    }

    public PostResponse toResponse(Post post) {
        if (post == null) {
            return null;
        }

        return PostResponse.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .publishedDate(post.getPublishedDate())
                .comments(post.getComments() != null ?
                        post.getComments().stream()
                                .map(commentMapper::toResponse)
                                .collect(Collectors.toList()) : null)
                .build();
    }

    public List<PostResponse> toResponseList(List<Post> posts) {
        if (posts == null) {
            return null;
        }
        return posts.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void updateEntityFromRequest(PostRequest postRequest, Post post) {
        if (postRequest != null && post != null) {
            post.setTitle(postRequest.getTitle());
            post.setContent(postRequest.getContent());
            post.setAuthor(postRequest.getAuthor());
            post.setPublishedDate(postRequest.getPublishedDate());
        }
    }
}
