package com.tuyrk.garbled.decorator2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.nio.charset.StandardCharsets;

/**
 * 装饰者组件-装饰器
 * 需要继承模板类
 *
 * @author tuyrk
 */
public class NewDecorator extends HttpServletRequestWrapper {
    /**
     * 持有接口的引用
     */
    private HttpServletRequest request;

    public NewDecorator(HttpServletRequest request) {
        super(request);
        this.request = request;
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
        byte[] bytes = value.getBytes(StandardCharsets.ISO_8859_1);
        return new String(bytes, StandardCharsets.UTF_8);
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
