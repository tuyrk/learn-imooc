package com.tuyrk.lesson02.impl;

import com.tuyrk.lesson02.IMessageFormat;

/**
 * 消息传输格式化转换
 *
 * @author tuyrk
 */
public class MessageFormatImpl implements IMessageFormat {
    /**
     * 消息转换方法
     *
     * @param message 要转换的消息
     * @param format  转换的格式[xml/json]
     * @return 返回转换后的数据
     */
    @Override
    public String format(String message, String format) {
        System.out.println("消息转换...");
        return message;
    }
}
