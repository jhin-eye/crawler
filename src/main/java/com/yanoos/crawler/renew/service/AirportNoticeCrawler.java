package com.yanoos.crawler.renew.service;

import com.yanoos.crawler.renew.global.WebDriverProvider;
import com.yanoos.crawler.renew.util.PostParser;
import com.yanoos.crawler.renew.util.StringUtil;
import com.yanoos.crawler.renew.util.WebDriverUtil;
import com.yanoos.crawler.util.dto.ColumnIndexDto;
import com.yanoos.crawler.util.util.SystemUtil;
import com.yanoos.global.entity.dto.PostDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLException;
import java.util.List;

//last updated 2025-03-17 00:35
//한국공항공사-공지사항
//Connection prematurely closed BEFORE response
@Service("AirportNoticeCrawler")
public class AirportNoticeCrawler extends AbstractCrawler {
    private static final String boardNameKor = "한국공항공사-공지사항";// 한국석유공사-공지사항

    private static final String basePathUrl = "https://www.airport.co.kr/www/cms/frCon/index.do?MENU_ID=1100#;";
    private static final String siteUrl = "https://www.airport.co.kr/www/cms/frCon/index.do?MENU_ID=1100#;";
    private static final String boardNameEng = "airport-notice";// airport_notice
    private static final String listTag = "tr";// TR인지 UL인지 등
    private static final String rowXpath = "//*[@id=\"tbody\"]/tr";// 리스트의 각 행
    private static final int pageSize = 15;

    private static final String pageButtonXpath = "//*[@id=\"contents\"]/article/div[1]/div[3]/ul/li/button";// 페이지 이동버튼
    private static final String boardTypeName = "registered";
    private static final String detailTitleXpath = "//*[@id=\"contents\"]/article/div[1]/div[1]/dl/dt/div[1]/p";
    private static final String detailContentXpath = "//*[@id=\"contents\"]/article/div[1]/div[1]/dl/dd/p";
    private static final ColumnIndexDto columnIndex = ColumnIndexDto.builder()
            .noIndex(0)
            .titleIndex(1)
            .writeDateIndex(4)
            .departmentIndex(3)
            .build();

    private final WebDriverProvider webDriverProvider;
    private final WebClientService webClientService;

    public AirportNoticeCrawler(WebDriverProvider webDriverProvider, WebClientService webClientService) {
        super(boardNameEng, boardNameKor, basePathUrl, siteUrl, boardTypeName, pageSize);
        this.webDriverProvider = webDriverProvider;
        this.webClientService = webClientService;
    }


    @Override
    public List<PostDto> crawling(int page) throws InterruptedException, SSLException {
        // if(page==1){
        //     throw new IllegalArgumentException("page is 1");
        // }
        WebDriver driver = webDriverProvider.getDriver("firefox");
        driver.get(basePathUrl);
        WebDriverUtil.waitForPageLoad(driver, 30);

        WebDriverUtil.movePageUsingJs(driver, page, pageButtonXpath);//페이지 이동

        WebDriverUtil.waitForPageLoad(driver, 30);

        List<WebElement> rows = driver.findElements(By.xpath(rowXpath));
        List<PostDto> postDtos = PostParser.makeBasePostDtoList(driver, rows, columnIndex, listTag);


        for (PostDto postDto : postDtos) {
            WebElement row = postDto.getRow();
            WebElement titleElement = row.findElements(By.tagName("td")).get(columnIndex.getTitleIndex());
            String id = titleElement.findElement(By.tagName("a")).getAttribute("data-idno");
            String href = String.format("https://www.airport.co.kr/www/cms/frBoardCon/boardView.do?pageNo=1&pagePerCnt=15&MENU_ID=1100&CONTENTS_NO=&SITE_NO=2&BOARD_SEQ=1&BBS_SEQ=%s&PWD=&SEARCH_FLD=&SEARCH=",id);

            // MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            // formData.add("boardSeq", id);
            //

            //상세 제목 조회
            HttpMethod httpMethod = HttpMethod.GET;
            String htmlData = webClientService.requestHtmlData(href, null, httpMethod, null);
            Document document = Jsoup.parse(htmlData);
            // postDto.setTitle(document.selectXpath(detailTitleXpath).text());
            postDto.setContent(document.selectXpath(detailContentXpath).text());
            // SystemUtil.testLog("href: {}", href);
            postDto.setEndpoint(href);
            postDto.setMethod(httpMethod.name());

        }
        for (PostDto postDto : postDtos) {
            SystemUtil.testLog(postDto.toString());
        }

        return postDtos;


    }
}
