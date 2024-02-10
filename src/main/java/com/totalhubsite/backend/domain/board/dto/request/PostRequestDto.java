package com.totalhubsite.backend.domain.board.dto.request;

import com.totalhubsite.backend.domain.board.entity.Board;
import com.totalhubsite.backend.domain.board.entity.Post;
import com.totalhubsite.backend.domain.member.entity.Member;
import lombok.Builder;

public record PostRequestDto(
    String title,
    String content,
    Board board,
    Member member
) {

    @Builder
    public PostRequestDto(String title, String content, Board board, Member member) {
        this.title = title;
        this.content = content;
        this.board = board;
        this.member = member;
    }


    public Post toEntity() {
        return Post.builder()
            .title(this.title)
            .content(this.content)
            .build();
    }

    public Post toEntity(Board board, Member member) {
        return Post.builder()
            .title(this.title)
            .content(this.content)
            .board(board)
            .member(member)
            .build();
    }

}
