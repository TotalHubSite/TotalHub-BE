package com.totalhubsite.backend.domain.board.dto.request;

import com.totalhubsite.backend.domain.board.entity.Board;
import lombok.Builder;

public record BoardRequestDto(
    String name,
    String description
) {

    @Builder
    public BoardRequestDto(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Board toEntity() {
        return Board.builder()
            .name(this.name)
            .description(this.description)
            .build();
    }

}
