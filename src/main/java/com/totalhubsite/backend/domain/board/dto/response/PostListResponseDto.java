package com.totalhubsite.backend.domain.board.dto.response;

import com.totalhubsite.backend.domain.board.entity.Post;
import lombok.Builder;

public record PostListResponseDto(
    Long id,
    String title,
    String content
) {

    @Builder
    public PostListResponseDto(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public static PostListResponseDto fromEntity(Post entity) {
        return PostListResponseDto.builder()
            .id(entity.getId())
            .title(entity.getTitle())
            .content(entity.getContent())
            .build();
    }

}
