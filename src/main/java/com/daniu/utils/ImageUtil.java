package com.daniu.utils;

import com.daniu.utils.HttpClientUtils;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;

@Component
public class ImageUtil {
    @Autowired
    private static PoolingHttpClientConnectionManager connectionManager;

    public static String urlToBase64(String url) {
        try {
            CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();
            CloseableHttpResponse httpResponse = HttpClientUtils.createHttpResponse(url, httpClient);
            InputStream inputStream = httpResponse.getEntity().getContent();
            byte[] bytes = readAllBytes(inputStream);
            String base64Img = Base64.getEncoder().encodeToString(bytes);
            return base64Img;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] readAllBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[8192];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        return outputStream.toByteArray();
    }

    public static void Base64ToImage(String base64Img) throws Exception {
        // 将Base64字符串解码为字节数组
        byte[] imageBytes = Base64.getDecoder().decode(base64Img);

        // 将字节数组转换为BufferedImage
        BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageBytes));

        // 将BufferedImage保存为图片文件
        File outputFile = new File("D:\\IntelliJ IDEA\\idea-wprkspace\\x-admin-web\\src\\main\\resources\\output.png");
        ImageIO.write(img, "png", outputFile);
    }


    /*public static void main(String[] args) throws Exception {
        String url = "https://w.wallhaven.cc/full/rd/wallhaven-rddgwm.jpg";
        String base64Img = urlToBase64(url);
        System.out.println(base64Img);
        Base64ToImage(base64Img);
    }*/
}

