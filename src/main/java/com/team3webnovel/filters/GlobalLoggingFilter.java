package com.team3webnovel.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")  // 모든 URL에 대해 필터 적용
public class GlobalLoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(GlobalLoggingFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("GlobalLoggingFilter 초기화");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 요청 정보 로깅 (IP, 포트, 메소드, URL)
        logger.info("요청이 들어왔습니다: IP={}, Port={}, Method={}, URL={}",
                httpRequest.getRemoteAddr(),
                httpRequest.getRemotePort(),
                httpRequest.getMethod(),
                httpRequest.getRequestURI());

        // 요청 처리 시간 측정을 위한 시작 시간
        long startTime = System.currentTimeMillis();

        try {
            // 다음 필터 또는 서블릿으로 요청을 전달
            chain.doFilter(request, response);
        } catch (Exception e) {
            logger.error("요청 처리 중 오류 발생: {}", e.getMessage(), e);
            throw e; // 예외를 다시 던져 다음 핸들러에서 처리할 수 있도록 함
        }

        // 응답 상태 및 처리 시간 로깅
        long duration = System.currentTimeMillis() - startTime;
        logger.info("응답이 완료되었습니다: Status={}, 처리 시간={} ms", httpResponse.getStatus(), duration);
    }

    @Override
    public void destroy() {
        logger.info("GlobalLoggingFilter 종료");
    }
}
