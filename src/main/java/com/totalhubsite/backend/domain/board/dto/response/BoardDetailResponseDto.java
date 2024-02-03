package com.totalhubsite.backend.domain.board.dto.response;

import com.totalhubsite.backend.domain.board.entity.Board;
import lombok.Builder;

public record BoardDetailResponseDto(
    Long id,
    String name,
    String description
) {

    @Builder
    public BoardDetailResponseDto(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public static BoardDetailResponseDto fromEntity(Board entity) {
        return BoardDetailResponseDto.builder()
            .id(entity.getId())
            .name(entity.getName())
            .description(entity.getDescription())
            .build();
    }

}
