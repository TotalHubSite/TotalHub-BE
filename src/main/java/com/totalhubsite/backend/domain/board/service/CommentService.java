package com.totalhubsite.backend.domain.board.service;

import com.totalhubsite.backend.domain.board.dto.request.CommentRequestDto;
import com.totalhubsite.backend.domain.board.dto.response.CommentDetailResponseDto;
import com.totalhubsite.backend.global.security.dto.PrincipalDetails;

public interface CommentService {

    CommentDetailResponseDto addComment(PrincipalDetails principalDetails, Long postId, CommentRequestDto requestDto);

    CommentDetailResponseDto findComment(Long commentId);

    CommentDetailResponseDto modifyComment(PrincipalDetails principalDetails, Long commentId, CommentRequestDto requestDto);

    void removeComment(PrincipalDetails principalDetails, Long commentId);
}
