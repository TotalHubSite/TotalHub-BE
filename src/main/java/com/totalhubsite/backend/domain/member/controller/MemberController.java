package com.totalhubsite.backend.domain.member.controller;

import com.totalhubsite.backend.domain.member.dto.request.SignUpRequestDto;
import com.totalhubsite.backend.domain.member.dto.response.SingUpResponseDto;
import com.totalhubsite.backend.domain.member.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/v1/signup")
    public ResponseEntity<SingUpResponseDto> singup(
        @RequestBody SignUpRequestDto requestDto
    ) {
        SingUpResponseDto responseDto = memberService.signup(requestDto);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(responseDto);
    }

}
