package com.totalhubsite.backend.domain.board.controller;

import com.totalhubsite.backend.domain.board.dto.request.BoardRequestDto;
import com.totalhubsite.backend.domain.board.dto.response.BoardDetailResponseDto;
import com.totalhubsite.backend.domain.board.dto.response.BoardListResponseDto;
import com.totalhubsite.backend.domain.board.service.BoardService;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<BoardDetailResponseDto> boardAdd(
        @RequestBody BoardRequestDto requestDto
    ) {
        BoardDetailResponseDto responseDto = boardService.addBoard(requestDto);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(responseDto);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardDetailResponseDto> boardDetails(
        @PathVariable Long boardId
    ) {
        BoardDetailResponseDto responseDto = boardService.findBoard(boardId);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(responseDto);
    }

    @GetMapping("/list")
    public ResponseEntity<Page<BoardListResponseDto>> boardList(
        Pageable pageable
    ) {
        Page<BoardListResponseDto> responseDtos = boardService.findBoardList(pageable);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(responseDtos);
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<BoardDetailResponseDto> boardModify(
        @PathVariable Long boardId,
        @RequestBody BoardRequestDto requestDto
    ) {
        BoardDetailResponseDto responseDto = boardService.modifyBoard(boardId, requestDto);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(responseDto);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> boardRemove(
        @PathVariable Long boardId
    ) {
        boardService.removeBoard(boardId);

        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(null);
    }


}
