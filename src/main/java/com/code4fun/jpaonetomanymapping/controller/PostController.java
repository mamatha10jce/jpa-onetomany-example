package com.code4fun.jpaonetomanymapping.controller;

import com.code4fun.jpaonetomanymapping.exception.ResourceNotFoundException;
import com.code4fun.jpaonetomanymapping.model.Post;
import com.code4fun.jpaonetomanymapping.repositories.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@Slf4j
public class PostController {

    @Autowired
    private PostRepository postRepository;

    //Getmapping
    @GetMapping(value = "/posts")
    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    //postmapping
    @PostMapping(value = "/posts")
    public Post createPost(@Valid @RequestBody Post post) {
        return postRepository.save(post);
    }

    //putmapping
    @PutMapping(value = "/posts/{id}")
    public Post updatePost(@Valid @PathVariable UUID postId, @RequestBody Post postRequest) {
        return postRepository.findById(postId).map(
                post -> {
                    post.builder().content(postRequest.getContent())
                            .title(postRequest.getContent())
                            .description(postRequest.getDescription())
                            .build();
                    return postRepository.save(post);
                }).orElseThrow(() -> new ResourceNotFoundException("post id: " + postId));
    }

    //deletemapping
    @DeleteMapping(value = "/posts/{id}")
    public ResponseEntity<?> deletePost(@PathVariable UUID postId) {
        return postRepository.findById(postId).map(post -> {
            postRepository.delete(post);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("PostId " + postId + " not found"));
    }
}
