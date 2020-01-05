package com.tuyrk.garbled.filter;

import com.tuyrk.garbled.decorator2.NewDecorator;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 编码转换Filter
 *
 * @author tuyrk
 */
@WebFilter(filterName = "code", urlPatterns = "/code")
public class CodeFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        /*HttpServletRequest codeRequest = new Parameter(new ParameterValues(request));*/
        // 简化方式
        HttpServletRequest codeRequest = new NewDecorator(request);
        filterChain.doFilter(codeRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
