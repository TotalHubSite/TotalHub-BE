package com.totalhubsite.backend.domain.board.service;

import com.totalhubsite.backend.domain.board.dto.request.PostRequestDto;
import com.totalhubsite.backend.domain.board.dto.response.PostDetailResponseDto;
import com.totalhubsite.backend.domain.board.dto.response.PostListResponseDto;
import com.totalhubsite.backend.global.security.dto.PrincipalDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

    PostDetailResponseDto addPost(PrincipalDetails principalDetails, Long boardId, PostRequestDto requestDto);

    PostDetailResponseDto findPost(Long postId);

    Page<PostListResponseDto> findPostList(Pageable pageable, Long boardId);

    PostDetailResponseDto modifyPost(PrincipalDetails principalDetails, Long postId, PostRequestDto requestDto);

    void removePost(PrincipalDetails principalDetails, Long postId);
}
