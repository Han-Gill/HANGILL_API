package com.koreatech.hangill.controller;

import com.koreatech.hangill.exception.global.DataNotFoundException;
import com.koreatech.hangill.exception.global.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.http.HttpStatus.*;

/**
 * @RestControllerAdvice : 전역적으로 @ExceptionHandler를 적용할 수 있는 어노테이션, 객체를 리턴할 수 있음 => try-catch 문으로 Controller가 더러워지는 것 방지.
 * @ExceptionHandler : 보통 컨트롤러에 추가하는 어노테이션으로 컨트롤러에서 발생하는 예외를 잡아서 대신 처리해주도록 하는 어노테이션
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // Validation 예외 처리 메소드
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        log.warn("잘못된 요청 매개변수 에러!\n {}, \n {}", ex.getMessage(), ex.getStackTrace());
        // BindingResult 로부터 에러메시지들 뽑아내기
        String errorMessages = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("\n"));
        return ResponseEntity.badRequest().body(ErrorResponse.from(errorMessages));
    }
    // 예상치 못한 에러 발생 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllException(Exception ex) {
        log.error("예상치 못한 에러 발생!\n {} \n {}", ex.getMessage(), ex.getStackTrace());
        return ResponseEntity.internalServerError().body(ErrorResponse.from("예상치 못한 에러 발생"));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalStateException(
            IllegalStateException ex,
            HttpServletRequest request
    ) {
        log.warn("상태 에러 발생!\n {}, \n {}", ex.getMessage(), ex.getStackTrace());
        return ResponseEntity.badRequest().body(ErrorResponse.from(ex.getMessage()));
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDataNotFoundException(
            DataNotFoundException ex,
            HttpServletRequest request
    ){
        log.warn("데이터 찾을 수 없음 에러 발생!!\n {}, \n {}", ex.getMessage(), ex.getStackTrace());
        return ResponseEntity.status(NOT_FOUND).body(ErrorResponse.from(
                ex.getMessage()
        ));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException ex,
            HttpServletRequest request
    ) {
        log.warn("잘못된 매개변수 예외 발생! \n <stackTrace>\n{}",
                Stream.of(ex.getStackTrace())
                        .map(String::valueOf).map(a -> a + "\n")
                        .toList());

        return ResponseEntity.badRequest().body(ErrorResponse.from(ex.getMessage()));
    }

    private void loggingRequest(HttpServletRequest request) {

    }














}
