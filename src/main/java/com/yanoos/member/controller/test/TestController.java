package com.yanoos.member.controller.test;

import com.yanoos.global.jwt.CustomPrincipal;
import com.yanoos.global.util.AuthUtil;
import com.yanoos.member.controller.test.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final AuthUtil authUtil;
    @GetMapping
    public ResponseEntity<GetTestOutDTO> getTest(){
        log.info("get Test");
        return ResponseEntity.ok(GetTestOutDTO.builder().result("ok").build());
    }

    //숨고용 임시 api
    @PostMapping
    public ResponseEntity<PostTestOutDTO> postTest(@RequestBody PostTestInDTO postTestInDTO){
        log.info("input is {}",postTestInDTO);
        return ResponseEntity.ok(PostTestOutDTO.builder()
                .boardName(postTestInDTO.getBoardName())
                .boardUrl(postTestInDTO.getBoardUrl())
                .postTitle(postTestInDTO.getPostTitle())
                .postUrl(postTestInDTO.getPostUrl())
                .build());
    }

    @PostMapping("/valid")
    public ResponseEntity<ValidTestOutDTO> validTest(@RequestBody @Valid ValidTestInDTO validTestInDTO){
        return ResponseEntity.ok(ValidTestOutDTO.builder().p1("11").build());
    }
    @GetMapping("/api/member/info")
    @ResponseBody
    public Long validJwt(){
        log.info("validJwt in");
        return authUtil.getMemberId();
    }

}
