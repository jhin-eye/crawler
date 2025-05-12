package com.yanoos.member.controller.dto;


import com.yanoos.global.entity.dto.PostDto;
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
    private MemberOut memberOut;
    private PostDto postDto;
    private boolean checked;
    private List<String> keywords;
}
