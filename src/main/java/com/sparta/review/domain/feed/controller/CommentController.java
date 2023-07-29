package com.sparta.review.domain.feed.controller;

import com.sparta.review.domain.feed.dto.CommentRequestDto;
import com.sparta.review.domain.feed.dto.CommentResponseDto;
import com.sparta.review.global.security.UserDetailsImpl;
import com.sparta.review.domain.feed.service.CommentService;
import com.sparta.review.global.error.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor

public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments/{postId}")
    public CommentResponseDto addComment(@PathVariable Long postId, @RequestBody CommentRequestDto requestDto,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        return commentService.addComment(postId, requestDto, userDetails.getUser());
    }

    @PutMapping("/comments/{commentId}")
    public CommentResponseDto updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto requestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        return commentService.updateComment(commentId, requestDto, userDetails.getUser());
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Message> deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.deleteComment(commentId, userDetails.getUser());
    }
    @PostMapping("/likes/{commentId}")
    public ResponseEntity<Message> likeBoard (@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.likeComment(commentId, userDetails.getUser());
    }

    @DeleteMapping("/likes/{commentId}")
    public ResponseEntity<Message> deleteLikeBoard (@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.deleteLikeComment(commentId, userDetails.getUser());
    }
}
