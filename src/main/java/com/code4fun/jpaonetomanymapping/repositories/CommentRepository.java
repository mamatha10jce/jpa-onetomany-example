package com.code4fun.jpaonetomanymapping.repositories;

import com.code4fun.jpaonetomanymapping.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
    Page<Comment> findByPostId(UUID postId, Pageable pageable);

    Optional<Comment> findByIdAndPostId(UUID id, UUID postId);
}
