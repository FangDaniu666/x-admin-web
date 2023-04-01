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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class GetUrl {
    @Autowired
    private SaveUrl saveUrl;
    public void doGet(PoolingHttpClientConnectionManager connectionManager, String page) throws Exception {
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();
        CloseableHttpResponse httpResponse = HttpClientUtils.createHttpResponse(page, httpClient);
        Document document = HttpClientUtils.createDocument(httpResponse);
        Elements imgs = document.getElementsByTag("img");//根据标签属性
        for (Element img : imgs) {
            String imgSrc = img.attr("src");//根据属性查找
            StringBuilder builder = new StringBuilder("");

            //正则表达式判断是否为http开头
            String regex = "^[http|https]:*";
            Pattern compile = Pattern.compile(regex);
            Matcher matcher = compile.matcher(imgSrc);
            if (!matcher.find()) {
                builder.append("https:");
            }

            //正则表达式获取文件名
            String regexjpg = "(\\w+\\.(jpg))$";
            Pattern compilejpg = Pattern.compile(regexjpg);
            Matcher matcherjpg = compilejpg.matcher(imgSrc);
            if (matcherjpg.find()) {
//                System.out.println(matcherjpg.group(0));
                builder = builder.append(imgSrc);
                String str2 = builder.toString();
//                System.out.println(str2);
                saveUrl.doSave(connectionManager, str2);
            }
        }

        httpResponse.close();
//        httpClient.close();不需要关闭
    }
}
