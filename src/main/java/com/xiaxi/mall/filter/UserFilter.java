package com.xiaxi.mall.filter;

import com.xiaxi.mall.model.pojo.User;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class UserFilter implements Filter {

    private static final ThreadLocal<User> currentUserThreadLocal = new ThreadLocal<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json;charset=utf-8");
        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute("user");
        currentUserThreadLocal.set(currentUser);
        if (currentUser == null) {
            PrintWriter writer =new HttpServletResponseWrapper((HttpServletResponse) servletResponse).getWriter();
            writer.write("""
                    {
                    "status":10007,
                    "msg":需要登陆,
                    "data":null\
                    }""");
            writer.flush();
            writer.close();
        }else{
            filterChain.doFilter(req, resp);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    public static User getCurrentUser() {
        return currentUserThreadLocal.get();
    }
    public static void clear() {
        currentUserThreadLocal.remove();
    }
}
