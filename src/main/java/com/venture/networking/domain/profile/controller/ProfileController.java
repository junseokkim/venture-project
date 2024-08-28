package com.venture.networking.domain.profile.controller;

import com.venture.networking.config.security.auth.PrincipalDetails;
import com.venture.networking.domain.profile.dto.response.ProfileDetailResponse;
import com.venture.networking.domain.profile.dto.response.ProfileListResponse;
import com.venture.networking.domain.profile.service.ProfileService;
import com.venture.networking.global.common.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "프로필 API", description = "프로필 관련 API")
@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @Operation(summary = "네트워킹 내 추천 프로필 조회 API", description = "네트워킹 내 상위 추천 프로필 10개를 조회하는 API입니다.")
    @GetMapping("/recommend")
    public BaseResponse<ProfileListResponse> getRecommendProfiles(
        @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        return BaseResponse.ok(profileService.getRecommendProfiles(principalDetails.getProfileId()));
    }

    @Operation(summary = "네트워킹 내 전체 프로필 조회 API", description = "네트워킹 내 전체 프로필을 조회하는 API입니다.")
    @GetMapping
    public BaseResponse<ProfileListResponse> getProfiles(
        @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        return BaseResponse.ok(profileService.getProfiles(principalDetails.getProfileId()));
    }

    @Operation(summary = "프로필 상세 조회 API", description = "특정 프로필의 상세 정보를 조회하는 API입니다.")
    @GetMapping("/{profileId}")
    public BaseResponse<ProfileDetailResponse> getProfileDetail(
        @PathVariable Long profileId
    ) {
        return BaseResponse.ok(profileService.getProfileDetail(profileId));
    }
}
