package com.yanoos.member.controller.member;


import com.yanoos.global.util.AuthUtil;
import com.yanoos.member.service.business_service.member.MemberBusinessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/api/member")
public class MemberController {
    private final MemberBusinessService memberBusinessService;
    private final AuthUtil authUtil;

    @GetMapping("/telegram-uuid")
    public String getTelegramUuidForm(){
        return "telegramUuidGenerateForm";
    }

    @PostMapping("/telegram-uuid")
    public String generateTelegramUuid(Model model){

        GenerateTelegramUuidOut generateTelegramUuidOut = memberBusinessService.generateTelegramUuid(authUtil.getMemberId());
        model.addAttribute("generateTelegramUuidOut", generateTelegramUuidOut);
        return "setting/telegramSettingUUidGenerated";
    }
}
