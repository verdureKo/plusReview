package com.sparta.review.domain.member.service;

import com.sparta.review.domain.member.dto.UserRequestDto;
import com.sparta.review.domain.member.entity.User;
import com.sparta.review.domain.member.entity.UserRoleEnum;
import com.sparta.review.domain.member.repository.UserRepository;
import com.sparta.review.global.error.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MessageSource messageSource;


    // ADMIN_TOKEN
    private final String ADMIN_TOKEN = "PuReumSuJungHyunJungDongGyuJangWon";

    public ResponseEntity<Message> signup(UserRequestDto.SignupRequestDto requestDto) {
        String nickname = requestDto.getNickname();
        String password = requestDto.getPassword(); // 수정님 알려주신 패스워드 비교 후 암호화 로직선택
        String confirmPassword = requestDto.getConfirmPassword(); // 비밀번호 확인용 값
        String email = requestDto.getEmail();
        String introduction = requestDto.getIntroduction();

        // 회원 중복 확인
        if (userRepository.existsByNickname(nickname)) {
            throw new IllegalArgumentException(messageSource.getMessage("duplication.nickname", null, Locale.getDefault()));    // message.properties 작성하여 예외 메세지 관리
        }

        if (password.contains(nickname)) {
            throw new IllegalArgumentException(messageSource.getMessage("cannot.equals.nickname", null, Locale.getDefault()));
        }

        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException(messageSource.getMessage("not.matches.pw", null, Locale.getDefault()));
        }

        // 암호화된 패스워드 생성
        String encryptedPassword = passwordEncoder.encode(password);

        // 회원 권한 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new IllegalArgumentException(messageSource.getMessage("not.admin.token", null, Locale.getDefault()));
            }
            role = UserRoleEnum.ADMIN;
        }

        // 사용자 등록
        User user = new User(nickname, encryptedPassword, email, introduction, role);
        userRepository.save(user);

        String msg = messageSource.getMessage("signup.success", null, Locale.getDefault());
        Message message = new Message(msg, HttpStatus.OK.value(), null);

        // ResponseEntity로 응답
        return ResponseEntity.ok(message);
    }
}
