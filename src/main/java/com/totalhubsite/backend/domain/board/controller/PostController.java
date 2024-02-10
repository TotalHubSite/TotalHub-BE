package com.totalhubsite.backend.domain.board.controller;

import com.totalhubsite.backend.domain.board.dto.request.PostRequestDto;
import com.totalhubsite.backend.domain.board.dto.response.PostDetailResponseDto;
import com.totalhubsite.backend.domain.board.dto.response.PostListResponseDto;
import com.totalhubsite.backend.domain.board.service.PostService;
import com.totalhubsite.backend.global.security.dto.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("/v1") // posts
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/boards/{board_id}/posts")
    public ResponseEntity<PostDetailResponseDto> postAdd(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PathVariable("board_id") Long boardId,
        @RequestBody PostRequestDto requestDto
    ) {
        PostDetailResponseDto responseDto = postService.addPost(principalDetails, boardId, requestDto);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(responseDto);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDetailResponseDto> postDetails(
        @PathVariable Long postId
    ) {
        PostDetailResponseDto responseDto = postService.findPost(postId);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(responseDto);
    }

    @GetMapping("/posts/list/{boardId}")
    public ResponseEntity<Page<PostListResponseDto>> postList(
        Pageable pageable,
        @PathVariable Long boardId
    ) {
        Page<PostListResponseDto> responseDtos = postService.findPostList(pageable, boardId);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(responseDtos);
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDetailResponseDto> postModify(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PathVariable Long postId,
        @RequestBody PostRequestDto requestDto
    ) {
        PostDetailResponseDto responseDto = postService.modifyPost(principalDetails, postId, requestDto);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(responseDto);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Void> postRemove(
        @AuthenticationPrincipal PrincipalDetails principalDetails,
        @PathVariable Long postId
    ) {
        postService.removePost(principalDetails, postId);

        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(null);
    }

}
