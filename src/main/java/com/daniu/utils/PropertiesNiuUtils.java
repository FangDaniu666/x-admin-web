package com.daniu.utils;

import java.io.*;
import java.util.Properties;

public class PropertiesNiuUtils {

    public static void PropertiesOutputStream(Properties properties, String configProperties, String property, String value) throws IOException {
        //PropertiesUtils保存nextPage到资源文件夹下的nextPage.properties
        properties.setProperty(property, value);
        OutputStream out = new FileOutputStream(configProperties);
        // 存储键值对到文件中
        properties.store(out, "This is PageNumber");
        // 关闭输出流
        out.close();
    }

    public static void copyProperties(Properties properties, String outputPath) throws IOException {
        File outputFile = new File(outputPath);
        if (!outputFile.exists()) {
            outputFile.createNewFile();
        }
        try (OutputStream outputStream = new FileOutputStream(outputFile)) {
            properties.store(outputStream, null);
        }
    }
}
