package com.totalhubsite.backend.domain.board.dto.response;

import com.totalhubsite.backend.domain.board.entity.Board;
import lombok.Builder;

public record BoardListResponseDto(
    Long id,
    String name,
    String description
) {

    @Builder
    public BoardListResponseDto(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public static BoardListResponseDto fromEntity(Board entity) {
        return BoardListResponseDto.builder()
            .id(entity.getId())
            .name(entity.getName())
            .description(entity.getDescription())
            .build();
    }

}
