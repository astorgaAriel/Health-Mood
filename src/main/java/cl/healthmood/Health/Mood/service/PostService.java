package cl.healthmood.Health.Mood.service;

import cl.healthmood.Health.Mood.dto.PostRequest;
import cl.healthmood.Health.Mood.dto.PostResponse;

import java.util.List;
import java.util.Optional;

public interface PostService {
    List<PostResponse> getAllPosts();
    Optional<PostResponse> getPostById(Integer id);
    PostResponse savePost(PostRequest postRequest);
    PostResponse updatePost(Integer id, PostRequest postRequest);
    void deletePost(Integer id);
}