package com.team3webnovel.exception;

import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.validation.BindException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpMediaTypeNotSupportedException;

import jakarta.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 로거 설정
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 400 Bad Request
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleBadRequest(HttpServletRequest request, Exception ex) {
        return handleException(request, ex, "400", "잘못된 요청입니다.");
    }

    // 403 Forbidden - 접근 거부
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView handleAccessDenied(HttpServletRequest request, Exception ex) {
        return handleException(request, ex, "403", "접근이 거부되었습니다.");
    }

    // 404 Not Found - 페이지를 찾을 수 없음
    @ExceptionHandler({NoSuchElementException.class, NoHandlerFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleNotFound(HttpServletRequest request, Exception ex) {
        return handleException(request, ex, "404", "페이지를 찾을 수 없습니다.");
    }

    // 405 Method Not Allowed - 잘못된 HTTP 메서드
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ModelAndView handleMethodNotAllowed(HttpServletRequest request, Exception ex) {
        return handleException(request, ex, "405", "지원되지 않는 HTTP 메서드입니다.");
    }

    // 415 Unsupported Media Type - 지원되지 않는 미디어 타입
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ModelAndView handleUnsupportedMediaType(HttpServletRequest request, Exception ex) {
        return handleException(request, ex, "415", "지원되지 않는 미디어 타입입니다.");
    }

    // 400 Bad Request - 파라미터가 누락된 경우
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleMissingParams(HttpServletRequest request, Exception ex) {
        return handleException(request, ex, "400", "필수 요청 파라미터가 누락되었습니다.");
    }

    // 400 Bad Request - 데이터 바인딩 오류
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleBindException(HttpServletRequest request, Exception ex) {
        return handleException(request, ex, "400", "데이터 바인딩 오류가 발생했습니다.");
    }

    // NullPointerException 처리
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleNullPointerException(HttpServletRequest request, NullPointerException ex) {
        return handleException(request, ex, "500", "NullPointerException이 발생했습니다. 로그인 여부를 확인해주세요.");
    }

    // 파일을 찾을 수 없는 경우
    @ExceptionHandler(FileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleFileNotFound(HttpServletRequest request, FileNotFoundException ex) {
        return handleException(request, ex, "404", "파일을 찾을 수 없습니다.");
    }

    // 500 Internal Server Error - 서버 오류
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleInternalServerError(HttpServletRequest request, Exception ex) {
        return handleException(request, ex, "500", "서버 내부 오류가 발생했습니다.");
    }

    // 모든 예외를 처리하는 핸들러
    @ExceptionHandler(Throwable.class)
    public ModelAndView handleAllExceptions(HttpServletRequest request, Exception ex) {
        return handleException(request, ex, "error", "예기치 않은 오류가 발생했습니다.");
    }

    // 공통적으로 사용하는 예외 처리 메서드
    private ModelAndView handleException(HttpServletRequest request, Exception ex, String errorCode, String errorMessage) {
        // 로그 출력
        logException(request, ex, errorCode, errorMessage);
        
        // ModelAndView 설정
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("status", errorCode);
        modelAndView.addObject("error", errorMessage);
        modelAndView.addObject("message", ex.getMessage());
        modelAndView.addObject("url", request.getRequestURL());
        return modelAndView;
    }

    // 예외 로그 출력 메서드
    private void logException(HttpServletRequest request, Exception ex, String errorCode, String errorMessage) {
        logger.error("Exception occurred at URL: {} | Status: {} | Error: {} | Exception Message: {}", 
                     request.getRequestURL(), errorCode, errorMessage, ex.getMessage());
    }
}