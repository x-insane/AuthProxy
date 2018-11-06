package com.xinsane.auth.account.filter;

import com.google.gson.Gson;
import com.xinsane.auth.account.helper.StringHelper;
import com.xinsane.auth.account.transfer.ApiResult;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(urlPatterns = "/api/*", filterName = "CsrfFilter")
public class CsrfFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        String csrf_key = req.getHeader("_csrf_key");
        String csrf_token = req.getHeader("_csrf_token");
        if (!req.getMethod().equals("POST") ||
                session == null || csrf_key == null || csrf_token == null ||
                !csrf_token.equals(session.getAttribute(csrf_key))) {
            response.setContentType("application/json; charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(new Gson().toJson(new ApiResult(403, "拒绝访问")));
            writer.flush();
            writer.close();
            return;
        }
        chain.doFilter(request,response);
    }

    /**
     * 前端控制器通过implements该接口来自动注入_csrf变量到模板和session中
     */
    public interface CsrfInterface {
        @ModelAttribute
        default void addCsrfAttribute(Model model, HttpSession session) {
            String key = "_csrf_" + StringHelper.randomString(5);
            String token = StringHelper.randomString(32);
            session.setAttribute(key, token);
            model.addAttribute("_csrf_key", key);
            model.addAttribute("_csrf_token", token);
        }
    }

    @Override
    public void destroy() {

    }
}
