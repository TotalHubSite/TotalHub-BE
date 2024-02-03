package com.totalhubsite.backend.domain.board.dto.request;

import com.totalhubsite.backend.domain.board.entity.Post;
import lombok.Builder;

public record PostRequestDto(
    String title,
    String content
) {

    @Builder
    public PostRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Post toEntity() {
        return Post.builder()
            .title(this.title)
            .content(this.content)
            .build();
    }

}
