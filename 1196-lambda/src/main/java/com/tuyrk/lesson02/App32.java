package com.tuyrk.lesson02;

import com.tuyrk.lesson02.impl.MessageFormatImpl;
import com.tuyrk.lesson02.impl.UserCredentialImpl;

import java.util.UUID;
import java.util.function.*;

/**
 * 3-2 默认方法和静态方法
 * 需求改动：
 * 所有的用户验证，可以同时获取用户的验证信息[是否认证成功|成功~返回用户|null]
 *
 * @author tuyrk
 */
public class App32 {
    public static void main(String[] args) {
        IUserCredential ic = new UserCredentialImpl();
        System.out.println(ic.verifyUser("admin"));
        System.out.println(ic.getCredential("admin"));

        String msg = "hello world";
        if (IMessageFormat.verifyMessage(msg)) {
            IMessageFormat format = new MessageFormatImpl();
            System.out.println(format.format("hello", "json"));
        }
    }
}
