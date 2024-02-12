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

        // 각 정보에 대한 요청
//        Board board = queryFactory.selectFrom(qBoard)
//            .where(qBoard.id.eq(boardId))
//            .fetchOne();
//
//        Long postCount = queryFactory.select(qPost.count())
//            .from(qPost)
//            .where(qPost.board.id.eq(boardId))
//            .fetchOne();
//
//        Long commentCount = queryFactory.select(qComment.count())
//            .from(qComment)
//            .where(qComment.post.board.id.eq(boardId))
//            .fetchOne();
//
//        BoardDetailResponseDto responseDto = BoardDetailResponseDto.fromEntity(board, postCount, commentCount);

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
