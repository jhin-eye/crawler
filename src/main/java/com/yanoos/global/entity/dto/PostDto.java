package com.yanoos.global.entity.dto;

import com.yanoos.crawler.util.dto.ColumnIndexDto;
import com.yanoos.crawler.util.util.SystemUtil;
import com.yanoos.crawler.util.util.webclient.factory.WebClientType;
import com.yanoos.global.util.TimeUtil;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
@Slf4j
public class PostDto {
    private WebElement row;
    private Long id;
    private BoardDto boardDto;
    private String no;
    private String title;
    private String content;
    private ZonedDateTime writeDate;
    private String department;
    private String url;
    private ZonedDateTime monitorTime;
    private String method;
    private String endpoint;
    private String parameters;
    private MultiValueMap<String, String> parameterMap;
    private Map<String, String> headers;
    private String documentHref;
    private Document document;
    private WebClientType webClientType;

    private WebClientType attachmentWebClientType;
    private HttpMethod attachmentMethod;
    private List<FileDto> fileDtos;
    private String contentId;
    private Map<String, String> cookies;

    public static PostDto from(WebElement row, ColumnIndexDto columnIndexDto, String listTag) {
        if (listTag.equalsIgnoreCase("li")) {
            return fromLi(row, columnIndexDto);
        } else if (listTag.equalsIgnoreCase("tr")) {
            return fromTr(row, columnIndexDto);
        } else if (listTag.equalsIgnoreCase("ul")) {
            return fromUl(row, columnIndexDto);
        } else if (listTag.equalsIgnoreCase("geochangGosi")) {
            return fromGeoChangGosi(row, columnIndexDto);
        } else if (listTag.equalsIgnoreCase("geochangNotice")) {
            return fromGeoChangNotice(row, columnIndexDto);
        }else {
            throw new IllegalArgumentException("listTag is not valid");
        }
    }

    private static PostDto fromGeoChangNotice(WebElement row, ColumnIndexDto columnIndexDto) {
        List<WebElement> tops = row
                .findElement(By.cssSelector(":scope > div"))
                .findElement(By.cssSelector(":scope > a"))
                .findElements(By.cssSelector(":scope > span"));

        WebElement top = tops.size()==1? tops.get(0) : tops.get(1);
        String title = top.findElement(By.tagName("strong")).getText();
        SystemUtil.testLog("title: {}", title);
        List<WebElement> i = top.findElements(By.cssSelector(":scope > i"));
        String[] columns = i.get(0).getText().split(" ");

        for (int j = 0; j < columns.length; j++) {
            SystemUtil.testLog("columns[{}]: {}", j, columns[j]);
        }

        String no = columns[columnIndexDto.getNoIndex()];
        String department = columnIndexDto.getDepartmentIndex() == -1 ? null : columns[columnIndexDto.getDepartmentIndex()];
        ZonedDateTime writeDate = columnIndexDto.getWriteDateIndex() == -1 ? null : TimeUtil.parseWriteDateToZonedDatetime(columns[columnIndexDto.getWriteDateIndex()]);
        return PostDto.builder()
                .row(row)
                .no(no)
                .title(title)
                .department(department)
                .writeDate(writeDate)
                .build();
    }

    private static PostDto fromGeoChangGosi(WebElement row, ColumnIndexDto columnIndexDto) {
        String[] split = row.getText().split("\n");
        String title = split[0];//0고정
        String text = split[1];//1을 기준으로 나누기
        SystemUtil.testLog("text: {}", text);
        // 정규표현식으로 키:값 쌍 추출
        Pattern pattern = Pattern.compile("(고시번호|등록일|공고기간|담당부서)\\s*:\\s*(.*?)(?=\\s+(고시번호|등록일|공고기간|담당부서)|$)");


        Matcher matcher = pattern.matcher(text);
        Map<String, String> result = new LinkedHashMap<>(); // 순서를 유지하고 싶으면 LinkedHashMap

        while (matcher.find()) {
            String key = matcher.group(1).trim();
            String value = matcher.group(2).trim();
            result.put(key, value);
        }
        String[] resultArr = result.values().toArray(new String[0]);
        for(int i=0;i<resultArr.length;i++){
            SystemUtil.testLog("resultArr[{}]: {}", i, resultArr[i]);
        }

        String no = resultArr[columnIndexDto.getNoIndex()];
        String department = columnIndexDto.getDepartmentIndex() == -1 ? null : resultArr[columnIndexDto.getDepartmentIndex()];
        ZonedDateTime writeDate = columnIndexDto.getWriteDateIndex() == -1 ? null : TimeUtil.parseWriteDateToZonedDatetime(resultArr[columnIndexDto.getWriteDateIndex()]);
        return PostDto.builder()
                .row(row)
                .no(no)
                .title(title)
                .department(department)
                .writeDate(writeDate)
                .build();
    }

    private static PostDto fromTr(WebElement row, ColumnIndexDto columnIndexDto) {
        List<WebElement> columns = row.findElements(By.tagName("td"));
        return getPostDto(row, columnIndexDto, columns);
    }
    private static PostDto fromUl(WebElement row, ColumnIndexDto columnIndexDto) {
        List<WebElement> columns = row.findElements(By.tagName("li"));

        return getPostDto(row, columnIndexDto, columns);
    }

    private static PostDto fromLi(WebElement row, ColumnIndexDto columnIndexDto) {
        String[] split = row.getText().split("\n");

        String no = split[columnIndexDto.getNoIndex()];
        String title = split[columnIndexDto.getTitleIndex()];
        String department = columnIndexDto.getDepartmentIndex() == -1 ? null : split[columnIndexDto.getDepartmentIndex()];
        ZonedDateTime writeDate = columnIndexDto.getWriteDateIndex() == -1 ? null : TimeUtil.parseWriteDateToZonedDatetime(split[columnIndexDto.getWriteDateIndex()]);

        return PostDto.builder()
                .row(row)
                .no(no)
                .title(title)
                .department(department)
                .writeDate(writeDate)
                .build();
    }

    private static PostDto getPostDto(WebElement row, ColumnIndexDto columnIndexDto, List<WebElement> columns) {
        String no = columns.get(columnIndexDto.getNoIndex()).getText();
        String title = columns.get(columnIndexDto.getTitleIndex()).getText();
        String department = columnIndexDto.getDepartmentIndex() == -1 ? null : columns.get(columnIndexDto.getDepartmentIndex()).getText();
        ZonedDateTime writeDate = columnIndexDto.getWriteDateIndex() == -1 ? null : TimeUtil.parseWriteDateToZonedDatetime(columns.get(columnIndexDto.getWriteDateIndex()).getText());

        return PostDto.builder()
                .row(row)
                .no(no)
                .title(title)
                .department(department)
                .writeDate(writeDate)
                .build();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostDto postDto = (PostDto) o;
        return Objects.equals(boardDto.getId(), postDto.boardDto.getId()) &&
                Objects.equals(no, postDto.no) &&
                Objects.equals(title, postDto.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(boardDto.getId(), no, title);
    }

    public String toString() {
        String contentPreview = (content != null)
                ? content.substring(0, Math.min(100, content.length()))
                : "null";
        return "\n===============================\n" +
                "no: " + no + "\n" +
                "title: " + title + "\n" +
                "writeDate: " + writeDate + "\n" +
                "endpoint: " + endpoint + "\n" +
                "content: " + contentPreview + "\n" +
                "===============================\n";
    }


    public void systemLog() {
        SystemUtil.testLog("title = {}", this.getTitle());
        SystemUtil.testLog("endPoint = {}", this.getEndpoint());
        SystemUtil.testLog("method = {}", this.getMethod());
        SystemUtil.testLog("parameters = {}", this.getParameters());
    }


}
