package com.tuyrk.garbled.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 解决乱码
 *
 * @author tuyrk
 */
@Controller
@RequestMapping("/")
public class CodeController {

    @GetMapping("form")
    public String form() {
        return "html/form";
    }

    @PostMapping("code")
    public String code(HttpServletRequest request) {
        // 获取姓名
        String username = request.getParameter("username");
        System.out.println(username);// ç¥ç§çå°å²å²
        // 获取爱好
        String[] hobbies = request.getParameterValues("hobby");
        System.out.println(Arrays.toString(hobbies));// [æ½ç, åé, ç«å¤´]

        /*byte[] bytes = username.getBytes(StandardCharsets.ISO_8859_1);
        String usernameNew = new String(bytes, StandardCharsets.UTF_8);*/
        /*System.out.println(new String(username.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));// 神秘的小岛岛
        for (String hobby : hobbies) {
            System.out.println(new String(hobby.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));// 抽烟, 喝酒, 烫头
        }*/
        /*System.out.println(CodeUtil.newCode(username));// 神秘的小岛岛
        for (String hobby : hobbies) {
            System.out.println(CodeUtil.newCode(hobby));// 抽烟, 喝酒, 烫头
        }*/
        return "";
    }
}
