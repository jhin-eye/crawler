package com.yanoos.crawler.renew.service;

import com.yanoos.global.entity.dto.PostDto;

import javax.net.ssl.SSLException;
import java.util.List;

public interface Crawler {
    List<PostDto> crawling(int page) throws InterruptedException, SSLException;

    String getBoardNameEng();

    String getBoardNameKor();

    String getBasePathUrl();

    String getSiteUrl();

    String getBoardTypeName();
    String getNameKor();

    int getPageSize();
}
