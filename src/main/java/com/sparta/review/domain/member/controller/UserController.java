package com.sparta.review.domain.member.controller;

import com.sparta.review.domain.member.dto.UserRequestDto;
import com.sparta.review.domain.member.service.UserService;
import com.sparta.review.global.error.Message;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final MessageSource messageSource;

    @PostMapping("/auth/signup")
    public Message<Message> signup(@RequestBody @Valid UserRequestDto.SignupRequestDto requestDto, BindingResult bindingResult) {
        // Validation 예외처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if(fieldErrors.size() > 0) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
            throw new IllegalArgumentException(messageSource.getMessage(
                    "fail.signup",
                    null,
                    "회원가입에 실패하였습니다",
                    Locale.getDefault()
            ));
        }
        return userService.signup(requestDto).getBody();
    }

    @PutMapping("/auth/update")
    public String update(){
        return "myPage";
    }
}