package com.yanoos.crawler.util.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.yanoos.crawler.util.constant.HtmlTagType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.util.MultiValueMap;

public class CrawlerUtil {
    public static String getContentIdByOnclickSplitQuot(WebElement detailButton,int splitIdx) {
        String id = detailButton
                .findElement(By.tagName(HtmlTagType.a.name()))
                .getAttribute("onclick");
        id = id.split("'")[splitIdx];
        return id;
    }
    //a태그의 onclick 속성에서 괄호안의 숫자를 추출
    public static String getContentIdByOnclickExtractInsideParentheses(WebElement detailButton) {
        String id = detailButton
                .findElement(By.tagName(HtmlTagType.a.name()))
                .getAttribute("onclick");
        id = id.split("\\(")[1].split("\\)")[0];
        return id;
    }
    public static String getContentIdByHref(WebElement detailButton) {
        String id = detailButton
                .findElement(By.tagName(HtmlTagType.a.name()))
                .getAttribute("href");
        return id;
    }
    public static String getContentIdByHrefSplitQuote(WebElement detailButton,int splitIdx) {
        String id = detailButton
                .findElement(By.tagName(HtmlTagType.a.name()))
                .getAttribute("href");
        return id.split("'")[splitIdx];
    }
    public static String getContentIdByHrefSplitSomeString(WebElement detailButton,String s, int splitIdx) {
        String id = detailButton
                .findElement(By.tagName(HtmlTagType.a.name()))
                .getAttribute("href");

        return id.split(s)[splitIdx];
    }
    public static String getContentIdFromA(WebElement detailButton,String attribute, int splitIdx) {
        String id = detailButton
                .findElement(By.tagName(HtmlTagType.a.name()))
                .getAttribute(attribute);
        if(splitIdx==-1){
            return id;
        }
        return id.split("'")[splitIdx];
    }
    public static String getContentIdFromTrByOnclickSplitQuot(WebElement detailButton, int splitIdx) {
        String id = detailButton
                .getAttribute("onclick");
        id = id.split("'")[splitIdx];
        return id;
    }
    public static String getContentIdByHrefExtractNumber(WebElement detailButton) {
        String href = detailButton
                .findElement(By.tagName(HtmlTagType.a.name()))
                .getAttribute("href");
        return href.replaceAll("[^0-9]","");
    }


    public static String multiValueMapToJsonString(MultiValueMap<String, String> formData) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonObject = objectMapper.createObjectNode();
        for (String key : formData.keySet()) {
            jsonObject.put(key, formData.getFirst(key));
        }
        return jsonObject.toString();
    }
}
