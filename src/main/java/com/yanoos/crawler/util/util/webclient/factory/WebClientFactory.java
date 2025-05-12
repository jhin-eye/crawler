package com.yanoos.crawler.util.util.webclient.factory;

import org.springframework.web.reactive.function.client.WebClient;

import javax.net.ssl.SSLException;
import java.io.IOException;

public interface WebClientFactory {
    WebClient createWebClient() throws SSLException;
}
