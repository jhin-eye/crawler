package com.yanoos.crawler.renew.service;

import com.yanoos.crawler.renew.global.WebDriverProvider;
import com.yanoos.crawler.renew.util.PostParser;
import com.yanoos.crawler.renew.util.StringUtil;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.net.ssl.SSLException;
import java.util.List;
//eminwon
//last updated 2025-04-29 00:59
//부천시-고시공고
@Service("BucheonGosiCrawler")
@Slf4j
public class BucheonGosiCrawler extends AbstractCrawler {
    private static final String boardNameKor = "부천시-고시공고";

    private static final String basePathUrl = "https://eminwon.bucheon.go.kr/emwp/jsp/ofr/OfrNotAncmtLSub.jsp?epcCheck=Y";
    private static final String siteUrl = "https://eminwon.bucheon.go.kr/emwp/jsp/ofr/OfrNotAncmtLSub.jsp?epcCheck=Y";
    private static final String boardNameEng = "bucheon_gosi";
    private static final String listTag = "tr";// TR인지 UL인지 등
    private static final String rowXpath = "/html/body/form/div[3]/table/tbody/tr";
    private static final int pageSize = 10;

    private static final String pageButtonXpath = "/html/body/form/div[3]/div[2]/table/tbody/tr/td[4]/span/a";
    private static final String boardTypeName = "registered";
    private static final String detailTitleXpath = "/html/body/div/form/table/thead/tr/th";
    private static final String detailContentXpath = "/html/body/div/form/table/tbody/tr[4]/td";
    private static final ColumnIndexDto columnIndex = ColumnIndexDto.builder()
            .noIndex(1)
            .titleIndex(2)
            .writeDateIndex(4)
            .departmentIndex(3)
            .build();

    private final WebDriverProvider webDriverProvider;
    private final WebClientService webClientService;

    public BucheonGosiCrawler(WebDriverProvider webDriverProvider, WebClientService webClientService) {
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
        Thread.sleep(2000);
        WebDriverUtil.waitForPageLoad(driver, 30);

        List<WebElement> rows = driver.findElements(By.xpath(rowXpath));

        List<PostDto> postDtos = PostParser.makeBasePostDtoList(driver, rows, columnIndex, listTag);


        for (PostDto postDto : postDtos) {
            WebElement row = postDto.getRow();
            WebElement titleElement = row.findElements(By.tagName("td")).get(columnIndex.getTitleIndex());
            String id = titleElement.findElement(By.tagName("a")).getAttribute("onclick");
            id = StringUtil.getStringInParentheses(id).replaceAll("'", "");

            String href = titleElement.findElement(By.tagName("a")).getAttribute("href");
            // String href = String.format("https://www.jongno.go.kr/portal/bbs/selectBoardArticle.do?bbsId=BBSMSTR_000000000271&menuNo=1756&menuId=1756&nttId=%s",id);
            // log.info("id: {}", id);
            //

            // MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            // formData.add("seq", id);
            //


            href = "https://eminwon.bucheon.go.kr/emwp/gov/mogaha/ntis/web/ofr/action/OfrAction.do";
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("jndinm","OfrNotAncmtEJB");
            formData.add("context","NTIS");
            formData.add("method","selectOfrNotAncmt");
            formData.add("methodnm","selectOfrNotAncmtRegst");
            formData.add("not_ancmt_mgt_no",id);

            formData.add("homepage_pbs_yn","Y");
            formData.add("subCheck","Y");
            formData.add("ofr_pageSize","10");
            formData.add("not_ancmt_se_code","01,02,03,04");

            //상세 제목 조회
            HttpMethod httpMethod = HttpMethod.POST;
            String htmlData = webClientService.requestHtmlData(href, null, httpMethod, formData);
            Document document = Jsoup.parse(htmlData);
            postDto.setTitle(document.selectXpath(detailTitleXpath).text());
            postDto.setContent(document.selectXpath(detailContentXpath).text());
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
