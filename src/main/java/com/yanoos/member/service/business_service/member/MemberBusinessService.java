package com.yanoos.member.service.business_service.member;

import com.yanoos.member.controller.member.GenerateTelegramUuidOut;
import com.yanoos.member.service.entity_service.keyword.KeywordEntityService;
import com.yanoos.member.service.entity_service.map_member_post.MapMemberPostEntityService;
import com.yanoos.member.service.entity_service.member.MemberEntityService;
import com.yanoos.member.service.entity_service.post.PostEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberBusinessService {
    private final MemberEntityService memberEntityService;
    private final KeywordEntityService keywordEntityService;
    private final MapMemberPostEntityService mapMemberPostEntityService;
    private final PostEntityService postEntityService;
    /**
     * 텔레그램 UUID를 생성한다.
     *
     * @param memberId
     * @return
     */
    @Transactional
    public GenerateTelegramUuidOut generateTelegramUuid(Long memberId) {
        UUID uuid = UUID.randomUUID();
        memberEntityService.updateTelegramUuid(memberId, uuid);
        return GenerateTelegramUuidOut.builder()
                .memberId(memberId)
                .uuid(uuid)
                .build();
    }

    //
    // /**
    //  * postTitle에 포함된 키워드를 모니터하는 회원 목록을 조회한다.
    //  *
    //  * @param postTitle
    //  * @return
    //  */
    // public List<Member> findMembersByPostTitle(String postTitle) {
    //     //postTitle에 포함된 키워드 목록 작성
    //     List<Keyword> keywords = keywordEntityService.getKeywordsInPostTitle(postTitle);
    //    //해당 키워드를 모니터하는 회원 목록 작성
    //     List<MapMemberKeyword> mapMemberKeywords = mapMemberKeywordEntityService.getMapMemberKeywordsByKeywordIds(keywords.stream().map(Keyword::getId).toList());
    //     List<Member> members = mapMemberKeywords.stream().map(MapMemberKeyword::getMember).toList();
    //     return members.stream().distinct().toList();
    // }
    //
    // public Member getMemberById(Long memberId) {
    //     return memberEntityService.getMemberByMemberId(memberId);
    // }
    //
    // @Transactional
    // public OutBoxFindPostContainingKeywordsIn mapMembersWithPost(PostCreatedIn postCreatedIn) {
    //     Post post = postEntityService.getPostByPostId(postCreatedIn.getValue().getPostId());
    //     log.info("postCreatedIn: {}", postCreatedIn.toString());
    //     List<Member> members = findMembersByPostTitle(post.getTitle());
    //     members.forEach(member -> {
    //         List<String> containedKeywords = member.getMapMemberKeywords().stream()
    //                 .map(mapMemberKeyword -> mapMemberKeyword.getKeyword().getKeyword()) // 각 MapMemberKeyword에서 키워드를 추출
    //                 .filter(keyword -> post.getTitle().contains(keyword)) // 추출된 키워드가 postTitle에 포함되어 있는지 필터링
    //                 .toList(); // 필터된 키워드를 리스트로 변환
    //
    //         MapMemberPost mapMemberPost = MapMemberPost.builder()
    //                 .member(member)
    //                 .post(post)
    //                 .checked(false)
    //                 .keywords(containedKeywords)
    //                 .build();
    //         member.addMapMemberPost(mapMemberPost);
    //         post.addMapMemberPost(mapMemberPost);
    //         mapMemberPostEntityService.saveMapMemberPost(mapMemberPost);
    //     });
    //
    //     return OutBoxFindPostContainingKeywordsIn.builder()
    //             .members(members)
    //             .postId(post.getId())
    //             .build();
    // }
}
