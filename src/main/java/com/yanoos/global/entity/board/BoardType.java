package com.yanoos.global.entity.board;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/*
create table board_type(
	board_type_id bigserial primary key,
	board_type_name varchar(255) unique not null,
	board_type_description varchar(255) null
);
 */
@Entity
@Getter
public class BoardType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_type_id")
    private Long id;

    @Column(name = "board_type_name", unique = true, nullable = false)
    private String name;

    @Column(name = "board_type_description")
    private String description;

    @OneToMany(mappedBy = "type", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Board> boards = new ArrayList<>();

}
