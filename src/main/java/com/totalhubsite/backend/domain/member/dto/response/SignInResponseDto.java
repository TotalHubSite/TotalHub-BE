package com.totalhubsite.backend.domain.member.dto.response;

import com.totalhubsite.backend.domain.member.entity.Member;
import com.totalhubsite.backend.domain.member.entity.Role;
import lombok.Builder;

public record SignInResponseDto(
    String email,
    String nickname,
    Role role,
    String token
) {

    @Builder
    public SignInResponseDto(String email, String nickname, Role role, String token) {
        this.email = email;
        this.nickname = nickname;
        this.role = role;
        this.token = token;
    }


    public static SignInResponseDto fromEntity(Member member, String token) {
        return SignInResponseDto.builder()
            .email(member.getEmail())
            .nickname(member.getNickname())
            .role(member.getRole())
            .token(token)
            .build();

    }
}
