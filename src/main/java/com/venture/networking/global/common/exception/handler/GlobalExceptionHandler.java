package com.venture.networking.global.common.exception.handler;

import com.venture.networking.global.common.exception.BaseException;
import com.venture.networking.global.common.exception.LoginRequiredException;
import com.venture.networking.global.common.exception.RefreshTokenNotValidatedException;
import com.venture.networking.global.common.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BaseException.class)
    public BaseResponse<Void> handleBaseException(BaseException e) {
        log.error("[BaseException] Message = {}", e.getMessage());
        return BaseResponse.fail(e.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = RefreshTokenNotValidatedException.class)
    public BaseResponse<Void> handlerRefreshTokenNotValidatedException(
        RefreshTokenNotValidatedException e
    ) {
        log.error("[RefreshTokenNotValidatedException] Message = {}", e.getMessage());
        return BaseResponse.fail(e.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = LoginRequiredException.class)
    public BaseResponse<Void> handlerLoginRequiredException(
        LoginRequiredException e
    ) {
        log.error("[LoginRequiredException] Message = {}", e.getMessage());
        return BaseResponse.fail(e.getMessage());
    }

}
