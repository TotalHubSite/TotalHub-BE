package com.totalhubsite.backend.domain.board.service;

import com.totalhubsite.backend.domain.board.dto.request.BoardRequestDto;
import com.totalhubsite.backend.domain.board.dto.response.BoardDetailResponseDto;
import com.totalhubsite.backend.domain.board.dto.response.BoardListResponseDto;
import com.totalhubsite.backend.domain.board.entity.Board;
import com.totalhubsite.backend.domain.board.exception.BoardNotFoundException;
import com.totalhubsite.backend.domain.board.repository.BoardRepository;
import java.nio.BufferOverflowException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Getter
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;

    @Override @Transactional
    public BoardDetailResponseDto addBoard(BoardRequestDto requestDto) {

        Board requestBoard = requestDto.toEntity();
        Board savedBoard = boardRepository.save(requestBoard);
        BoardDetailResponseDto responseDto = BoardDetailResponseDto.fromEntity(savedBoard);

        return responseDto;
    }

    @Override @Transactional(readOnly = true)
    public BoardDetailResponseDto findBoard(Long boardId) {

        Board findBoard = findBoardById(boardId);

        BoardDetailResponseDto responseDto = BoardDetailResponseDto.fromEntity(findBoard);

        return responseDto;
    }

    @Override @Transactional(readOnly = true)
    public Page<BoardListResponseDto> findBoardList(Pageable pageable) {

        Page<Board> findBoards = boardRepository.findAll(pageable);

        Page<BoardListResponseDto> responseDtos = findBoards.map(BoardListResponseDto::fromEntity);

        return responseDtos;
    }

    @Override @Transactional
    public BoardDetailResponseDto modifyBoard(Long boardId, BoardRequestDto requestDto) {

        Board findBoard = findBoardById(boardId);
        findBoard.update(requestDto);
        BoardDetailResponseDto responseDto = BoardDetailResponseDto.fromEntity(findBoard);

        return responseDto;
    }

    @Override @Transactional
    public void removeBoard(Long boardId) {
        Board findBoard = findBoardById(boardId);
        boardRepository.delete(findBoard);
    }

    private Board findBoardById(Long boardId) {
        Board findBoard = boardRepository.findById(boardId)
                    .orElseThrow(BufferOverflowException::new);

        return findBoard;
    }


}
