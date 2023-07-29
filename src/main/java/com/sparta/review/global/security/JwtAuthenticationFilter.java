package com.sparta.review.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.review.domain.member.dto.UserRequestDto;
import com.sparta.review.domain.member.entity.UserRoleEnum;
import com.sparta.review.global.util.JwtUtil;
import com.sparta.review.global.error.Message;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserRequestDto.LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), UserRequestDto.LoginRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getNickname(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        String nickname = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();

        String token = jwtUtil.createToken(nickname, role);
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);
        response.setCharacterEncoding("UTF-8");

        // 로그인 성공 메시지를 클라이언트로 반환
        String successMessage = "로그인 성공!";
        Message message = new Message(successMessage, HttpServletResponse.SC_OK, null);

        // 메시지를 JSON 형식으로 변환하여 클라이언트에게 전송
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(new ObjectMapper().writeValueAsString(message));
        out.flush();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        log.info("로그인 실패");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            String json = new ObjectMapper().writeValueAsString(new Message("회원을 찾을 수 없습니다.", HttpServletResponse.SC_BAD_REQUEST, null));
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
