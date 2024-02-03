package com.totalhubsite.backend.domain.board.service;

import com.totalhubsite.backend.domain.board.dto.request.PostRequestDto;
import com.totalhubsite.backend.domain.board.dto.response.PostDetailResponseDto;
import com.totalhubsite.backend.domain.board.dto.response.PostListResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

    PostDetailResponseDto addPost(PostRequestDto requestDto);

    PostDetailResponseDto findPost(Long postId);

    Page<PostListResponseDto> findPostList(Pageable pageable, Long boardId);

    PostDetailResponseDto modifyPost(Long postId, PostRequestDto requestDto);

    void removePost(Long postId);
}
