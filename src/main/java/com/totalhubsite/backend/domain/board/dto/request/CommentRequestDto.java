package com.totalhubsite.backend.domain.board.dto.request;

import com.totalhubsite.backend.domain.board.entity.Comment;
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

}
