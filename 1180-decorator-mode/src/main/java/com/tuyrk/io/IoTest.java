package com.tuyrk.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class IoTest {
    public static void main(String[] args) throws FileNotFoundException {
        // 构件抽象路径
        File file = new File("aaa.txt");
        // 公共file实例构件aaa.txt文件
        FileInputStream fis = new FileInputStream(file);
        // 使用缓冲输入流
        BufferedInputStream bis = new BufferedInputStream(fis);
    }
}