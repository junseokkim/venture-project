package com.venture.networking.global.common.exception;

import com.venture.networking.global.common.exception.code.ErrorCode;

public class RefreshTokenNotValidatedException extends BaseException {
    public RefreshTokenNotValidatedException() {
        super(ErrorCode.REFRESH_TOKEN_NOT_VALIDATED.getMessage());
    }
}
