package com.venture.networking.domain.networking.controller;

import com.venture.networking.config.security.auth.PrincipalDetails;
import com.venture.networking.domain.auth.dto.response.AuthTokenIssueResponse;
import com.venture.networking.domain.networking.dto.request.NetworkingCreateRequest;
import com.venture.networking.domain.networking.dto.request.NetworkingProfileCreateRequest;
import com.venture.networking.domain.networking.dto.request.NetworkingProfileExampleRequest;
import com.venture.networking.domain.networking.dto.response.NetworkingCreateResponse;
import com.venture.networking.domain.networking.dto.response.NetworkingInviteCodeResponse;
import com.venture.networking.domain.networking.dto.response.NetworkingParticipateListResponse;
import com.venture.networking.domain.networking.dto.response.NetworkingProfileExampleResponse;
import com.venture.networking.domain.networking.service.NetworkingService;
import com.venture.networking.global.common.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "네트워킹 API", description = "네트워킹 관련 API")
@RestController
@RequestMapping("/networkings")
@RequiredArgsConstructor
public class NetworkingController {

    private final NetworkingService networkingService;

    @Operation(summary = "네트워킹 생성 API", description = "새로운 네트워킹을 생성하는 API입니다.")
    @PostMapping
    public BaseResponse<NetworkingCreateResponse> createNetworking(
        @RequestBody NetworkingCreateRequest request,
        @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        return BaseResponse.ok(networkingService.createNetworking(request, principalDetails.getMember()));
    }

    @Operation(summary = "초대코드 화인 API", description = "입력한 초대코드가 유효한지 확인하는 API입니다.")
    @GetMapping
    public BaseResponse<NetworkingInviteCodeResponse> verifyInviteCode(
        @RequestParam String inviteCode) {
        return BaseResponse.ok(networkingService.verifyInviteCode(inviteCode));
    }

    @Operation(summary = "네트워킹 참여자 정보 예시 생성 API", description = "새로운 네트워킹 참여자 정보 예시를 생성하는 API입니다.")
    @PostMapping("/{networkingId}/profiles-example")
    public BaseResponse<AuthTokenIssueResponse> createProfileExample(
        @PathVariable Long networkingId,
        @RequestBody NetworkingProfileExampleRequest request,
        @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        return BaseResponse.ok(networkingService.createProfileExample(networkingId, request, principalDetails.getMember()));
    }

    @Operation(summary = "네트워킹 참여자 정보 예시 조회 API", description = "네트워킹 참여자 정보 예시를 조회하는 API입니다.")
    @GetMapping("/{networkingId}/profiles-example")
    public BaseResponse<NetworkingProfileExampleResponse> getProfileExample(
        @PathVariable Long networkingId,
        @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        return BaseResponse.ok(networkingService.getProfileExample(networkingId, principalDetails.getMember()));
    }

    @Operation(summary = "네트워킹 참여자 정보 입력 API", description = "네트워킹 참여자 정보를 입력하는 API입니다.")
    @PostMapping("/{networkingId}/profiles")
    public BaseResponse<AuthTokenIssueResponse> createNetworkingProfile(
        @PathVariable Long networkingId,
        @RequestBody NetworkingProfileCreateRequest request,
        @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        return BaseResponse.ok(networkingService.createNetworkingProfile(networkingId, request, principalDetails.getMember()));
    }

    @Operation(summary = "참여 네트워킹 목록 조회 API", description = "참여 중인 네트워킹 목록을 조회하는 API입니다.")
    @GetMapping("/participations")
    public BaseResponse<NetworkingParticipateListResponse> getParticipateList(
        @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        return BaseResponse.ok(networkingService.getParticipateList(principalDetails.getMember()));
    }
}
