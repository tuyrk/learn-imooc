package com.tuyrk.jdk11;

/**
 * 3-10 JDK11 中新增加的常用 API
 * java.lang.String
 *
 * @author tuyrk
 */
public class StringExample {
    public static void main(String[] args) {
        String example = "Hello, JDK11\u3000";
        String empty = "\u3000";
        System.out.println("原始字符：" + example);
        // 对比trim和strip
        System.out.println("trim -> 删除字符串的头尾空白符" + example.trim());
        System.out.println("strip -> 删除字符串的头尾空白符" + example.strip());
        System.out.println("stripLeading -> 删除字符串的头部空白符" + example.stripLeading());
        System.out.println("stripTrailing -> 删除字符串的尾部空白符" + example.stripTrailing());

        // 对比isEmpty和isBlank
        System.out.println("isEmpty -> 字符串长度是否为0" + empty.isEmpty());
        System.out.println("isBlank -> 是否为空或仅包含空格" + empty.isBlank());

        // lines
        String lines = "hello\nworld\njdk11";
        lines.lines().forEach(System.out::println);

        // repeat
        System.out.println("*".repeat(10));
    }
}
