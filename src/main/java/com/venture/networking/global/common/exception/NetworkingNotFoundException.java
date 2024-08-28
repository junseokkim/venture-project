package com.venture.networking.global.common.exception;

import com.venture.networking.global.common.exception.code.ErrorCode;

public class NetworkingNotFoundException extends BaseException {
    public NetworkingNotFoundException() {
        super(ErrorCode.NETWORKING_NOT_FOUND.getMessage());
    }
}
