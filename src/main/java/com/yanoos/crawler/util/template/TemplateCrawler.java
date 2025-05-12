package com.yanoos.crawler.util.template;

import com.yanoos.crawler.util.constant.HtmlTagType;
import com.yanoos.crawler.util.dto.ColumnIndexDto;
import com.yanoos.crawler.util.util.CrawlerUtil;
import com.yanoos.crawler.util.util.webclient.WebClientUtil;
import com.yanoos.crawler.util.util.webclient.factory.WebClientProvider;
import com.yanoos.crawler.util.util.webclient.factory.WebClientType;
import com.yanoos.global.entity.dto.PostDto;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import javax.net.ssl.SSLException;
import java.io.IOException;
import java.util.List;

//
@Slf4j
public class TemplateCrawler extends Crawler {
    public TemplateCrawler() {
        super();
        this.boardNameEng = "";//airport_notice
        this.boardNameKor = "";//한국공항공사-공지사항
        this.boardTypeName = "registered";
        this.basePathUrl = "";//사이트주소
        // this.siteUrl = "";//https://www.airport.co.kr/www/cms/frCon/index.do?MENU_ID=1100 사이트 따로 쓸때만 사용

        this.pageButtonXpath = "";//페이지 이동버튼
        this.movedPageButtonCssSelector = null;//페이지 이동버튼완료체크용 (cssSelector)
        this.movedPageButtonXpath = null;//페이지 이동버튼완료체크용
        this.listTag = "tr";//TR인지 UL인지 등
        this.rowXpath = "";//리스트의 각 행
        this.contentInDetailXpath = ""; //상세내용 xpath
        this.needDetailTitle = false;//제목 축약형만 사용
        this.skipMovePage = false;
        // this.detailTitleXpath="";
        this.columnIndex = ColumnIndexDto.builder()
                .noIndex(0)
                .titleIndex(0)
                .writeDateIndex(0)
                .departmentIndex(0)
                .build();
        this.columnIndex.setDetailButtonIndex(this.columnIndex.getTitleIndex());
        if(this.siteUrl==null){
            this.siteUrl = this.basePathUrl;
        }
    }

    @Override
    public void validateMoveComplete(int targetPageNumber) throws InterruptedException {
        Thread.sleep(5000);
        // driver.findElements(By.cssSelector(movedPageButtonCssSelector));

        // new WebDriverWait(driver, Duration.ofSeconds(300))
        //         .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(movedPageButtonCssSelector)));

        // WebElement movedPageButton = getTargetPageButton(targetPageNumber, movedPageButtonXpath);

    }

    @Override
    protected String getContent(WebElement detailButton) throws SSLException {

        // log.debug("getContent {}", detailButton);
        // String id = detailButton
        //         .findElement(By.tagName(HtmlTagType.a.name()))
        //         .getAttribute("onclick");
        // //fn_goView('78693')에서 78693만 추출
        // id = id.split("'")[1];

        log.debug("getContent {}", detailButton);
        String id = getContentId(detailButton);
        if(System.getProperty("os.name").contains("Windows")) {
            log.info("id = {}",id);
        }

        String detailHtml = getDetailHtml(id);
        if (System.getProperty("os.name").contains("Windows")) {
            log.info("detailHtml = {}", detailHtml);
        }
        Document document = Jsoup.parse(detailHtml);
        String content = document.selectXpath(contentInDetailXpath).outerHtml();
        return content;
    }

    public String getDetailHtml(String id) throws SSLException {//변경시 SdmGosiCrawler 참고
        String url = "https://eminwon.seongnam.go.kr/emwp/gov/mogaha/ntis/web/ofr/action/OfrAction.do";
        // WebClient 생성 (일반 WebClient 사용) ===================== 시작
        if (System.getProperty("os.name").contains("Windows")) {
            log.info("webClient req uri = {}", url);
        }
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("jndinm", "OfrNotAncmtEJB");
        formData.add("context", "NTIS");
        formData.add("method", "selectOfrNotAncmt");
        formData.add("methodnm", "selectOfrNotAncmtRegst");
        formData.add("not_ancmt_mgt_no", id); // 유일하게 변수로 받는 게시글 ID
        formData.add("homepage_pbs_yn", "Y");
        formData.add("subCheck", "N");
        formData.add("ofr_pageSize", "10");
        formData.add("not_ancmt_se_code", "01,04");
        formData.add("title", "고시공고");
        formData.add("initValue", "Y");
        formData.add("contentYn", "Y");
        formData.add("key", "B_Subject");
        return WebClientUtil.getHtmlByPost(WebClientType.DEFAULT, url, formData);
    }
    //제목 축약형만 사용
    @Override
    protected String getTitle(List<WebElement> tds) throws SSLException {
        if(this.detailTitleXpath==null){
            return tds.get(columnIndex.getTitleIndex()).getText();
        }
        WebElement detailButton = tds.get(columnIndex.getDetailButtonIndex());
        String id = getContentId(detailButton);

        log.debug("id = {}",id);

        String detailHtml = getDetailHtml(id);
        Document document = Jsoup.parse(detailHtml);
        // log.info("document = \n{}",document);
        String title = document.selectXpath(detailTitleXpath).text();

        return title;
    }
    public String getContentId(WebElement detailButton) {
        String id = CrawlerUtil.getContentIdByHref(detailButton);
        return id;
    }

    @Override
    public void afterJob(List<PostDto> posts) {
        log.debug("afterJob");
        posts.forEach(PostDto::systemLog);

    }
    @Override
    public PostDto setHttpInfoToPostDto(PostDto postDto,WebElement titleElement) {
        String id = getContentId(titleElement);
        postDto.setMethod(HttpMethod.GET.name());
        postDto.setEndpoint(id);
        // postDto.setParameters(CrawlerUtil.multiValueMapToJsonString(formData));

        return postDto;
    }


    public static void main(String[] args) throws InterruptedException , IOException {
        log.info("run");
        log.debug("debug");
        Crawler crawler = new TemplateCrawler();
        crawler.crawling(2);

    }
}
