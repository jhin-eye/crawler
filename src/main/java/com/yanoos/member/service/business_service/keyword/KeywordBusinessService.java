package com.yanoos.member.service.business_service.keyword;

import com.yanoos.global.exception.BusinessException;
import com.yanoos.global.exception.code.MemberErrorCode;
import com.yanoos.global.util.AuthUtil;
import com.yanoos.member.service.business_service.dto.CreateKeywordIn;
import com.yanoos.member.controller.dto.PostKeywordIn;
import com.yanoos.member.controller.dto.PostKeywordOut;
import com.yanoos.global.entity.member.Keyword;
import com.yanoos.global.entity.member.Member;
import com.yanoos.member.service.entity_service.keyword.KeywordEntityService;
import com.yanoos.member.service.entity_service.map_member_keyword.dto.CreateMapMemberKeywordIn;
import com.yanoos.member.service.entity_service.member.MemberEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class KeywordBusinessService {
    // private final KeywordEntityService keywordEntityService;
    // private final MapMemberKeywordEntityService mapMemberKeywordEntityService;
    // private final MemberEntityService memberEntityService;
    // private final AuthUtil authUtil;
    // /**
    //  * 키워드 등록 및 등록 멤버에 키워드 매핑
    //  *
    //  * @param postKeywordIn
    //  * @return
    //  */
    // @Transactional
    // public PostKeywordOut postKeyword(PostKeywordIn postKeywordIn) {
    //     Long memberId = authUtil.getMemberId();
    //
    //     Member member = memberEntityService.getMemberByMemberId(memberId);
    //     Keyword keyword = keywordEntityService.createKeyword(CreateKeywordIn.builder()
    //             .keyword(postKeywordIn.getKeyword())
    //             .build());
    //
    //     MapMemberKeyword mapMemberKeyword = mapMemberKeywordEntityService.createMapMemberKeyword(CreateMapMemberKeywordIn.builder()
    //             .keyword(keyword)
    //             .member(member)
    //             .build());
    //
    //     return PostKeywordOut.builder()
    //             .isSuccess(true)
    //             .build();
    // }
    //
    // public List<String> getKeywordsByMemberId(long memberId) {
    //     validationTelegramIdExist(memberId);
    //     Member member = memberEntityService.getMemberByMemberId(memberId);
    //
    //     List<Keyword> mapMemberKeywords = member.getKeywords();
    //     return mapMemberKeywords.stream().map(Keyword::getKeyword).toList();
    // }
    //
    //
    // private void validationTelegramIdExist(long memberId) {
    //     Member member = memberEntityService.getMemberByMemberId(memberId);
    //     if(member.getMapMemberTelegramUsers().isEmpty()){
    //         throw new BusinessException(MemberErrorCode.TELEGRAM_UUID_NOT_FOUND);
    //     }
    // }
}
