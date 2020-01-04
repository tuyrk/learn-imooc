package com.tuyrk.garbled.decorator;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

/**
 * 具体装饰-getParameterValues
 * 需要继承装饰器的类
 *
 * @author tuyrk
 */
public class ParameterValues extends Decorators {
    public ParameterValues(HttpServletRequest request) {
        super(request);
    }

    /**
     * 对getParameterValues进行增强
     *
     * @param name key
     * @return values
     */
    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        for (int i = 0; i < values.length; i++) {
            byte[] bytes = values[i].getBytes(StandardCharsets.ISO_8859_1);
            values[i] = new String(bytes, StandardCharsets.UTF_8);
        }
        return values;
    }
}
