package com.totalhubsite.backend.domain.member.dto.request;

public record SignInRequestDto(
    String email,
    String password
) {
}
