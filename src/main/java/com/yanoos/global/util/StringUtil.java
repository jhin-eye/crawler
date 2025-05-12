package com.yanoos.global.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    // 클래스 이름을 카멜케이스로 변환하는 메서드
    public static String toCamelCase(String s) {
        String[] parts = s.split("_");
        StringBuilder camelCaseString = new StringBuilder(parts[0].substring(0, 1).toUpperCase() + parts[0].substring(1));
        for (int i = 1; i < parts.length; i++) {
            camelCaseString.append(parts[i].substring(0, 1).toUpperCase()).append(parts[i].substring(1));
        }
        return camelCaseString.toString();
    }

    //작은 따옴표로 묶인 부분들만 String List로 추출
    public static List<String> extractSingleQuote(String s) {
        Pattern pattern = Pattern.compile("'([^']*)'");
        Matcher matcher = pattern.matcher(s);

        List<String> result = new ArrayList<>();
        while (matcher.find()) {
            result.add(matcher.group(1));
        }
        return result;
    }

    //문자열에서 확장자 제거
    public static String removeFileExtension(String fileName){
        if(fileName == null || !fileName.contains(".")){
            return fileName;
        }
        return fileName.substring(0,fileName.lastIndexOf("."));
    }
    //문자열에서 확장자 추출
    public static String extractFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return ""; // 확장자가 없거나 null이면 빈 문자열 반환
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1); // 마지막 점 이후 문자열 반환
    }

}
