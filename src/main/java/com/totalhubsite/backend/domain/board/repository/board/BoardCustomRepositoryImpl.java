package com.totalhubsite.backend.domain.board.repository.board;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.totalhubsite.backend.domain.board.dto.response.BoardDetailResponseDto;
import com.totalhubsite.backend.domain.board.dto.response.BoardListResponseDto;
import com.totalhubsite.backend.domain.board.entity.QBoard;
import com.totalhubsite.backend.domain.board.entity.QComment;
import com.totalhubsite.backend.domain.board.entity.QPost;
import com.totalhubsite.backend.domain.member.dto.response.MemberInfoResponseDto;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

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

    @Override
    public Page<BoardListResponseDto> findBoardList(Pageable pageable) {

        QBoard qBoard = QBoard.board;
        QPost qPost = QPost.post;
        QComment qComment = QComment.comment;

        List<BoardListResponseDto> responseDtos = queryFactory
            .select(Projections.constructor(BoardListResponseDto.class,
                qBoard.id,
                qBoard.name,
                qBoard.description,
                JPAExpressions.select(qPost.count()).from(qPost).where(qPost.board.id.eq(qBoard.id)),
                JPAExpressions.select(qComment.count()).from(qComment).where(qComment.post.board.id.eq(qBoard.id)),
                Projections.constructor(MemberInfoResponseDto.class, qBoard.member.id, qBoard.member.nickname)
            ))
            .from(qBoard)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        long total = queryFactory
            .selectFrom(qBoard)
            .fetchCount();

        return new PageImpl<>(responseDtos, pageable, total);
    }

}
