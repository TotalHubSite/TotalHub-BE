package com.totalhubsite.backend.domain.board.dto.response;

import com.totalhubsite.backend.domain.board.entity.Comment;
import com.totalhubsite.backend.domain.member.dto.response.MemberInfoResponseDto;
import com.totalhubsite.backend.domain.member.entity.Member;
import lombok.Builder;

public record CommentDetailResponseDto(
    Long id,
    String content,
    MemberInfoResponseDto member
) {

    @Builder
    public CommentDetailResponseDto(Long id, String content, MemberInfoResponseDto member) {
        this.id = id;
        this.content = content;
        this.member = member;
    }

    public static CommentDetailResponseDto fromEntity(Comment entity) {
        Member findMember = entity.getMember();
        MemberInfoResponseDto memberInfo = MemberInfoResponseDto.fromEntity(findMember);

        return CommentDetailResponseDto.builder()
            .id(entity.getId())
            .content(entity.getContent())
            .member(memberInfo)
            .build();
    }

}
