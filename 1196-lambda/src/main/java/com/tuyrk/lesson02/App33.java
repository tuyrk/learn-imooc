package com.tuyrk.lesson02;

/**
 * 3-3 Lambda表达式和函数式接口的关系
 *
 * @author tuyrk
 */
public class App33 {
    public static void main(String[] args) {
        // 1. 匿名内部类，实现接口的抽象方法
        IUserCredential ic2 = new IUserCredential() {
            @Override
            public String verifyUser(String username) {
                return "admin".equals(username) ? "管理员" : "会员";
            }
        };
        System.out.println(ic2.verifyUser("manager"));
        System.out.println(ic2.verifyUser("admin"));

        // 2. Lambda表达式，针对函数式接口的简单实现
        IUserCredential ic3 = (username) -> {
            return "admin".equals(username) ? "lbd管理员" : "lbd会员";
        };
        System.out.println(ic3.verifyUser("manager"));
        System.out.println(ic3.verifyUser("admin"));
    }
}
