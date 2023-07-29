package com.sparta.review.domain.member.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)    // 유일한 삼촌 옵션
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)    // 유일한 삼촌 옵션
    private String email;

    @Column(nullable = false)
    private String introduce;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public User(String nickname, String password, String email, String introduce, UserRoleEnum role) {
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.introduce = introduce;
        this.role = role;
    }
}