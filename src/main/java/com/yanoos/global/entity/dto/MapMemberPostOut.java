package com.yanoos.global.entity.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
public class MapMemberPostOut {
    private Long mapMemberPostId;
    private MemberDto memberDto;
    private PostDto postDto;
    private boolean checked;
    private List<String> keywords;
}
