package com.totalhubsite.backend.domain.board.controller;

import com.totalhubsite.backend.domain.board.dto.request.CommentRequestDto;
import com.totalhubsite.backend.domain.board.dto.response.CommentDetailResponseDto;
import com.totalhubsite.backend.domain.board.service.CommentService;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/v1/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDetailResponseDto> commentAdd(
        @RequestBody CommentRequestDto requestDto
    ) {

        CommentDetailResponseDto responseDto = commentService.addComment(requestDto);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(responseDto);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDetailResponseDto> commentDetails(
        @PathVariable Long commentId
    ) {
        CommentDetailResponseDto responseDto = commentService.findComment(commentId);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(responseDto);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDetailResponseDto> commentModify(
        @PathVariable Long commentId,
        @RequestBody CommentRequestDto requestDto
    ) {
        CommentDetailResponseDto responseDto = commentService.modifyComment(commentId, requestDto);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(responseDto);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> commentRemove(
        @PathVariable Long commentId
    ) {
        commentService.removeComment(commentId);

        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(null);
    }

}


