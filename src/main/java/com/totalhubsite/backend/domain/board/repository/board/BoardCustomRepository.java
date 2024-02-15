package com.totalhubsite.backend.domain.board.repository.board;

import com.totalhubsite.backend.domain.board.dto.response.BoardDetailResponseDto;
import com.totalhubsite.backend.domain.board.dto.response.BoardListResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardCustomRepository {

    BoardDetailResponseDto findBoardDetails(Long boardId);

    Page<BoardListResponseDto> findBoardList(Pageable pageable);

}
