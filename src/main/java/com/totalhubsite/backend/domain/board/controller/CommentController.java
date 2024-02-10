package com.totalhubsite.backend.domain.board.controller;

import com.totalhubsite.backend.domain.board.dto.request.CommentRequestDto;
import com.totalhubsite.backend.domain.board.dto.response.CommentDetailResponseDto;
import com.totalhubsite.backend.domain.board.service.CommentService;
import com.totalhubsite.backend.global.security.dto.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{post_id}/comments")
    public ResponseEntity<CommentDetailResponseDto> commentAdd(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PathVariable("post_id") Long postId,
        @RequestBody CommentRequestDto requestDto
    ) {

        CommentDetailResponseDto responseDto = commentService.addComment(principalDetails, postId, requestDto);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(responseDto);
    }

    @GetMapping("/comments/{commentId}")
    public ResponseEntity<CommentDetailResponseDto> commentDetails(
        @PathVariable Long commentId
    ) {
        CommentDetailResponseDto responseDto = commentService.findComment(commentId);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(responseDto);
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentDetailResponseDto> commentModify(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PathVariable Long commentId,
        @RequestBody CommentRequestDto requestDto
    ) {
        CommentDetailResponseDto responseDto = commentService.modifyComment(principalDetails, commentId, requestDto);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(responseDto);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> commentRemove(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PathVariable Long commentId
    ) {
        commentService.removeComment(principalDetails, commentId);

        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(null);
    }

}


