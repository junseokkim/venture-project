package com.venture.networking.global.common.exception;

import com.venture.networking.global.common.exception.code.ErrorCode;

public class S3DeleteFailException extends BaseException {
    public S3DeleteFailException() {
        super(ErrorCode.S3_UPLOAD_FAIL.getMessage());
    }
}
