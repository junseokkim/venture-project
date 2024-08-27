package com.venture.networking.global.common.exception;

import com.venture.networking.global.common.exception.code.ErrorCode;

public class S3UploadFailException extends BaseException {
    public S3UploadFailException() {
        super(ErrorCode.S3_UPLOAD_FAIL.getMessage());
    }
}
