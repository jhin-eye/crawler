package com.yanoos.global.entity.dto;


import com.yanoos.global.entity.board.BoardType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardDto {
    private Long id;

    private String nameEng;

    private String nameKor;

    private String className;

    private String url;

    private BoardType type;
}
