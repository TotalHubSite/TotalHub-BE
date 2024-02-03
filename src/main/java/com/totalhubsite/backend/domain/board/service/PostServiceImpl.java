package com.totalhubsite.backend.domain.board.service;

import com.totalhubsite.backend.domain.board.dto.request.PostRequestDto;
import com.totalhubsite.backend.domain.board.dto.response.PostDetailResponseDto;
import com.totalhubsite.backend.domain.board.dto.response.PostListResponseDto;
import com.totalhubsite.backend.domain.board.entity.Post;
import com.totalhubsite.backend.domain.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;


    @Override @Transactional
    public PostDetailResponseDto addPost(PostRequestDto requestDto) {

        Post requestPost = requestDto.toEntity();
        Post savedPost = postRepository.save(requestPost);
        PostDetailResponseDto responseDto = PostDetailResponseDto.fromEntity(savedPost);

        return responseDto;
    }

    @Override @Transactional(readOnly = true)
    public PostDetailResponseDto findPost(Long postId) {

        Post findPost = findPostById(postId);
        PostDetailResponseDto responseDto = PostDetailResponseDto.fromEntity(findPost);

        return responseDto;
    }

    @Override @Transactional(readOnly = true)
    public Page<PostListResponseDto> findPostList(Pageable pageable, Long boardId) {

        Page<Post> findPosts = postRepository.findAll(pageable);
        Page<PostListResponseDto> responseDtos = findPosts.map(PostListResponseDto::fromEntity);

        return responseDtos;
    }

    @Override @Transactional
    public PostDetailResponseDto modifyPost(Long postId, PostRequestDto requestDto) {

        Post findPost = findPostById(postId);
        findPost.update(requestDto);
        PostDetailResponseDto responseDto = PostDetailResponseDto.fromEntity(findPost);

        return responseDto;
    }

    @Override @Transactional
    public void removePost(Long postId) {
        Post findPost = findPostById(postId);
        postRepository.delete(findPost);
    }


    private Post findPostById(Long postId) {
        Post findPost = postRepository.findById(postId)
            .orElseThrow(); // TODO: 예외추가
        return findPost;
    }

}
