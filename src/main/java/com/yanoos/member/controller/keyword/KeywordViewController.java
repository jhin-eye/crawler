package com.yanoos.member.controller.keyword;// package com.yanoos.member.controller.keyword;
//
// import com.yanoos.global.exception.BusinessException;
// import com.yanoos.global.exception.code.MemberErrorCode;
// import com.yanoos.global.util.AuthUtil;
// import com.yanoos.member.service.business_service.keyword.KeywordBusinessService;
// import com.yanoos.member.service.business_service.member.MemberBusinessService;
// import com.yanoos.member.controller.dto.PostKeywordIn;
// import com.yanoos.member.controller.dto.PostKeywordOut;
// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.*;
//
// import java.util.ArrayList;
// import java.util.List;
//
// @RequiredArgsConstructor
// @Slf4j
// @Controller
// @RequestMapping("/api/view/keyword")
// public class KeywordViewController {
//     private final AuthUtil authUtil;
//     private final KeywordBusinessService keywordBusinessService;
//     private final MemberBusinessService memberBusinessService;
//
//
//     @GetMapping
//     public String getKeywords(Model model){
//         List<String> mapMemberKeywords = new ArrayList<>();
//         try {
//             mapMemberKeywords=keywordBusinessService.getKeywordsByMemberId(authUtil.getMemberId());
//         }catch (BusinessException e){
//             if(e.getErrorCode().equals(MemberErrorCode.TELEGRAM_UUID_NOT_FOUND)){
//                 model.addAttribute("alert","먼저 텔레그램 연동을 진행하세요");
//                 return "telegramUuidGenerateForm";
//             }else{
//                 throw e;
//             }
//         }
//         model.addAttribute("keywords",mapMemberKeywords);
//         return "keyword";
//     }
//
//
//
//
//     @PostMapping()
//     public String postKeyword(@ModelAttribute PostKeywordIn postKeywordIn) {
//         validatePostKeywordIn(postKeywordIn);
//         PostKeywordOut postKeywordOut = keywordBusinessService.postKeyword(postKeywordIn);
//         return "redirect:/api/view/settings/keyword";
//     }
//
//     private void validatePostKeywordIn(PostKeywordIn postKeywordIn) {
//         log.info("validatePostKeywordIn success!");
//     }
//
//
// }
