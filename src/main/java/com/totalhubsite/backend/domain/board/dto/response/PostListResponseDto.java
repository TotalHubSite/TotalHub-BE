package com.totalhubsite.backend.domain.board.dto.response;

import com.totalhubsite.backend.domain.board.entity.Post;
import com.totalhubsite.backend.domain.member.dto.response.MemberInfoResponseDto;
import com.totalhubsite.backend.domain.member.entity.Member;
import lombok.Builder;

public record PostListResponseDto(
    Long id,
    String title,
    String content,
    MemberInfoResponseDto member
) {

    @Builder
    public PostListResponseDto(Long id, String title, String content,
        MemberInfoResponseDto member) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.member = member;
    }


    public static PostListResponseDto fromEntity(Post entity) {
        Member member = entity.getMember();
        MemberInfoResponseDto memberInfo = MemberInfoResponseDto.fromEntity(member);

        return PostListResponseDto.builder()
            .id(entity.getId())
            .title(entity.getTitle())
            .content(entity.getContent())
            .member(memberInfo)
            .build();
    }

}
