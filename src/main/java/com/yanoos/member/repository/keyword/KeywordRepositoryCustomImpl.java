package com.yanoos.member.repository.keyword;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yanoos.global.entity.member.Keyword;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import static com.yanoos.global.entity.member.QKeyword.keyword1;

@RequiredArgsConstructor
public class KeywordRepositoryCustomImpl implements KeywordRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    // @Override
    // public List<Keyword> findKeywordsByMemberId(long memberId) {
    //     return queryFactory.selectFrom(keyword1)
    //             .innerJoin(mapMemberKeyword).on(mapMemberKeyword.keyword.eq(keyword1))
    //             .where(mapMemberKeyword.member.id.eq(memberId))
    //             .fetch();
    // }
    //
    // @Override
    // public List<Keyword> findKeywordsInPostTitle(String postTitle) {
    //     List<Keyword> allKeywords = queryFactory.selectFrom(keyword1).fetch();
    //     return allKeywords.stream()
    //             .filter(curKeyword -> postTitle.contains(curKeyword.getKeyword()))
    //             .collect(Collectors.toList());
    // }
}
