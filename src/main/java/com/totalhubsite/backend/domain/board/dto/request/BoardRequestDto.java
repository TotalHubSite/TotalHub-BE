package com.totalhubsite.backend.domain.board.dto.request;

import com.totalhubsite.backend.domain.board.entity.Board;
import com.totalhubsite.backend.domain.member.entity.Member;
import lombok.Builder;

public record BoardRequestDto(
    String name,
    String description,
    Member member
) {

    @Builder
    public BoardRequestDto(String name, String description, Member member) {
        this.name = name;
        this.description = description;
        this.member = member;
    }


    public Board toEntity() {
        return Board.builder()
            .name(this.name)
            .description(this.description)
            .build();
    }

    public Board toEntity(Member member) {
        return Board.builder()
            .name(this.name)
            .description(this.description)
            .member(member)
            .build();
    }

}
