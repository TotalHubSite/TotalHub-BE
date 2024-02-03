package com.totalhubsite.backend.domain.board.service;

import com.totalhubsite.backend.domain.board.dto.request.CommentRequestDto;
import com.totalhubsite.backend.domain.board.dto.response.CommentDetailResponseDto;
import com.totalhubsite.backend.domain.board.entity.Comment;
import com.totalhubsite.backend.domain.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;

    @Override @Transactional
    public CommentDetailResponseDto addComment(CommentRequestDto requestDto) {

        Comment requestComment = requestDto.toEntity();
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
    public CommentDetailResponseDto modifyComment(Long commentId, CommentRequestDto requestDto) {
        Comment findComment = findCommentById(commentId);
        findComment.update(requestDto);
        CommentDetailResponseDto responseDto = CommentDetailResponseDto.fromEntity(findComment);

        return responseDto;
    }

    @Override @Transactional
    public void removeComment(Long commentId) {
        Comment findComment = findCommentById(commentId);
        commentRepository.delete(findComment);
    }


    private Comment findCommentById(Long commentId) {
        Comment findComment = commentRepository.findById(commentId)
                                    .orElseThrow(); // TODO: 예외구현
        return findComment;
    }
}
