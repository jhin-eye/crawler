package com.yanoos.crawler.util.template;

import com.yanoos.crawler.util.constant.HtmlTagType;
import com.yanoos.crawler.util.dto.ColumnIndexDto;
import com.yanoos.crawler.util.util.CrawlerUtil;
import com.yanoos.crawler.util.util.SystemUtil;
import com.yanoos.crawler.util.util.webclient.WebClientUtil;
import com.yanoos.crawler.util.util.webclient.factory.WebClientType;
import com.yanoos.global.entity.dto.FileDto;
import com.yanoos.global.entity.dto.PostDto;
import com.yanoos.global.exception.BusinessException;
import com.yanoos.global.exception.code.CrawlerErrorCode;
import com.yanoos.global.util.FileUtil;
import com.yanoos.global.util.NumberUtil;
import com.yanoos.global.util.TimeUtil;
import com.yanoos.global.util.WebDriverFactory;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.net.ssl.SSLException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public abstract class Crawler {
    public WebDriver driver;
    public String boardNameEng;
    public String boardNameKor;
    public String boardTypeName;
    public String siteUrl;// 정식 사이트 주소

    public String basePathUrl;// 게시판주소
    public String pageButtonXpath;// 버튼
    public String movedPageButtonXpath;// 버튼완료체크용
    public String movedPageButtonCssSelector;// 버튼완료체크옹 (cssSelector)
    public String listTag;// tr인지 ul인지
    public String contentInDetailXpath;// 상세조회 내부의 content의 xpath
    public ColumnIndexDto columnIndex;
    public boolean needDetailTitle;
    public String detailTitleXpath;//SdcoGosiCrawler
    public boolean skipMovePage;

    public String rowXpath;
    protected String attachmentsXpath;

    public Crawler() {

    }

    public List<PostDto> crawling(int page) throws InterruptedException, SSLException, IOException {
        try {
            this.driver = WebDriverFactory.getWebDriver();
            SystemUtil.testLog("moveSite");
            log.debug("moveSite");
            moveSite();
            waitForPageLoad(driver);
            SystemUtil.testLog("movePage");

            movePage(page);
            SystemUtil.testLog("validateMoveComplete");
            waitForPageLoad(driver);
            SystemUtil.testLog("getPostDtos");
            List<PostDto> posts = getPostDtos();

            SystemUtil.testLog("posts {}", posts);
            afterJob(posts);
            return posts;
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }

    }

    public void getAttachments(PostDto postDto) throws IOException {
        List<FileDto> fileDtos = postDto.getFileDtos();

        for(FileDto fileDto:fileDtos){
            fileDto.setData(getFileData(fileDto, postDto));
            afterFileJob(fileDto);
            downLoadFile(fileDto,postDto);
        }
    }

    public void afterFileJob(FileDto fileDto) throws UnsupportedEncodingException {
    }

    public ResponseEntity<byte[]> getFileData(FileDto fileDto, PostDto postDto) throws IOException {
        log.info("headers = {}", fileDto.getHeaders());
        log.info("parameters = {}", fileDto.getParameters());
        return WebClientUtil.requestFileData(WebClientType.DEFAULT, fileDto.getUrl(), fileDto.getHeaders(),postDto.getAttachmentMethod(),fileDto.getParameters());
    }

    public void downLoadFile(FileDto fileDto, PostDto postDto) throws IOException {
        //파일 저장
        String path = getFilePath(fileDto.getName()+"."+fileDto.getExtension());
        if (SystemUtil.isLocal()) {
            FileUtil.saveFile(path, fileDto.getData().getBody());
        }
    }

    public void makeFileInfo(Element element, PostDto postDto) throws IOException {

        String fileUrl = getFileUrl(element, postDto);
        SystemUtil.testLog("fileUrl = {}", fileUrl);
        String fileName = getFileName(element, postDto);
        String fileExtension = getFileExtension(element, postDto);
        SystemUtil.testLog("fileName = {}", fileName);
        fileName = encodingFileName(fileName);

        FileDto fileDto = new FileDto();
        fileDto.setName(fileName);
        fileDto.setExtension(fileExtension);
        fileDto.setUrl(fileUrl);
        fileDto.setHeaders(makeFileHeaderMap(element, postDto));
        if(postDto.getCookies().size()!=0){
            fileDto.getHeaders().put("Cookie",makeCookie(postDto.getCookies()));
        }

        fileDto.setParameters(makeFileParameterMap(element, postDto));

        postDto.getFileDtos().add(fileDto);
    }

    public String makeCookie(Map<String, String> cookies) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : cookies.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append(";");
        }
        return sb.toString();
    }


    public String getFileExtension(Element element, PostDto postDto) {
        return null;
    }

    public MultiValueMap<String, String> makeFileParameterMap(Element element, PostDto postDto) {
        return null;
    }

    public Map<String, String> makeFileHeaderMap(Element element, PostDto postDto) {
        return new HashMap<>();
    }


    public String encodingFileName(String fileName) {
        return fileName;
    }

    public String getFileUrl(Element element, PostDto postDto) {
        // element 내부의 첫 a 태그의 href 속성값을 반환
        return element.select("a").attr("href");
    }

    public String getFileName(Element element, PostDto postDto) {
        return null;
    }

    public String getFilePath(String fileName) {
        if (SystemUtil.isLocal()) {
            return "C:\\Users\\Public\\" + this.getClass().getSimpleName() + "\\" + fileName;
        }
        return "/home/" + this.getClass().getSimpleName() + "/" + fileName;
    }


    private List<PostDto> getPostDtos() throws IOException {
        if (listTag.equalsIgnoreCase("tr")) {
            List<WebElement> trs = getTrList();
            List<PostDto> postDtos = new ArrayList<>();
            for (int i = 0; i < trs.size(); i++) {
                PostDto postDto = trToPostDto(trs.get(i));
                postDtos.add(postDto);
                // trs = getTrList();
            }
            return postDtos;
        } else if (listTag.equalsIgnoreCase("ul")) {
            List<WebElement> uls = driver.findElements(By.xpath(rowXpath));
            List<PostDto> postDtos = new ArrayList<>();
            for (int i = 0; i < uls.size(); i++) {
                PostDto postDto = ulToPostDto(uls.get(i));
                postDtos.add(postDto);
            }
            return postDtos;
        } else if (listTag.equalsIgnoreCase("li")) {
            List<WebElement> lis = driver.findElements(By.xpath(rowXpath));
            List<PostDto> postDtos = new ArrayList<>();
            for (int i = 0; i < lis.size(); i++) {
                PostDto postDto = liToPostDto(lis.get(i));
                postDtos.add(postDto);
            }
            return postDtos;
        }
        throw new RuntimeException("no list type: " + listTag);
    }


    public PostDto liToPostDto(WebElement webElement) throws IOException {
        String text = webElement.getText();
        log.info("text = {}", text);
        String[] split = text.split("\n");
        if (System.getProperty("os.name").contains("Windows")) {
            for (int i = 0; i < split.length; i++) {
                log.info("idx = {}, text = {}", i, split[i]);
            }
        }
        PostDto postDto = new PostDto();
        String no = split[columnIndex.getNoIndex()];
        String title = split[columnIndex.getTitleIndex()];
        setHttpInfoToPostDto(postDto, webElement);


        String department = columnIndex.getDepartmentIndex() == -1 ? null : split[columnIndex.getDepartmentIndex()];
        ZonedDateTime writeDate = columnIndex.getWriteDateIndex() == -1 ? null : TimeUtil.parseWriteDateToZonedDatetime(split[columnIndex.getWriteDateIndex()]);
        postDto = setInfoToPostDto(postDto, no, title, department, writeDate);

        return postDto;
    }

    private PostDto ulToPostDto(WebElement webElement) throws IOException {
        List<WebElement> lis = webElement.findElements(By.tagName(HtmlTagType.li.name()));
        // print lis text
        for (int i = 0; i < lis.size(); i++) {
            log.debug("idx = {}, text = {}", i, lis.get(i).getText());
        }
        PostDto postDto = new PostDto();

        String no = lis.get(columnIndex.getNoIndex()).getText();
        String title = lis.get(columnIndex.getTitleIndex()).getText();
        WebElement titleElement = lis.get(columnIndex.getDetailButtonIndex());


        setHttpInfoToPostDto(postDto, titleElement);


        String department = columnIndex.getDepartmentIndex() == -1 ? null : lis.get(columnIndex.getDepartmentIndex()).getText();
        ZonedDateTime writeDate = columnIndex.getWriteDateIndex() == -1 ? null : TimeUtil.parseWriteDateToZonedDatetime(lis.get(columnIndex.getWriteDateIndex()).getText());

        postDto = setInfoToPostDto(postDto, no, title, department, writeDate);
        return postDto;
    }

    public PostDto trToPostDto(WebElement tr) throws IOException {
        List<WebElement> tds = tr.findElements(By.tagName(HtmlTagType.td.name()));
        PostDto postDto = new PostDto();

        WebElement titleElement = tds.get(columnIndex.getDetailButtonIndex());
        setHttpInfoToPostDto(postDto, titleElement);

        postDto = setInfoToPostDto(postDto, tds.get(columnIndex.getNoIndex()).getText(), getTitle(tds), columnIndex.getDepartmentIndex() == -1 ? null : tds.get(columnIndex.getDepartmentIndex()).getText(), columnIndex.getWriteDateIndex() == -1 ? null : TimeUtil.parseWriteDateToZonedDatetime(tds.get(columnIndex.getWriteDateIndex()).getText()));

        log.debug("rowDto : {}", postDto.toString());
        return postDto;
    }

    public PostDto setInfoToPostDto(PostDto postDto, String no, String title, String department, ZonedDateTime writeDate) throws IOException {
        postDto.setNo(no);
        postDto.setTitle(title);
        if(this.detailTitleXpath!=null){
            postDto.setTitle(postDto.getDocument().selectXpath(this.detailTitleXpath).text());
        }
        SystemUtil.testLog("titls = {}", postDto.getTitle());
        postDto.setDepartment(department);
        postDto.setWriteDate(writeDate);
        postDto.setMonitorTime(ZonedDateTime.now(ZoneId.of("Asia/Seoul")));
        if(contentInDetailXpath==null){
            postDto.setContent(postDto.getDocument().outerHtml());
            return postDto;
        }
        postDto.setContent(postDto.getDocument().selectXpath(contentInDetailXpath).outerHtml());

        Document document = postDto.getDocument();
        if(attachmentsXpath==null){
            return postDto;
        }
        Elements elements = document.selectXpath(attachmentsXpath);

        postDto.setAttachmentMethod(getAttachmentHttpMethod());
        postDto.setAttachmentWebClientType(getAttachmentWebClientType());
        postDto.setFileDtos(new ArrayList<>());

        for (Element element : elements) {
            makeFileInfo(element, postDto);
        }
        if(postDto.getFileDtos()!=null) {
            getAttachments(postDto);
        }
        return postDto;
    }

    public WebClientType getAttachmentWebClientType() {
        return WebClientType.DEFAULT;
    }

    public HttpMethod getAttachmentHttpMethod(){
        return HttpMethod.GET;
    }

    protected List<WebElement> getTrList() {
        List<WebElement> trs = driver.findElements(By.xpath(rowXpath))
                .stream().filter(tr -> isRealTr(tr)).toList();// 정상 tr 필터링
        return trs;
    }

    public boolean isRealTr(WebElement tr) {
        List<WebElement> tds = tr.findElements(By.tagName(HtmlTagType.td.name()));
        if (System.getProperty("os.name").contains("Windows")) {
            log.info("is dev mode");
            log.debug("\n\n<TD INFO> size={}", tds.size());
            for (int i = 0; i < tds.size(); i++) {
                log.info("idx = {}, text = {}", i, tds.get(i).getText());
            }
        } else {
            // log.info("is not dev mode");

        }

        return true;
    }


    public PostDto setHttpInfoToPostDto(PostDto postDto, WebElement titleElement) throws SSLException {
        String contentId = getContentId(titleElement, postDto);
        postDto.setContentId(contentId);
        setParameterMap(postDto);
        Map<String,String> cookies = new HashMap<>();
        driver.manage().getCookies().forEach(cookie -> {
            log.info("cookie = {}", cookie);
            cookies.put(cookie.getName(),cookie.getValue());
        });
        postDto.setWebClientType(getWebClientType());
        postDto.setCookies(cookies);
        postDto.setParameters(getParameters(postDto));
        postDto.setHeaders(makePostDtoHeaderMap(titleElement, postDto));
        postDto.getHeaders().put(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8");


        String href = convertIdToHref(contentId, postDto);
        postDto.setDocumentHref(href);
        Document document = Jsoup.parse(getDetailHtml(href, postDto));
        SystemUtil.testLog("document = {}", document);
        postDto.setDocument(document);

        postDto.setMethod(getHttpMethod());
        postDto.setEndpoint(href);

        return postDto;
    }

    public void setParameterMap(PostDto postDto) {
        postDto.setParameterMap(new LinkedMultiValueMap<>());
    }

    public String getParameters(PostDto postDto) {
        return CrawlerUtil.multiValueMapToJsonString(postDto.getParameterMap());
    }
    public Map<String, String> makePostDtoHeaderMap(WebElement element, PostDto postDto) {
        return new HashMap<>();
    }

    public String getHttpMethod() {
        return null;
    }

    public WebClientType getWebClientType() {
        return WebClientType.DEFAULT;
    }

    public String convertIdToHref(String contentId) {
        return contentId;
    }

    public String convertIdToHref(String contentId, PostDto postDto) {
        return contentId;
    }

    public String getContentId(WebElement titleElement) {
        return titleElement
                .findElement(By.tagName(HtmlTagType.a.name()))
                .getAttribute("href");
    }

    public String getContentId(WebElement titleElement, PostDto postDto) {
        return titleElement
                .findElement(By.tagName(HtmlTagType.a.name()))
                .getAttribute("href");
    }

    public String getDetailHtml(String href) throws SSLException {
        return href;
    }


    public String getDetailHtml(String href, PostDto postDto) throws SSLException {//변경시 SdmGosiCrawler 참고
        // WebClient 생성 (일반 WebClient 사용) ===================== 시작
        if (System.getProperty("os.name").contains("Windows")) {
            log.info("webClient req uri = {}", href);
        }

        return WebClientUtil.requestHtmlData(postDto.getWebClientType(), href, postDto.getHeaders(),HttpMethod.valueOf(getHttpMethod()),postDto.getParameterMap());
    }

    protected String getTitle(List<WebElement> tds) throws SSLException {
        return tds.get(columnIndex.getTitleIndex()).getText();
    }

    protected String getContent(WebElement detailButton) throws SSLException {
        return "no content";
    }


    protected void moveSite() throws InterruptedException {

        driver.get(basePathUrl);
    }



    public WebElement getTargetPageButton(int targetPageNumber, String xpath) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

        List<WebElement> pageButtons = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(xpath)));

        for (WebElement pageButton : pageButtons) {
            String pageNumber = pageButton.getText();
            if (!NumberUtil.isNumberBetween0And9(pageNumber) && System.getProperty("os.name").contains("Windows")) {
                // log.info("{} is not a number", pageNumber);
                continue;
            } else {
                // log.info("{} is a number", pageNumber);
                try {
                    pageNumber = pageNumber.replaceAll("[^0-9]", "");
                    Integer.parseInt(pageNumber);
                } catch (NumberFormatException e) {
                    // log.info("{} is not a number!!!!!", pageNumber);
                    continue;
                }
            }

            int curPageNumber = Integer.parseInt(pageNumber);
            if (System.getProperty("os.name").contains("Windows")) {
                log.info("current page number is {}", curPageNumber);
            }
            if (curPageNumber == targetPageNumber) {
                // log.info("curPageNumber {} == targetPageNumber {}", curPageNumber, targetPageNumber);
                return pageButton;
            }
        }

        throw new BusinessException(CrawlerErrorCode.CANNOT_GET_TARGET_PAGE_BUTTON);
    }
    public void movePage(int targetPage) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,3000)");
        Thread.sleep(3000);
        if (skipMovePage) {
            return;
        }
        if (targetPage == 1) {
            return;
        }
        WebElement targetPageButton = getTargetPageButton(targetPage, pageButtonXpath);
        targetPageButton.click();
    }

    public void waitForPageLoad(WebDriver driver) throws InterruptedException {
        Thread.sleep(5000);
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        int timeout = 30; // 최대 대기 시간 (초)
        int pollingInterval = 500; // 상태 확인 주기 (밀리초)
        int elapsedTime = 0;

        while (elapsedTime < timeout * 1000) {
            try {
                // `document.readyState` 값 확인
                String readyState = jsExecutor.executeScript("return document.readyState").toString();
                if ("complete".equals(readyState)) {
                    break; // 페이지 로드 완료
                }
                Thread.sleep(pollingInterval); // 대기 후 다시 확인
                elapsedTime += pollingInterval;
            } catch (InterruptedException e) {
                throw e;
            }
        }

        if (elapsedTime >= timeout * 1000) {
            throw new RuntimeException("페이지 로드 시간 초과");
        }
    }
    public void validateMoveComplete(int targetPageNumber) throws InterruptedException {


    }

    public void afterJob(List<PostDto> posts) {
        for (PostDto post : posts) {
            log.info("post = {}", post);
        }


    }
}
