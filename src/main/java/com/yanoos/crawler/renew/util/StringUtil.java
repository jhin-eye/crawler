package com.yanoos.crawler.renew.util;

public class StringUtil {
    //괄호안 문자 꺼내기
    public static String getStringInParentheses(String str) {
        return str.substring(str.indexOf("(") + 1, str.indexOf(")"));
    }
}
