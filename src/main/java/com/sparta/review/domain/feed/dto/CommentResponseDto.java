package com.sparta.review.domain.feed.dto;

import com.sparta.review.domain.feed.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long id;
    private String nickname;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private int likeCount;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.nickname = comment.getUser().getNickname();
        this.comment = comment.getComment();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
        this.likeCount = comment.getLikeCount();
    }
}
