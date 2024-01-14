package com.totalhubsite.backend.apitest;

import lombok.Builder;

public record TestRequestDto(
    String content
) {

    @Builder
    public TestRequestDto(String content) {
        this.content = content;
    }
}
