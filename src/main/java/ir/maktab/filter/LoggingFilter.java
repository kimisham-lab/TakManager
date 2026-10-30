package ir.maktab.filter;

import ir.maktab.service.LogService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.http.HttpServletRequest;


import java.io.IOException;
@WebFilter("/*")
@WebInitParam(name = "logPrefix", value = "...")
public class LogginFilter implements Filter {
    private LogService logService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String prefix =
                filterConfig.getInitParameter("logPrefix");

        logService = new LogService();

        logService.setPrefix(prefix);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest =
                (HttpServletRequest) servletRequest;

        String method = httpRequest.getMethod();
        String uri = httpRequest.getRequestURI();

        logService.Log("BEFORE " + method + " " + uri);

        filterChain.doFilter(servletRequest, servletResponse);

        logService.Log("AFTER " + method + " " + uri);

    }
}
