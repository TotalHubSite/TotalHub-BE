package com.totalhubsite.backend.domain.board.dto.request;

import com.totalhubsite.backend.domain.board.entity.Comment;
import com.totalhubsite.backend.domain.board.entity.Post;
import com.totalhubsite.backend.domain.member.entity.Member;
import lombok.Builder;

public record CommentRequestDto(
    String content
) {

    @Builder
    public CommentRequestDto(String content) {
        this.content = content;
    }

    public Comment toEntity() {
        return Comment.builder()
            .content(this.content)
            .build();
    }

    public Comment toEntity(Member member, Post post) {
        return Comment.builder()
            .content(this.content)
            .member(member)
            .post(post)
            .build();
    }

}
