package com.daniu.grab;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.daniu.entity.Imgs;
import com.daniu.service.impl.ImgsServiceImpl;
import com.daniu.utils.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class SaveUrl {
    @Autowired
    private ImgsServiceImpl imgsService;

    public void doSave(PoolingHttpClientConnectionManager connectionManager, String url) throws Exception {
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();
        CloseableHttpResponse httpResponse = HttpClientUtils.createHttpResponse(url, httpClient);
        HttpEntity entity = httpResponse.getEntity();
        if (entity != null) {
            byte[] bytes = EntityUtils.toByteArray(entity);
            if (bytes.length >= 10240) {
                LambdaQueryWrapper<Imgs> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(null != url, Imgs::getUrl, url);
                log.info(imgsService.list(wrapper).toString());
                if ("[]" == imgsService.list(wrapper).toString()) {
                    log.info("--------------");
                    Imgs img = new Imgs();
                    img.setUrl(url);
                    imgsService.save(img);
                    log.info(img.toString());
                    log.info("++++++++++++++");
                }
                TimeUnit.SECONDS.sleep(2);
            }
        }
        httpResponse.close();
        EntityUtils.consume(entity);
//        httpClient.close();不需要关闭
    }
}


