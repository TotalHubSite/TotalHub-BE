package com.totalhubsite.backend.domain.board.dto.response;

import com.totalhubsite.backend.domain.board.entity.Comment;
import lombok.Builder;

public record CommentDetailResponseDto(
    Long id,
    String content
) {

    @Builder
    public CommentDetailResponseDto(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    public static CommentDetailResponseDto fromEntity(Comment entity) {
        return CommentDetailResponseDto.builder()
            .id(entity.getId())
            .content(entity.getContent())
            .build();
    }

}
