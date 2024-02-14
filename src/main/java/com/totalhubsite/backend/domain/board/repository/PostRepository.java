package com.totalhubsite.backend.domain.board.repository;

import com.totalhubsite.backend.domain.board.entity.Post;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p join fetch p.comments c join fetch c.member where p.id = :postId")
    Optional<Post> findWithCommentsById(@Param("postId") Long postId);

    Page<Post> findByBoardId(Long boardId, Pageable pageable);

}
