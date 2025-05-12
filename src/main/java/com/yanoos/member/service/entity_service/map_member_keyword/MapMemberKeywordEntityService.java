package com.yanoos.member.service.entity_service.map_member_keyword;// package com.yanoos.member.service.entity_service.map_member_keyword;
//
// import com.yanoos.member.service.entity_service.map_member_keyword.dto.CreateMapMemberKeywordIn;
// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;
//
// import java.util.List;
//
// @Slf4j
// @RequiredArgsConstructor
// @Service
// @Transactional(readOnly = true)
// public class MapMemberKeywordEntityService {
//
//
//     @Transactional
//     public MapMemberKeyword createMapMemberKeyword(CreateMapMemberKeywordIn createMapMemberKeywordIn) {
//         MapMemberKeyword build = MapMemberKeyword.builder()
//                 .keyword(createMapMemberKeywordIn.getKeyword())
//                 .member(createMapMemberKeywordIn.getMember())
//                 .build();
//         return mapmemberKeywordRepository.save(build);
//     }
//
//
//     public List<MapMemberKeyword> getMapMemberKeywordsByKeywordIds(List<Long> keywordIds) {
//         return mapmemberKeywordRepository.findByKeywordIds(keywordIds);
//     }
// }
