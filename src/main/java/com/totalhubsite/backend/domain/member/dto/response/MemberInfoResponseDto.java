package com.totalhubsite.backend.domain.member.dto.response;

import com.totalhubsite.backend.domain.member.entity.Member;
import lombok.Builder;

public record MemberInfoResponseDto(
    Long memberId,
    String nickname
) {

    @Builder
    public MemberInfoResponseDto(Long memberId, String nickname) {
        this.memberId = memberId;
        this.nickname = nickname;
    }

    public static MemberInfoResponseDto fromEntity(Member entity) {
        return MemberInfoResponseDto.builder()
            .memberId(entity.getId())
            .nickname(entity.getNickname())
            .build();
    }

}
