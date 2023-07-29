package com.sparta.review.domain.feed.entity;

import com.sparta.review.domain.feed.dto.PostRequestDto;
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
@Table(name="posts")
@NoArgsConstructor
public class Post extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String nickname;
    @Column(nullable = false)
    private String content;
    @Column(name = "likes", nullable = false)
    private int likeCount=0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    @OrderBy("id desc")
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Like> likes = new ArrayList<>();

    public Post(PostRequestDto requestDto, User user){
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.nickname = user.getNickname();
        this.user = user;
    }

    public void update(PostRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }

    public void increaseLikeCount(){
        this.likeCount++;
    }

    public void decreaseLikeCount(){
        this.likeCount--;
    }
}
