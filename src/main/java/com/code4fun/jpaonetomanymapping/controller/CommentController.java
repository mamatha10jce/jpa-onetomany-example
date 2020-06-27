package com.code4fun.jpaonetomanymapping.controller;

import com.code4fun.jpaonetomanymapping.exception.ResourceNotFoundException;
import com.code4fun.jpaonetomanymapping.model.Comment;
import com.code4fun.jpaonetomanymapping.repositories.CommentRepository;
import com.code4fun.jpaonetomanymapping.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    //Getmapping by postid
    @GetMapping(value = "/posts/{postId}/comments")
    public Page<Comment> getComments(@PathVariable UUID postId, Pageable pageable) {
        return commentRepository.findByPostId(postId, pageable);
    }

    //postmapping by post id
    @PostMapping(value = "/posts/{postId}/comments")
    public Comment createComment(@PathVariable UUID postId, @Valid @RequestBody Comment comment) {
        return postRepository.findById(postId).map(post -> {
            comment.setPost(post);
            return commentRepository.save(comment);
        }).orElseThrow(() -> new ResourceNotFoundException("PostId " + postId + " not found"));
    }

    //put mapping by post id and comment id
    @PutMapping(value = "/posts/{postId}/comments/{commentId}")
    public Comment updateComments(@PathVariable(value = "postId") UUID postId,
                                  @PathVariable(value = "commentId") UUID commentId,
                                  @Valid @RequestBody Comment commentRequest) {
        if (!postRepository.existsById(postId)) {
            throw new ResourceNotFoundException("Post id : " + postId + " not found");
        }

        return commentRepository.findById(commentId).map(comment -> {
            comment.setText(commentRequest.getText());
            return commentRepository.save(comment);
        }).orElseThrow(() -> new ResourceNotFoundException("commentId: " + commentId));
    }

    //delete by post id and comment id
    @DeleteMapping(value = "/posts/{postId}/comments/{commentId}")
    public ResponseEntity<?> deleteComments(@PathVariable(value = "postId") UUID postId,
                                            @PathVariable(value = "commentId") UUID commentId) {
        return commentRepository.findByIdAndPostId(postId, commentId).map(comment -> {
            commentRepository.delete(comment);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("commentId not found: " + commentId));
    }


}
