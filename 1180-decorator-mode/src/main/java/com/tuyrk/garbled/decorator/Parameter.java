package com.tuyrk.garbled.decorator;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

/**
 * 具体装饰-getParameter
 * 需要继承装饰器的类
 *
 * @author tuyrk
 */
public class Parameter extends Decorators {
    public Parameter(HttpServletRequest request) {
        super(request);
    }

    /**
     * 对getParameter进行增强
     *
     * @param name key
     * @return value
     */
    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        // 解决乱码
        byte[] bytes = value.getBytes(StandardCharsets.ISO_8859_1);
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
