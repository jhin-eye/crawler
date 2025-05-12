package com.yanoos.global.entity.member;

import com.yanoos.global.entity.board.Board;
import jakarta.persistence.*;

/*

create table map_member_board(
	map_member_board_id bigserial primary key,
	member_id bigint not null,
	board_id bigint not null,
	constraint fk_map_member_board_member_id foreign key(member_id) references "member"(member_id)
	ON DELETE CASCADE
	ON UPDATE CASCADE,
	constraint fk_map_member_board_board_id foreign key(board_id) references board(board_id)
	ON DELETE CASCADE
	ON UPDATE CASCADE,
	constraint uk_map_member_board_member_id_board_id unique (member_id,board_id)
);
 */
@Entity
@Table(name = "map_member_board")
public class MapMemberBoard {
    @Id
    @Column(name = "map_member_board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;
}
