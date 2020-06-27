package com.code4fun.jpaonetomanymapping.repositories;

import com.code4fun.jpaonetomanymapping.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
}
