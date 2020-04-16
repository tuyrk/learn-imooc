package com.tuyrk.lesson02;

/**
 * 消息传输格式化转换接口
 *
 * @author tuyrk
 */
@FunctionalInterface
public interface IMessageFormat {
    /**
     * 消息转换方法
     *
     * @param message 要转换的消息
     * @param format  转换的格式[xml/json]
     * @return 返回转换后的数据
     */
    String format(String message, String format);

    /**
     * 消息合法性验证方法
     *
     * @param msg 要验证的消息
     * @return 返回验证结果
     */
    static boolean verifyMessage(String msg) {
        return msg != null;
    }
}
