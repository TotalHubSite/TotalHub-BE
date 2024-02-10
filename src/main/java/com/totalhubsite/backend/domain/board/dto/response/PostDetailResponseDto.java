package com.totalhubsite.backend.domain.board.dto.response;

import com.totalhubsite.backend.domain.board.entity.Comment;
import com.totalhubsite.backend.domain.board.entity.Post;
import com.totalhubsite.backend.domain.member.dto.response.MemberInfoResponseDto;
import com.totalhubsite.backend.domain.member.entity.Member;
import java.util.List;
import lombok.Builder;

public record PostDetailResponseDto(
    Long id,
    String title,
    String content,
    MemberInfoResponseDto member,
    List<CommentDetailResponseDto> comments
) {

    @Builder
    public PostDetailResponseDto(Long id, String title, String content,
        MemberInfoResponseDto member,
        List<CommentDetailResponseDto> comments) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.member = member;
        this.comments = comments;
    }

    public static PostDetailResponseDto fromEntity(Post entity) {
        Member member = entity.getMember();
        MemberInfoResponseDto memberInfo = MemberInfoResponseDto.fromEntity(member);

        List<Comment> Comments = entity.getComments();
        List<CommentDetailResponseDto> CommentDtos = Comments.stream()
                            .map(CommentDetailResponseDto::fromEntity)
                            .toList();

        return PostDetailResponseDto.builder()
            .id(entity.getId())
            .title(entity.getTitle())
            .content(entity.getContent())
            .member(memberInfo)
            .comments(CommentDtos)
            .build();
    }

}
