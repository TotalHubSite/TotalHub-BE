package com.totalhubsite.backend.domain.board.dto.response;

import com.totalhubsite.backend.domain.board.entity.Board;
import com.totalhubsite.backend.domain.member.dto.response.MemberInfoResponseDto;
import com.totalhubsite.backend.domain.member.entity.Member;
import lombok.Builder;

public record BoardDetailResponseDto(
    Long id,
    String name,
    String description,
    Integer postCount,
    Integer commentCount,
    MemberInfoResponseDto member

) {

    @Builder
    public BoardDetailResponseDto(Long id, String name, String description, Integer postCount,
        Integer commentCount, MemberInfoResponseDto member) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.postCount = postCount;
        this.commentCount = commentCount;
        this.member = member;
    }

    public static BoardDetailResponseDto fromEntity(Board entity) {
        Member findMember = entity.getMember();
        MemberInfoResponseDto memberInfo = MemberInfoResponseDto.fromEntity(findMember);

        return BoardDetailResponseDto.builder()
            .id(entity.getId())
            .name(entity.getName())
            .description(entity.getDescription())
            .postCount(entity.getPostCount())
            .commentCount(entity.getCommentCount())
            .member(memberInfo)
            .build();
    }

    public static BoardDetailResponseDto fromEntity(Board entity, Integer postCount, Integer commentCount) {
        Member findMember = entity.getMember();
        MemberInfoResponseDto memberInfo = MemberInfoResponseDto.fromEntity(findMember);

        return BoardDetailResponseDto.builder()
            .id(entity.getId())
            .name(entity.getName())
            .description(entity.getDescription())
            .postCount(postCount)
            .commentCount(commentCount)
            .member(memberInfo)
            .build();
    }

}
