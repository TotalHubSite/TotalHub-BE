package com.totalhubsite.backend.domain.board.entity;

import com.totalhubsite.backend.domain.board.dto.request.BoardRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(length = 100)
    private String name;
    @Column(length = 1000)
    private String description;

    @OneToMany(mappedBy = "board")
    private List<Post> posts = new ArrayList<>();

    @Builder
    public Board(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public void update(BoardRequestDto requestDto) {
        this.name = requestDto.name();
        this.description = requestDto.description();
    }

}
