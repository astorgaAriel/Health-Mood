package cl.healthmood.Health.Mood.controller;

import cl.healthmood.Health.Mood.dto.PostRequest;
import cl.healthmood.Health.Mood.dto.PostResponse;
import cl.healthmood.Health.Mood.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Validated
@Slf4j
@CrossOrigin(origins = "*")
public class PostController {

    private final PostService postService;

    // Todos pueden ver los posts (sin restricción de autenticación)
    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        log.info("Getting all posts");
        List<PostResponse> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    // Todos pueden ver un post específico (sin restricción de autenticación)
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Integer id) {
        log.info("Getting post by ID: {}", id);
        return postService.getPostById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Solo ADMIN puede crear posts
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody PostRequest postRequest) {
        log.info("Creating new post: {}", postRequest.getTitle());
        try {
            PostResponse savedPost = postService.savePost(postRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPost);
        } catch (IllegalArgumentException e) {
            log.error("Validation error creating post: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    // Solo ADMIN puede actualizar posts
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostResponse> updatePost(@PathVariable Integer id, @Valid @RequestBody PostRequest postRequest) {
        log.info("Updating post ID: {}", id);
        try {
            PostResponse updatedPost = postService.updatePost(id, postRequest);
            return ResponseEntity.ok(updatedPost);
        } catch (IllegalArgumentException e) {
            log.error("Validation error updating post: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            log.error("Post not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    // Solo ADMIN puede eliminar posts
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePost(@PathVariable Integer id) {
        log.info("Deleting post ID: {}", id);
        try {
            postService.deletePost(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.error("Post not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }
}