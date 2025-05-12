package com.yanoos.crawler.renew.service;

public abstract class AbstractCrawler implements Crawler{
    private final String boardNameEng;
    private final String boardNameKor;
    private final String basePathUrl;
    private final String siteUrl;
    private final String boardTypeName;
    private final int pageSize;

    public AbstractCrawler(String boardNameEng, String boardNameKor, String basePathUrl, String siteUrl, String boardTypeName, int pageSize) {
        this.boardNameEng = boardNameEng;
        this.boardNameKor = boardNameKor;
        this.basePathUrl = basePathUrl;
        this.siteUrl = siteUrl;
        this.boardTypeName = boardTypeName;
        this.pageSize = pageSize;

    }

    @Override
    public String getBoardNameEng() {
        return boardNameEng;
    }

    @Override
    public String getBoardNameKor() {
        return boardNameKor;
    }

    @Override
    public String getBasePathUrl() {
        return basePathUrl;
    }

    @Override
    public String getSiteUrl() {
        return siteUrl;
    }
    @Override
    public String getBoardTypeName() {
        return boardTypeName;
    }
    @Override
    public String getNameKor() {
        return boardNameKor;
    }
    @Override
    public int getPageSize() {
        return pageSize;
    }
}
