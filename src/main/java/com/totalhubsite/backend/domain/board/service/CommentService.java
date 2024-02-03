package com.totalhubsite.backend.domain.board.service;

import com.totalhubsite.backend.domain.board.dto.request.CommentRequestDto;
import com.totalhubsite.backend.domain.board.dto.response.CommentDetailResponseDto;

public interface CommentService {

    CommentDetailResponseDto addComment(CommentRequestDto requestDto);

    CommentDetailResponseDto findComment(Long commentId);

    CommentDetailResponseDto modifyComment(Long commentId, CommentRequestDto requestDto);

    void removeComment(Long commentId);
}
