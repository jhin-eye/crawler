package com.yanoos.global.jwt.controller;

import com.yanoos.global.jwt.dto.TokensResponseDto;
import com.yanoos.global.jwt.service.JwtTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@Controller
@RequestMapping("/api/token")
@RequiredArgsConstructor
public class TokenController {
    private final JwtTokenService jwtTokenService;
    @GetMapping("/by/refresh")
    public String createAccessTokenByRefreshToken(HttpServletRequest request, HttpServletResponse response){
        log.info("createAccessTokenByRefreshToken in");
        TokensResponseDto tokensResponseDto = jwtTokenService.regenerateTokensByRefreshToken(request);
        jwtTokenService.setTokensOnCookie(response, tokensResponseDto.getAccessToken(), tokensResponseDto.getRefreshToken());
        return "redirect:/api/view/keyword";
    }
}
