package com.yanoos.crawler.util.template;

import com.yanoos.crawler.util.constant.HtmlTagType;
import com.yanoos.global.entity.dto.PostDto;
import com.yanoos.global.util.NumberUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import javax.net.ssl.SSLException;
import java.io.IOException;
import java.util.List;

import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import reactor.netty.http.client.HttpClient;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

@Slf4j
public class EminwonCrawler extends Crawler {


    @Override
    public void validateMoveComplete(int targetPageNumber) throws InterruptedException {
        Thread.sleep(5000);
        // driver.findElements(By.cssSelector(movedPageButtonCssSelector));

        // new WebDriverWait(driver, Duration.ofSeconds(300))
        //         .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(movedPageButtonCssSelector)));

        // WebElement movedPageButton = getTargetPageButton(targetPageNumber, movedPageButtonXpath);

    }

    @Override
    public boolean isRealTr(WebElement tr) {
        List<WebElement> tds = tr.findElements(By.tagName(HtmlTagType.td.name()));
        log.debug("\n\n<TD INFO> size={}",tds.size());
        for(int i=0;i<tds.size();i++){
            log.debug("idx = {}, text = {}",i,tds.get(i).getText());
        }

        return tds.size()==7;
    }

    @Override
    protected String getContent(WebElement detailButton) throws SSLException {
        String detailId = getDetailId(detailButton);
        String detailHtml = getDetailHtml(detailId);
        Document document = Jsoup.parse(detailHtml);
        String content = document.selectXpath(contentInDetailXpath).outerHtml();
        if (System.getProperty("os.name").contains("Windows")) {
            log.info("detailHtml = {}", detailHtml);
        }
        return content;
    }

    @Override
    protected String getTitle(List<WebElement> tds) throws SSLException  {
        WebElement detailButton = tds.get(columnIndex.getDetailButtonIndex());
        String detailId = getDetailId(detailButton);
        String detailHtml = getDetailHtml(detailId);
        Document document = Jsoup.parse(detailHtml);
        return document.selectXpath("/html/body/form[2]/table[2]/tbody/tr/td/table[1]/tbody/tr[9]/td").text();
    }
    protected String getDetailId(WebElement detailButton) {
        String href = detailButton
                .findElement(By.tagName(HtmlTagType.p.name()))
                .findElement(By.tagName(HtmlTagType.a.name()))
                .getAttribute("onclick");
        ;
        String detailId = NumberUtil.extractNumber(href);
        return detailId;
    }



    public String getDetailHtml(String notAncmtMgtNo)  throws SSLException {
        // WebClient 생성 (일반 WebClient 사용) ===================== 시작
        WebClient webClient = WebClient.builder().codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(30 * 1024 * 1024)) // 최대 3MB로 설정
                .build();
        // WebClient 생성 (일반 WebClient 사용) ===================== 끝

        // // WebClient 생성 (SSL 검증을 무시하는 HttpClient 사용) ===================== 시작
        // HttpClient httpClient = null;
        // try {
        //     // 모든 인증서를 신뢰하는 SslContext 생성
        //     SslContext sslContext = SslContextBuilder.forClient()
        //             .trustManager(InsecureTrustManagerFactory.INSTANCE) // 모든 인증서 신뢰 설정
        //             .build();
        //
        //     // HttpClient에 SSLContext 적용
        //     httpClient = HttpClient.create()
        //             .secure(sslProvider -> sslProvider.sslContext(sslContext)); // sslContext 설정
        // } catch (SSLException e) {
        //     // e.printStackTrace();
        //     throw e;
        // }
        // // WebClient 생성 (SSL 검증을 무시하는 HttpClient 사용)
        // WebClient webClient = WebClient.builder()
        //         .clientConnector(new ReactorClientHttpConnector(httpClient))
        //         .codecs(configurer -> configurer
        //                 .defaultCodecs()
        //                 .maxInMemorySize(30 * 1024 * 1024)) // 최대 3MB로 설정
        //         .build();
        // // WebClient 생성 (SSL 검증을 무시하는 HttpClient 사용) ===================== 끝
        // 상수화된 요청 파라미터들
        final String JNDINM = "OfrNotAncmtEJB";
        final String CONTEXT = "NTIS";
        final String METHOD = "selectOfrNotAncmt";
        final String METHODNM = "selectOfrNotAncmtRegst";
        final String HOMEPAGE_PBS_YN = "Y";
        final String SUB_CHECK = "N";
        final String OFR_PAGE_SIZE = "10";
        final String NOT_ANCMT_SE_CODE = "01,04";
        final String TITLE = "%EA%B3%A0%EC%8B%9C%EA%B3%B5%EA%B3%A0";
        final String INIT_VALUE = "Y";
        final String CONTENT_YN = "Y";
        final String KEY = "B_Subject";

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("jndinm", JNDINM);
        formData.add("context", CONTEXT);
        formData.add("method", METHOD);
        formData.add("methodnm", METHODNM);
        formData.add("not_ancmt_mgt_no", notAncmtMgtNo); // 유일하게 변수로 받는 게시글 ID
        formData.add("homepage_pbs_yn", HOMEPAGE_PBS_YN);
        formData.add("subCheck", SUB_CHECK);
        formData.add("ofr_pageSize", OFR_PAGE_SIZE);
        formData.add("not_ancmt_se_code", NOT_ANCMT_SE_CODE);
        formData.add("title", TITLE);
        formData.add("initValue", INIT_VALUE);
        formData.add("contentYn", CONTENT_YN);
        formData.add("key", KEY);

        log.debug("announcementRequest = {}", formData);
        if (System.getProperty("os.name").contains("Windows")) {
            log.info("notAncmtMgtNo = {}",notAncmtMgtNo );
        }

        return webClient
                .post()
                .uri("https://dongjak.eminwon.seoul.kr/emwp/gov/mogaha/ntis/web/ofr/action/OfrAction.do")
                .header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8")
                .bodyValue(formData)
                .retrieve()
                .bodyToMono(String.class).block();
    }
    @Override
    public void afterJob(List<PostDto> posts) {
        log.debug("afterJob");
        posts.forEach(postDto -> {
            log.info("title = {}",postDto.getTitle());
        });

    }

    public static void main(String[] args) throws InterruptedException ,IOException {
        Crawler crawler = new EminwonCrawler();
        crawler.crawling(2);

    }
}
