package com.sparta.review.domain.feed.dto;

import com.sparta.review.domain.feed.entity.Comment;
import com.sparta.review.domain.feed.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PostResponseDto {
    // param 형식으로 값을 넘겨주려고 할 때에는 @Setter와 @NoArgsConstructor를 넣어주어야함
    // @JsonInclude(JsonInclude.NON_NULL) null값이 아닌 애들만 반환을 하겠다는 것
    @Getter
    public static class PostBasicResponseDto{
        private Long id;
        private String title;
        private String nickname;
        private String content;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;

        public PostBasicResponseDto(Post post){
            this.id = post.getId();
            this.title = post.getTitle();
            this.nickname = post.getNickname();
            this.content = post.getContent();
            this.createdAt = post.getCreatedAt();
            this.modifiedAt = post.getModifiedAt();
        }
    }

    @Getter
    public static class PostReadResponseDto {
        private String title;
        private String nickname;
        private String contents;
        private LocalDateTime createdAt;
        private int likeCount;

        private List<CommentResponseDto> commentList  = new ArrayList<>();

        public PostReadResponseDto(Post post){
            this.title = post.getTitle();
            this.nickname = post.getNickname();
            this.contents = post.getContent();
            this.createdAt = post.getCreatedAt();
            this.likeCount = post.getLikeCount();

            for(Comment comment : post.getCommentList()) {
                commentList.add(new CommentResponseDto(comment));
            }
        }
    }
}
