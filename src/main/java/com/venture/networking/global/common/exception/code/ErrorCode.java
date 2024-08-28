package com.venture.networking.global.common.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // Auth
    TOKEN_EXPIRED("토큰이 만료되었습니다"),
    REFRESH_TOKEN_NOT_VALIDATED("리프레시 토큰이 유효하지 않습니다"),
    LOGIN_REQUIRED("로그인 재시도가 필요합니다"),

    // Member
    MEMBER_NOT_FOUND("멤버를 찾을 수 없습니다"),
    HUMAN_MEMBER_CANNOT_BE_ACCESSED("휴먼 계정의 아이디의 정보는 조회할 수 없습니다"),

    // Profile
    PROFILE_NOT_FOUND("프로필을 찾을 수 없습니다"),
    PROFILE_NOT_SELECTED("프로필을 선택해주세요"),

    // Networking
    NETWORKING_NOT_FOUND("네트워킹을 찾을 수 없습니다"),
    NETWORKING_INVITE_CODE_NOT_FOUND("네트워킹 초대코드를 찾을 수 없습니다"),
    NETWORKING_PROFILE_FIELD_NOT_FOUND("네트워킹 프로필 필드를 찾을 수 없습니다"),

    // S3
    S3_UPLOAD_FAIL("S3 업로드에 실패하였습니다"),
    S3_DELETE_FAIL("S3 삭제에 실패하였습니다"),
    S3_GENERATE_MODEL_URL_FAIL("S3 모델 URL 생성에 실패하였습니다"),
    ;

    private final String message;

    public String getMessage(Object... args) {
        return String.format(message, args);
    }
}
