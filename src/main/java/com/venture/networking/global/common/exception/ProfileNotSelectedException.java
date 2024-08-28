package com.venture.networking.global.common.exception;

import com.venture.networking.global.common.exception.code.ErrorCode;

public class ProfileNotSelectedException extends BaseException {
    public ProfileNotSelectedException() {
        super(ErrorCode.PROFILE_NOT_SELECTED.getMessage());
    }
}
