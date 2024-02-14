package com.totalhubsite.backend.domain.board.repository.board;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.totalhubsite.backend.domain.board.dto.response.BoardDetailResponseDto;
import com.totalhubsite.backend.domain.board.entity.QBoard;
import com.totalhubsite.backend.domain.board.entity.QComment;
import com.totalhubsite.backend.domain.board.entity.QPost;
import com.totalhubsite.backend.domain.member.dto.response.MemberInfoResponseDto;
import jakarta.persistence.EntityManager;

public class BoardCustomRepositoryImpl implements BoardCustomRepository{

    private final JPAQueryFactory queryFactory;

    public BoardCustomRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public BoardDetailResponseDto findBoardDetails(Long boardId) {

        QBoard qBoard = QBoard.board;
        QPost qPost = QPost.post;
        QComment qComment = QComment.comment;

        // 한번의 요청으로 처리
        BoardDetailResponseDto responseDto  = queryFactory
            .select(Projections.constructor(BoardDetailResponseDto.class,
                qBoard.id,
                qBoard.name,
                qBoard.description,
                JPAExpressions.select(qPost.count()).from(qPost).where(qPost.board.id.eq(boardId)),
                JPAExpressions.select(qComment.count()).from(qComment).where(qComment.post.board.id.eq(boardId)),
                Projections.constructor(MemberInfoResponseDto.class, qBoard.member.id, qBoard.member.nickname)
            ))
            .from(qBoard)
            .where(qBoard.id.eq(boardId))
            .fetchOne();

        return responseDto;
    }
}
