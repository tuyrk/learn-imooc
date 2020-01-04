package com.tuyrk.garbled.util;

import java.nio.charset.StandardCharsets;

/**
 * 编码转换
 *
 * @author tuyrk
 */
public class CodeUtil {
    /**
     * ISO-8859-1转UTF-8编码
     *
     * @param codename 需要进行转码的字符串
     * @return 转码后的字符串
     */
    public static String newCode(String codename) {
        byte[] bytes = codename.getBytes(StandardCharsets.ISO_8859_1);
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
