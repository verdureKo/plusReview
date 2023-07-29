package com.sparta.review.domain.feed.controller;

import com.sparta.review.domain.feed.dto.PostRequestDto;
import com.sparta.review.domain.feed.dto.PostResponseDto;
import com.sparta.review.global.security.UserDetailsImpl;
import com.sparta.review.domain.feed.service.PostService;
import com.sparta.review.global.error.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor

public class PostController {
    private final PostService postService;

    @GetMapping("/posts")
    public List<PostResponseDto.PostReadResponseDto> getPosts(){
        return postService.getPosts();
    }

    @PostMapping("/posts")
    public PostResponseDto.PostBasicResponseDto createPost(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.createPost(requestDto, userDetails.getUser());
    }

    @GetMapping("/posts/{id}")
    public PostResponseDto.PostReadResponseDto getPost(@PathVariable Long id){
        return postService.getPost(id);
    }


    @PutMapping("/posts/{id}")
    public PostResponseDto.PostReadResponseDto updatePost(
            @PathVariable Long id, @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        return postService.updatePost(id, requestDto, userDetails.getUser());
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Message> deletePost(
            @PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        return postService.deletePost(id, userDetails.getUser());
    }

    @PostMapping("/likes/{id}")
    public ResponseEntity<Message> likePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.likePost(id, userDetails.getUser());
    }

    @DeleteMapping("/likes/{id}")
    public ResponseEntity<Message> deleteLikePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.deleteLikePost(id, userDetails.getUser());
    }
}
