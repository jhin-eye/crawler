package com.yanoos.event.dto;

import com.yanoos.global.entity.member.Member;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class OutBoxNewPostIn {
    @Setter
    private Long parentEventId;
    private Long postId;

}
