package com.venture.networking.global.common.exception;

import com.venture.networking.global.common.exception.code.ErrorCode;

public class InviteCodeNotFoundException extends BaseException {
    public InviteCodeNotFoundException() {
        super(ErrorCode.NETWORKING_INVITE_CODE_NOT_FOUND.getMessage());
    }
}
