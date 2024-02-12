package com.totalhubsite.backend.domain.board.repository.board;

import com.totalhubsite.backend.domain.board.dto.response.BoardDetailResponseDto;

public interface BoardCustomRepository {

    BoardDetailResponseDto findBoardDetails(Long boardId);

}
