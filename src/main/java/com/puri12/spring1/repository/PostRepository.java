package com.puri12.spring1.repository;

import com.puri12.spring1.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Object> findAllByOrderByCreatedAtDesc();
    List<Object> findAllByCategory(String category);

}
