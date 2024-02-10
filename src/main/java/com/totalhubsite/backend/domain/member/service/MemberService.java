package com.totalhubsite.backend.domain.member.service;

import com.totalhubsite.backend.domain.member.dto.request.SignUpRequestDto;
import com.totalhubsite.backend.domain.member.dto.response.SingUpResponseDto;

public interface MemberService {

    SingUpResponseDto signup(SignUpRequestDto requestDto);
}
