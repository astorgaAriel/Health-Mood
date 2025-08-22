package cl.healthmood.Health.Mood.service;

import cl.healthmood.Health.Mood.dto.PostRequest;
import cl.healthmood.Health.Mood.dto.PostResponse;
import cl.healthmood.Health.Mood.mapper.PostMapper;
import cl.healthmood.Health.Mood.model.Post;
import cl.healthmood.Health.Mood.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Override
    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        log.debug("Obteniendo todos los posts");
        return postRepository.findAll().stream()
                .map(postMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PostResponse> getPostById(Integer id) {
        log.debug("Buscando post por ID: {}", id);
        return postRepository.findById(id)
                .map(postMapper::toResponse);
    }

    @Override
    @Transactional
    public PostResponse savePost(PostRequest postRequest) {
        log.debug("Guardando nuevo post: {}", postRequest.getTitle());
        validatePostRequest(postRequest);

        Post post = postMapper.toEntity(postRequest);
        Post savedPost = postRepository.save(post);
        return postMapper.toResponse(savedPost);
    }

    @Override
    @Transactional
    public PostResponse updatePost(Integer id, PostRequest postRequest) {
        log.debug("Actualizando post ID: {}", id);
        validatePostRequest(postRequest);

        return postRepository.findById(id)
                .map(existingPost -> {
                    updatePostFields(existingPost, postRequest);
                    Post updatedPost = postRepository.save(existingPost);
                    return postMapper.toResponse(updatedPost);
                })
                .orElseThrow(() -> new RuntimeException("Post no encontrado con ID: " + id));
    }

    @Override
    @Transactional
    public void deletePost(Integer id) {
        log.debug("Eliminando post ID: {}", id);
        
        if (!postRepository.existsById(id)) {
            throw new RuntimeException("Post no encontrado con ID: " + id);
        }
        
        postRepository.deleteById(id);
    }

    private void validatePostRequest(PostRequest postRequest) {
        if (postRequest.getTitle() == null || postRequest.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("El título del post es obligatorio");
        }

        if (postRequest.getContent() == null || postRequest.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("El contenido del post es obligatorio");
        }

        if (postRequest.getAuthor() == null || postRequest.getAuthor().trim().isEmpty()) {
            throw new IllegalArgumentException("El autor del post es obligatorio");
        }

        if (postRequest.getPublishedDate() == null) {
            throw new IllegalArgumentException("La fecha de publicación es obligatoria");
        }
    }

    private void updatePostFields(Post existingPost, PostRequest postRequest) {
        if (postRequest.getTitle() != null && !postRequest.getTitle().trim().isEmpty()) {
            existingPost.setTitle(postRequest.getTitle().trim());
        }

        if (postRequest.getContent() != null && !postRequest.getContent().trim().isEmpty()) {
            existingPost.setContent(postRequest.getContent());
        }

        if (postRequest.getAuthor() != null && !postRequest.getAuthor().trim().isEmpty()) {
            existingPost.setAuthor(postRequest.getAuthor().trim());
        }

        if (postRequest.getPublishedDate() != null) {
            existingPost.setPublishedDate(postRequest.getPublishedDate());
        }
    }
}