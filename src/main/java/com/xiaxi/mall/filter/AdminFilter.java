package com.xiaxi.mall.filter;


import com.xiaxi.mall.model.pojo.User;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;


public class AdminFilter implements Filter {
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
        User user = (User) session.getAttribute("user");
        if (user == null) {
            PrintWriter writer =new HttpServletResponseWrapper((HttpServletResponse) servletResponse).getWriter();
            writer.write("""
                    {
                    "status":10007,
                    "msg":需要登陆,
                    "data":null\
                    }""");
            writer.flush();
            writer.close();
            return;
        } else if (user.getRole() != 2) {
            PrintWriter writer =new HttpServletResponseWrapper((HttpServletResponse) servletResponse).getWriter();
            writer.write("""
                    {
                    "status":10009,
                    "msg":非管理员用户,
                    "data":null\
                    }""");
            writer.flush();
            writer.close();
            return;
        } else if (user.getRole() == 2) {
            filterChain.doFilter(req, resp);
        }

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
