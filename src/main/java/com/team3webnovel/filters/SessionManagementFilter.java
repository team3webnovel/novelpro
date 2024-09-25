package com.team3webnovel.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*") // 모든 경로에 대해 필터 적용
public class SessionManagementFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession(false);

        if (session != null && session.getAttribute("user") != null) {
            // 세션 유지 로그
            System.out.println("세션 유지 중: " + session.getAttribute("user"));
        } else {
            System.out.println("세션 없음 또는 유저 정보 없음");
        }

        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 필터 초기화 시 필요한 작업
    }

    @Override
    public void destroy() {
        // 필터가 제거될 때 필요한 작업
    }
}
