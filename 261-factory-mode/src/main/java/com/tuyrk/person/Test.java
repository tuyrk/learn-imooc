package com.tuyrk.person;

/**
 * 模拟客户端实现
 *
 * @author tuyrk
 */
public class Test {
    public static void main(String[] args) {
        // 圣诞女孩
        PersonFactory mcFactory = new MCFactory();
        Girl girl = mcFactory.getGirl();
        girl.drawWomen();// 圣诞女孩

        // 新年男孩
        PersonFactory hnFactory = new HNFactory();
        Boy boy = hnFactory.getBoy();
        boy.drawMan();// 新年男孩
    }
}
