package com.totalhubsite.backend.domain.member.dto.response;

import com.totalhubsite.backend.domain.member.entity.Member;
import lombok.Builder;

public record SingUpResponseDto(
    Long memberId,
    String email,
    String nickname
) {

    @Builder
    public SingUpResponseDto(Long memberId, String email, String nickname) {
        this.memberId = memberId;
        this.email = email;
        this.nickname = nickname;
    }

    public static SingUpResponseDto fromEntity(Member entity) {
        return SingUpResponseDto.builder()
            .memberId(entity.getId())
            .email(entity.getEmail())
            .nickname(entity.getNickname())
            .build();
    }

}
