package com.tuyrk.jdk11;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 3-10 JDK11 中新增加的常用 API
 * java.nio.file.Files
 *
 * @author tuyrk
 */
public class FilesExample {
    public static void main(String[] args) throws IOException {
        String path = "jdk11_new_feature.txt";
        Files.writeString(Path.of(path), "jdk11 new feature", StandardCharsets.UTF_8);
        System.out.println(Files.readString(Paths.get(path), StandardCharsets.UTF_8));
    }
}
