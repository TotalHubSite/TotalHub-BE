package com.totalhubsite.backend.domain.board.service;

import com.totalhubsite.backend.domain.board.dto.request.BoardRequestDto;
import com.totalhubsite.backend.domain.board.dto.response.BoardDetailResponseDto;
import com.totalhubsite.backend.domain.board.dto.response.BoardListResponseDto;
import com.totalhubsite.backend.domain.board.entity.Board;
import com.totalhubsite.backend.domain.board.exception.BoardNotFoundException;
import com.totalhubsite.backend.domain.board.repository.BoardRepository;
import com.totalhubsite.backend.domain.member.entity.Member;
import com.totalhubsite.backend.domain.member.exception.PermissionDeniedException;
import com.totalhubsite.backend.global.security.dto.PrincipalDetails;
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
    public BoardDetailResponseDto addBoard(PrincipalDetails principalDetails, BoardRequestDto requestDto) {

        if(principalDetails == null) { throw new PermissionDeniedException(); }

        Member member = principalDetails.getMember();

        Board requestBoard = requestDto.toEntity(member);
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
    public BoardDetailResponseDto modifyBoard(PrincipalDetails principalDetails, Long boardId, BoardRequestDto requestDto) {

        Board findBoard = findBoardById(boardId);

        if(principalDetails == null || !findBoard.getMember().equals(principalDetails.getMember())) {
            throw new PermissionDeniedException();
        }

        findBoard.update(requestDto);
        BoardDetailResponseDto responseDto = BoardDetailResponseDto.fromEntity(findBoard);

        return responseDto;
    }

    @Override @Transactional
    public void removeBoard(PrincipalDetails principalDetails, Long boardId) {
        Board findBoard = findBoardById(boardId);

        if(principalDetails == null || !findBoard.getMember().equals(principalDetails.getMember())) {
            throw new PermissionDeniedException();
        }

        boardRepository.delete(findBoard);
    }

    private Board findBoardById(Long boardId) {
        Board findBoard = boardRepository.findById(boardId)
                    .orElseThrow(BoardNotFoundException::new);

        return findBoard;
    }


}
