package com.yanoos.crawler.renew.service;

import com.yanoos.crawler.renew.global.WebDriverProvider;
import com.yanoos.crawler.renew.util.PostParser;
import com.yanoos.crawler.renew.util.WebDriverUtil;
import com.yanoos.crawler.util.dto.ColumnIndexDto;
import com.yanoos.crawler.util.util.SystemUtil;
import com.yanoos.global.entity.dto.PostDto;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLException;
import java.util.List;

//last updated 2025-04-18 06:00
//전북지방환경청-공고
@Service("SmgmeGosiCrawler")
@Slf4j
public class SmgmeGosiCrawler extends AbstractCrawler {
    private static final String boardNameKor = "전북지방환경청-공고";

    private static final String basePathUrl = "https://www.me.go.kr/smg/web/index.do?menuId=857";
    private static final String siteUrl = "https://www.me.go.kr/smg/web/index.do?menuId=857";
    private static final String boardNameEng = "smgme_gosi";
    private static final String listTag = "ul";// TR인지 UL인지 등
    private static final String rowXpath = "//*[@id=\"content3\"]/div/div/div[2]/div/div/div[2]/ul";
    private static final int pageSize = 10;

    private static final String pageButtonXpath = "//*[@id=\"content3\"]/div/div/p[2]/span/a";
    private static final String boardTypeName = "registered";
    private static final String detailTitleXpath = "//*[@id=\"boardTableWrapper\"]/div/ul/li[1]";
    private static final String detailContentXpath = "//*[@id=\"boardTableWrapper\"]/div/ul/li[3]";
    private static final ColumnIndexDto columnIndex = ColumnIndexDto.builder()
            .noIndex(0)
            .titleIndex(1)
            .writeDateIndex(3)
            .departmentIndex(2)
            .build();

    private final WebDriverProvider webDriverProvider;
    private final WebClientService webClientService;

    public SmgmeGosiCrawler(WebDriverProvider webDriverProvider, WebClientService webClientService) {
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

        WebDriverUtil.movePage(driver, page, pageButtonXpath);//페이지 이동

        WebDriverUtil.waitForPageLoad(driver, 30);

        List<WebElement> rows = driver.findElements(By.xpath(rowXpath));

        List<PostDto> postDtos = PostParser.makeBasePostDtoList(driver, rows, columnIndex, listTag);


        for (PostDto postDto : postDtos) {
            WebElement row = postDto.getRow();
            WebElement titleElement = row.findElements(By.tagName("li")).get(columnIndex.getTitleIndex());
            // String id = titleElement.findElement(By.tagName("a")).getAttribute("href");
            // id = StringUtil.getStringInParentheses(id).replaceAll("'", "");

            String href = titleElement.findElement(By.tagName("a")).getAttribute("href");
            // String href = String.format("https://www.jongno.go.kr/portal/bbs/selectBoardArticle.do?bbsId=BBSMSTR_000000000271&menuNo=1756&menuId=1756&nttId=%s",id);
            // log.info("id: {}", id);
            //

            // MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            // formData.add("seq", id);
            //


            //상세 제목 조회
            HttpMethod httpMethod = HttpMethod.GET;
            // String htmlData = webClientService.requestHtmlData(href, null, httpMethod, null);
            // Document document = Jsoup.parse(htmlData);
            // postDto.setTitle(document.selectXpath(detailTitleXpath).text());
            // postDto.setContent(document.selectXpath(detailContentXpath).text());
            // SystemUtil.testLog("href: {}", href);
            postDto.setEndpoint(href);
            postDto.setMethod(httpMethod.name());

        }

        for (PostDto postDto : postDtos) {
            SystemUtil.testLog(postDto.toString());
        }
        SystemUtil.testLog("\nSIZE: {}\n", postDtos.size());

        return postDtos;


    }
}
