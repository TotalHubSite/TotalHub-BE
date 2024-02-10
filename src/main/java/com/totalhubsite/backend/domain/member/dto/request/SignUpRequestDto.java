package com.totalhubsite.backend.domain.member.dto.request;

import com.totalhubsite.backend.domain.member.entity.Member;
import com.totalhubsite.backend.domain.member.entity.Role;
import lombok.Builder;

public record SignUpRequestDto(
    String email,
    String password,
    String nickname,
    String phoneNumber,
    Role role
) {

    @Builder
    public SignUpRequestDto(String email, String password, String nickname, String phoneNumber,
        Role role) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }


    public Member toEntity(String encodedPassword, String nickname) {
        return Member.builder()
            .email(this.email)
            .password(encodedPassword)
            .nickname(this.nickname == null ? nickname : this.nickname)
            .phoneNumber(this.phoneNumber)
            .role(this.role)
            .build();
    }
}
