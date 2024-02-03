package com.totalhubsite.backend.domain.board.repository;

import com.totalhubsite.backend.domain.board.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
