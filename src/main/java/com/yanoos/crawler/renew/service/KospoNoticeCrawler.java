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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.net.ssl.SSLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 한국남부발전 공지사항
@Service("KospoNoticeCrawler")
public class KospoNoticeCrawler extends AbstractCrawler {
    private static final String basePathUrl = "https://www.kospo.co.kr/kospo/90/subview.do";
    private static final String siteUrl = "https://www.kospo.co.kr/kospo/90/subview.do";
    private static final String boardNameEng = "kospo-notice";// airport_notice
    private static final String boardNameKor = "한국남부발전 공지사항";// 한국석유공사-공지사항
    private static final String listTag = "tr";// TR인지 UL인지 등
    private static final String rowXpath = "//*[@id=\"menu90_obj3980\"]/div[2]/form[2]/div/table/tbody/tr";// 리스트의 각 행

    private static final String pageButtonXpath = "//*[@id=\"menu90_obj3980\"]/div[2]/form[3]/div/div/ul/li";// 페이지 이동버튼
    private static final String boardTypeName = "registered";
    private static final int pageSize = 10;
    // private static final String detailTitleXpath = "//*[@id=\"frmDefault\"]/div/table/tbody/tr[1]/td[1]";
    // private static final String detailContentXpath = "//*[@id=\"menu90_obj3980\"]/div[2]/div[3]";
    private static final ColumnIndexDto columnIndex = ColumnIndexDto.builder()
            .noIndex(0)
            .titleIndex(1)
            .departmentIndex(2)
            .writeDateIndex(3)
            .build();

    private final WebDriverProvider webDriverProvider;
    private final WebClientService webClientService;

    public KospoNoticeCrawler(WebDriverProvider webDriverProvider, WebClientService webClientService) {
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

        WebDriverUtil.movePage(driver, page, pageButtonXpath);

        WebDriverUtil.waitForPageLoad(driver, 30);

        List<WebElement> rows = driver.findElements(By.xpath(rowXpath));
        List<PostDto> postDtos = PostParser.makeBasePostDtoList(driver, rows, columnIndex, listTag);


        for (PostDto postDto : postDtos) {
            WebElement row = postDto.getRow();
            WebElement titleElement = row.findElements(By.tagName("td")).get(columnIndex.getTitleIndex());


            String href = titleElement.findElement(By.tagName("a")).getAttribute("href");


            //상세 제목 조회
            HttpMethod method = HttpMethod.GET;
            String htmlData = webClientService.requestHtmlData(href, null, method, null);
            Document document = Jsoup.parse(htmlData);

            postDto.setContent(document.toString());
            // SystemUtil.testLog("href: {}", href);
            postDto.setEndpoint(href);
            postDto.setMethod(method.name());

        }
        for (PostDto postDto : postDtos) {
            SystemUtil.testLog(postDto.toString());
        }

        return postDtos;


    }
}
