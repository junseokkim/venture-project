package com.venture.networking.global.common.exception;


import com.venture.networking.global.common.exception.code.ErrorCode;

public class S3UrlGenerationFailException extends BaseException {
    public S3UrlGenerationFailException() {
        super(ErrorCode.S3_GENERATE_MODEL_URL_FAIL.getMessage());
    }
}
