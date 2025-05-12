package com.yanoos.crawler.util.util.webclient.factory;

import javax.net.ssl.SSLException;
import java.io.IOException;

public class WebClientProvider {
    public static org.springframework.web.reactive.function.client.WebClient getWebClient(WebClientType webClientType) throws SSLException {
        if (webClientType == WebClientType.IGNORE_SSL) {
            return new IgnoreSSLWebClientFactory().createWebClient();
        }
        return new DefaultWebClientFactory().createWebClient();
    }
}
