package com.totalhubsite.backend.domain.board.service;

import com.totalhubsite.backend.domain.board.dto.request.CommentRequestDto;
import com.totalhubsite.backend.domain.board.dto.response.CommentDetailResponseDto;
import com.totalhubsite.backend.domain.board.entity.Comment;
import com.totalhubsite.backend.domain.board.entity.Post;
import com.totalhubsite.backend.domain.board.repository.CommentRepository;
import com.totalhubsite.backend.domain.board.repository.PostRepository;
import com.totalhubsite.backend.domain.member.exception.PermissionDeniedException;
import com.totalhubsite.backend.global.security.dto.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Override @Transactional
    public CommentDetailResponseDto addComment(PrincipalDetails principalDetails, Long postId, CommentRequestDto requestDto) {

        if(principalDetails == null) { throw new PermissionDeniedException(); }

        Post findPost = postRepository.findById(postId).orElseThrow(); // TODO: 예외처리

        Comment requestComment = requestDto.toEntity(principalDetails.getMember(), findPost);
        Comment savedComment = commentRepository.save(requestComment);
        CommentDetailResponseDto responseDto = CommentDetailResponseDto.fromEntity(savedComment);

        return responseDto;
    }

    @Override @Transactional(readOnly = true)
    public CommentDetailResponseDto findComment(Long commentId) {

        Comment findComment = findCommentById(commentId);
        CommentDetailResponseDto responseDto = CommentDetailResponseDto.fromEntity(findComment);

        return responseDto;
    }

    @Override @Transactional
    public CommentDetailResponseDto modifyComment(PrincipalDetails principalDetails, Long commentId, CommentRequestDto requestDto) {

        Comment findComment = findCommentById(commentId);

        if(principalDetails == null || !findComment.getMember().equals(principalDetails.getMember())) {
            throw new PermissionDeniedException();
        }

        findComment.update(requestDto);
        CommentDetailResponseDto responseDto = CommentDetailResponseDto.fromEntity(findComment);

        return responseDto;
    }

    @Override @Transactional
    public void removeComment(PrincipalDetails principalDetails, Long commentId) {
        Comment findComment = findCommentById(commentId);

        if(principalDetails == null || !findComment.getMember().equals(principalDetails.getMember())) {
            throw new PermissionDeniedException();
        }

        commentRepository.delete(findComment);
    }


    private Comment findCommentById(Long commentId) {
        Comment findComment = commentRepository.findById(commentId)
                                    .orElseThrow(); // TODO: 예외구현
        return findComment;
    }
}
