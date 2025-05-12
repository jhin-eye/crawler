package com.yanoos.crawler.renew.service;

import com.yanoos.crawler.util.util.webclient.factory.WebClientProvider;
import com.yanoos.crawler.util.util.webclient.factory.WebClientType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import javax.net.ssl.SSLException;
import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebClientService {
    private final WebClient webClient;

    public String requestHtmlData(
            String url,
            Map<String, String> headers,
            HttpMethod method,
            MultiValueMap<String, String> formData // POST 요청의 본문
    ) throws SSLException {


        WebClient.RequestBodySpec requestSpec = webClient
                .method(method)
                .uri(url)
                .headers(httpHeaders -> {
                    if (headers != null) {
                        headers.forEach(httpHeaders::set);
                    }
                });

        if (method == HttpMethod.POST && formData != null) {
            requestSpec.bodyValue(formData);
        }

        return requestSpec
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }




    public ResponseEntity<byte[]> requestFileData(
            String url,
            Map<String, String> headers,
            HttpMethod method,
            MultiValueMap<String, String> formData // POST 요청의 본문
    ) throws IOException {

        WebClient.RequestBodySpec requestSpec = webClient
                .method(method)
                .uri(url)
                .headers(httpHeaders -> {
                    if (headers != null) {
                        headers.forEach(httpHeaders::set);
                    }
                });

        if (method == HttpMethod.POST && formData != null) {
            requestSpec.bodyValue(formData);
        }

        ResponseEntity<byte[]> fileData = requestSpec
                .retrieve()
                .toEntity(byte[].class)
                .block();

        return fileData;
    }
}
