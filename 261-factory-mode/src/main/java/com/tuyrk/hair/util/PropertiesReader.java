package com.tuyrk.hair.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * properties文件的读取工具
 *
 * @author tuyrk
 */
public class PropertiesReader {

    /**
     * 获取配置文件的属性
     *
     * @return 属性Map集合
     */
    public static Map<String, String> getProperties() {
        Map<String, String> map = new HashMap<>();
        try {
            Resource resource = new ClassPathResource("application.properties");
            Properties props = PropertiesLoaderUtils.loadProperties(resource);
            Enumeration<?> en = props.propertyNames();
            while (en.hasMoreElements()) {
                String key = (String) en.nextElement();
                String property = props.getProperty(key);
                map.put(key, property);
                /*System.out.println(key + " " + property);*/
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }
}
