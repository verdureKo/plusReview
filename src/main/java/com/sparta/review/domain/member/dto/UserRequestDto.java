package com.sparta.review.domain.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public class UserRequestDto {
    @Setter
    @Getter
    public static class LoginRequestDto {
        @Size(min = 3, max = 10)
        @Pattern(regexp = "^[a-zA-Z0-9]*$")
        @NotBlank(message = "닉네임 입력해주세요")
        private String nickname;

        @Size(min = 4, max = 15)
        @Pattern(regexp = "^[a-zA-Z0-9]*$")
        @NotBlank(message = "비밀번호를 입력해주세요")
        private String password;
    }

    @Data
    public static class SignupRequestDto {
        @Size(min = 3, max = 10)
        @Pattern(regexp = "^[a-zA-Z0-9]*$")
        @NotBlank(message = "닉네임 입력해주세요")
        private String nickname;

        @Size(min = 4, max = 15)
        @Pattern(regexp = "^[a-zA-Z0-9]*$")
        @NotBlank(message = "비밀번호를 입력해주세요")
        private String password;

        @Size(min = 4, max = 15)
        @Pattern(regexp = "^[a-zA-Z0-9]*$")
        @NotBlank(message = "비밀번호 확인을 입력해주세요")
        private String confirmPassword;

        @NotBlank(message = "이메일을 입력해주세요")
        @Email(message = "이메일의 형식이 맞지 않습니다")
        private String email;

        @NotBlank(message = "한 줄 소개를 입력해주세요")
        private String introduction;

        private boolean admin = false;
        private String adminToken = "";
    }

    @Getter
    @AllArgsConstructor
    public static class UserInfoDto {
        String username;
        boolean isAdmin;
    }
}
