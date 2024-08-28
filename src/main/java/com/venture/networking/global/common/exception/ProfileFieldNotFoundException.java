package com.venture.networking.global.common.exception;

import com.venture.networking.global.common.exception.code.ErrorCode;

public class ProfileFieldNotFoundException extends BaseException {
    public ProfileFieldNotFoundException() {
        super(ErrorCode.NETWORKING_PROFILE_FIELD_NOT_FOUND.getMessage());
    }
}
