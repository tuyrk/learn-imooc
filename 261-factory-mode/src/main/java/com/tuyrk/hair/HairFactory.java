package com.tuyrk.hair;

import com.tuyrk.hair.impl.LeftHair;
import com.tuyrk.hair.impl.RightHair;
import com.tuyrk.hair.util.PropertiesReader;

import java.util.Map;

/**
 * 发型工厂
 *
 * @author tuyrk
 */
public class HairFactory {
    /**
     * 根据类型来创建对象
     *
     * @param key 发型种类
     * @return 发型实例
     */
    public HairInterface getHair(String key) {
        if ("left".equals(key)) {
            return new LeftHair();
        } else if ("right".equals(key)) {
            return new RightHair();
        }
        return null;
    }

    /**
     * 根据类的名称来生产对象
     *
     * @param className 类名
     * @return 发型实例
     */
    public HairInterface getHairByClass(String className) {
        try {
            return (HairInterface) Class.forName(className).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据类的名称来生产对象
     *
     * @param key 类名对应名称
     * @return 发型实例
     */
    public HairInterface getHairByClassKey(String key) {
        try {
            Map<String, String> map = PropertiesReader.getProperties();
            return (HairInterface) Class.forName(map.get(key)).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
