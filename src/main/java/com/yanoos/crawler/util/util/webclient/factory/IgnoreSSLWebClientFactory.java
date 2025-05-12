package com.yanoos.crawler.util.util.webclient.factory;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;
import java.io.IOException;

public class IgnoreSSLWebClientFactory implements WebClientFactory {
    @Override
    public org.springframework.web.reactive.function.client.WebClient createWebClient() throws SSLException {
        HttpClient httpClient = null;
        try {
            // 모든 인증서를 신뢰하는 SslContext 생성
            SslContext sslContext = SslContextBuilder.forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE) // 모든 인증서 신뢰 설정
                    .build();

            // HttpClient에 SSLContext 적용
            httpClient = HttpClient.create()
                    .secure(sslProvider -> sslProvider.sslContext(sslContext)); // sslContext 설정
        } catch (SSLException e) {
            // e.printStackTrace();
            throw e;
        }
        // WebClient 생성 (SSL 검증을 무시하는 HttpClient 사용)
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(300 * 1024 * 1024)) // 최대 3MB로 설정
                .build();
    }
}