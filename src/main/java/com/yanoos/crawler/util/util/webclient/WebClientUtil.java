package com.yanoos.crawler.util.util.webclient;

import com.yanoos.crawler.util.util.SystemUtil;
import com.yanoos.crawler.util.util.webclient.factory.WebClientProvider;
import com.yanoos.crawler.util.util.webclient.factory.WebClientType;
import com.yanoos.global.util.FileUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import javax.net.ssl.SSLException;
import java.io.IOException;
import java.util.Map;
public class WebClientUtil {
    public static String getHtmlByGet(WebClientType webClientType, String url) throws SSLException {
        WebClient webClient = WebClientProvider.getWebClient(webClientType);
        return webClient
                .get() // GET 요청
                .uri(url) // 요청할 URI 설정
                .header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8") // 헤더 설정
                .retrieve() // 응답 수신
                .bodyToMono(String.class) // 응답을 Mono<String>으로 변환
                .block(); // 블로킹하여 결과를 즉시 반환
    }

    public static String getHtmlByPost(WebClientType webClientType, String url, MultiValueMap<String, String> formData) throws SSLException {
        WebClient webClient = WebClientProvider.getWebClient(webClientType);
        return webClient
                .post()
                .uri(url)
                .header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8")
                .bodyValue(formData)
                .retrieve()
                .bodyToMono(String.class).block();
    }
    public static String requestHtmlData(
            WebClientType webClientType,
            String url,
            Map<String, String> headers,
            HttpMethod method,
            MultiValueMap<String, String> formData // POST 요청의 본문
    ) throws SSLException {

        WebClient webClient = WebClientProvider.getWebClient(webClientType);

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




    public static ResponseEntity<byte[]> requestFileData(
            WebClientType webClientType,
            String url,
            Map<String, String> headers,
            HttpMethod method,
            MultiValueMap<String, String> formData // POST 요청의 본문
    ) throws IOException {

        WebClient webClient = WebClientProvider.getWebClient(webClientType);

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

    public static void main(String[] args) throws IOException {
    }


}
