package com.daniu.grab;

import com.daniu.utils.HttpClientUtils;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetPageUrl {
    @Autowired
    private GetUrl getUrl;
    @Autowired
    private PoolingHttpClientConnectionManager connectionManager;
    public void doGet(int start, int end, String url) throws Exception {
        //放出http请求
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();
        for (int homepage = start; homepage <= end; homepage++) {
            CloseableHttpResponse httpResponse = HttpClientUtils.createHttpResponse(url + homepage, httpClient);
            Document document = HttpClientUtils.createDocument(httpResponse);
            Elements preview = document.getElementsByClass("preview");//根据标签属性
            for (Element pre : preview) {
                String page = pre.attr("href");//根据属性查找
//                System.out.println(page);
                getUrl.doGet(connectionManager, page);
            }
            httpResponse.close();
//        httpClient.close();不需要关闭
        }
    }
}
