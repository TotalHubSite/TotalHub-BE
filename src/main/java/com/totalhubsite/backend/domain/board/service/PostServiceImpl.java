package com.totalhubsite.backend.domain.board.service;

import com.totalhubsite.backend.domain.board.dto.request.PostRequestDto;
import com.totalhubsite.backend.domain.board.dto.response.CommentDetailResponseDto;
import com.totalhubsite.backend.domain.board.dto.response.PostDetailResponseDto;
import com.totalhubsite.backend.domain.board.dto.response.PostListResponseDto;
import com.totalhubsite.backend.domain.board.entity.Board;
import com.totalhubsite.backend.domain.board.entity.Comment;
import com.totalhubsite.backend.domain.board.entity.Post;
import com.totalhubsite.backend.domain.board.exception.BoardNotFoundException;
import com.totalhubsite.backend.domain.board.repository.BoardRepository;
import com.totalhubsite.backend.domain.board.repository.PostRepository;
import com.totalhubsite.backend.domain.member.entity.Member;
import com.totalhubsite.backend.domain.member.exception.PermissionDeniedException;
import com.totalhubsite.backend.global.security.dto.PrincipalDetails;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final BoardRepository boardRepository;
    private final PostRepository postRepository;


    @Override @Transactional
    public PostDetailResponseDto addPost(PrincipalDetails principalDetails, Long boardId, PostRequestDto requestDto) {

        if(principalDetails == null) {
            throw new PermissionDeniedException();
        }

        Board findBoard = boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);

        Post requestPost = requestDto.toEntity(findBoard, principalDetails.getMember());
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

        Page<Post> findPosts = postRepository.findByBoardId(boardId, pageable);
        Page<PostListResponseDto> responseDtos = findPosts.map(PostListResponseDto::fromEntity);

        return responseDtos;
    }

    @Override @Transactional
    public PostDetailResponseDto modifyPost(PrincipalDetails principalDetails, Long postId, PostRequestDto requestDto) {

        Post findPost = findPostById(postId);

        if(principalDetails == null || !findPost.getMember().equals(principalDetails.getMember())) {
            throw new PermissionDeniedException();
        }

        findPost.update(requestDto);
        PostDetailResponseDto responseDto = PostDetailResponseDto.fromEntity(findPost);

        return responseDto;
    }

    @Override @Transactional
    public void removePost(PrincipalDetails principalDetails, Long postId) {
        Post findPost = findPostById(postId);

        if(principalDetails == null || !findPost.getMember().equals(principalDetails.getMember())) {
            throw new PermissionDeniedException();
        }

        postRepository.delete(findPost);
    }


    private Post findPostById(Long postId) {
        Post findPost = postRepository.findById(postId)
            .orElseThrow(); // TODO: 예외추가
        return findPost;
    }

}
