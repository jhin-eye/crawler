package com.yanoos.crawler.renew.util;

import com.yanoos.crawler.util.dto.ColumnIndexDto;
import com.yanoos.crawler.util.util.SystemUtil;
import com.yanoos.global.entity.dto.PostDto;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class PostParser {
    public static List<PostDto> makeBasePostDtoList(WebDriver driver, List<WebElement> rows, ColumnIndexDto columnIndex, String listTag) {
        List<PostDto> postDtos = new ArrayList<>();
        for (WebElement row : rows) {
            logColumns(row, listTag);// 컬럼 인덱스 확인용
            postDtos.add(PostDto.from(row, columnIndex, listTag));
        }
        return postDtos;
    }


    public static void logColumns(WebElement row, String listTag) {
        SystemUtil.testLog(" ");
        if (listTag.equalsIgnoreCase("tr")) {
            logTrColumns(row);
        } else if (listTag.equalsIgnoreCase("li")) {
            logLiColumns(row);
        } else if (listTag.equalsIgnoreCase("ul")) {
            logUlColumns(row);
        } else if (listTag.equalsIgnoreCase("geochangGosi")) {
            logGeochangGosiColumns(row);
        } else if (listTag.equalsIgnoreCase("geochangNotice")) {
            //
        } else {
            throw new IllegalArgumentException("listTag is not valid");
        }
    }

    private static void logUlColumns(WebElement row) {
        List<WebElement> columns = row.findElements(By.tagName("li"));
        for (int i = 0; i < columns.size(); i++) {
            SystemUtil.testLog(makeColumnString(i, columns.get(i).getText()));
        }
    }

    private static void logGeochangGosiColumns(WebElement row) {
    }

    private static void logTrColumns(WebElement row) {
        List<WebElement> columns = row.findElements(By.tagName("td"));
        for (int i = 0; i < columns.size(); i++) {
            SystemUtil.testLog(makeColumnString(i, columns.get(i).getText()));
        }
    }

    private static void logLiColumns(WebElement row) {
        String[] split = row.getText().split("\n");
        for (int i = 0; i < split.length; i++) {
            SystemUtil.testLog(makeColumnString(i, split[i]));
        }
    }

    private static String makeColumnString(int idx, String columnText) {
        return String.format("column[%d]: %s", idx, columnText);
    }


}
