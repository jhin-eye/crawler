package com.yanoos.crawler.util.util.webclient.factory;

import org.springframework.web.reactive.function.client.WebClient;

import javax.net.ssl.SSLException;
import java.io.IOException;

public class DefaultWebClientFactory implements WebClientFactory{

    @Override
    public WebClient createWebClient() throws SSLException {
        // WebClient 생성 (일반 WebClient 사용) ===================== 시작
        return WebClient.builder().codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(300 * 1024 * 1024)) // 최대 3MB로 설정
                .build();
        // WebClient 생성 (일반 WebClient 사용) ===================== 끝
    }
}
