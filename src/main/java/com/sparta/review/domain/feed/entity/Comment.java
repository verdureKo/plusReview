package com.sparta.review.domain.feed.entity;

import com.sparta.review.domain.feed.dto.CommentRequestDto;
import com.sparta.review.global.time.Timestamped;
import com.sparta.review.domain.member.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="comments")
@NoArgsConstructor
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String comment;

    @Column(name = "likes", nullable = false)
    private int likeCount = 0;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    private List<Like> likes = new ArrayList<>();

    public Comment(CommentRequestDto requestDto, User user, Post post) {
        this.comment = requestDto.getComment();
        this.user = user;
        this.post = post;
    }

    public void update(CommentRequestDto requestDto) {
        this.comment = requestDto.getComment();
    }

    public void increaseLikeCount(){
        this.likeCount++;
    }

    public void decreaseLikeCount(){
        this.likeCount--;
    }
}
