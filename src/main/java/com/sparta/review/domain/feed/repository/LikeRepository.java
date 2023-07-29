package com.sparta.review.domain.feed.repository;

import com.sparta.review.domain.feed.entity.Comment;
import com.sparta.review.domain.feed.entity.Like;
import com.sparta.review.domain.feed.entity.Post;
import com.sparta.review.domain.member.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndPost(User user, Post post);

    Optional<Like> findByUserAndComment(User user, Comment comment);
}
