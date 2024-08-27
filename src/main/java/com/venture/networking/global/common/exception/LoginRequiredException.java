package com.venture.networking.global.common.exception;

import com.venture.networking.global.common.exception.code.ErrorCode;

public class LoginRequiredException extends BaseException {
    public LoginRequiredException() {
        super(ErrorCode.LOGIN_REQUIRED.getMessage());
    }
}
