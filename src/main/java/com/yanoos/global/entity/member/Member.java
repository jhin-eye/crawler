package com.yanoos.global.entity.member;

import com.yanoos.global.entity.dto.MemberDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_email", nullable = false, unique = true)
    private String email;

    @Column(name = "member_nickname", nullable = false, unique = true)
    private String nickname;

    @Column(name="member_role", nullable = false)
    private String role;

    @Column(name="telegram_authentication_uuid", unique = true)
    private UUID telegramAuthenticationUuid;

    @Column(name="telegram_authentication_uuid_created_at")
    private LocalDateTime telegramUuidCreatedAt;

    @Column(name="is_approved", nullable = false)
    private boolean isApproved;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MemberOAuth> memberOAuths = new ArrayList<>();

    @OneToMany(mappedBy="member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Keyword> keywords = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MapMemberTelegramUser> mapMemberTelegramUsers = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MapMemberPost> mapMemberPosts = new ArrayList<>();

    public void generateTelegramUuid(UUID uuid) {
        this.telegramAuthenticationUuid = uuid;
        this.telegramUuidCreatedAt = LocalDateTime.now();
    }


    // Getter, Setter
    public MemberDto toDto(){
        return MemberDto.builder()
                .memberId(this.id)
                .memberEmail(this.email)
                .memberNickname(this.nickname)
                .build();
    }

    public void addMapMemberPost(MapMemberPost mapMemberPost) {
        this.mapMemberPosts.add(mapMemberPost);
    }
}