package com.totalhubsite.backend.domain.board.service;

import com.totalhubsite.backend.domain.board.dto.request.BoardRequestDto;
import com.totalhubsite.backend.domain.board.dto.response.BoardDetailResponseDto;
import com.totalhubsite.backend.domain.board.dto.response.BoardListResponseDto;
import com.totalhubsite.backend.global.security.dto.PrincipalDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardService {

    BoardDetailResponseDto addBoard(PrincipalDetails principalDetails, BoardRequestDto requestDto);

    BoardDetailResponseDto findBoard(Long boardId);

    Page<BoardListResponseDto> findBoardList(Pageable pageable);

    BoardDetailResponseDto modifyBoard(PrincipalDetails principalDetails, Long boardId, BoardRequestDto requestDto);

    void removeBoard(PrincipalDetails principalDetails, Long boardId);
}
