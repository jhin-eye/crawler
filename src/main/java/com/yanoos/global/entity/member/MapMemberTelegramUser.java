package com.yanoos.global.entity.member;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "map_member_telegram_user")
public class MapMemberTelegramUser {

    // 기본 키로 사용할 필드
    @Id
    @Column(name = "telegram_user_id")
    private Long telegramUserId;

    // member 테이블의 외래 키로 사용할 필드
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // Getter, Setter
}