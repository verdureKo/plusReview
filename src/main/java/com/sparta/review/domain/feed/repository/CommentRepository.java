package com.sparta.review.domain.feed.repository;

import com.sparta.review.domain.feed.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByOrderByCreatedAtDesc();
}
