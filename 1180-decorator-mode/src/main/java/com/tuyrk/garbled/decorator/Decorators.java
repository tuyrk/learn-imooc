package com.tuyrk.garbled.decorator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 装饰者组件-装饰器
 * 需要继承模板类
 *
 * @author tuyrk
 */
public abstract class Decorators extends HttpServletRequestWrapper {
    /**
     * 持有接口的引用
     */
    private HttpServletRequest request;

    public Decorators(HttpServletRequest request) {
        super(request);
        this.request = request;
    }

    @Override
    public String getParameter(String name) {
        return super.getParameter(name);
    }

    @Override
    public String[] getParameterValues(String name) {
        return super.getParameterValues(name);
    }
}
