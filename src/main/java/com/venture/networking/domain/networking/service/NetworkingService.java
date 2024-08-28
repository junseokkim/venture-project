package com.venture.networking.domain.networking.service;

import com.venture.networking.config.security.jwt.JwtTokenProvider;
import com.venture.networking.domain.auth.dto.response.AuthTokenIssueResponse;
import com.venture.networking.domain.member.entity.Member;
import com.venture.networking.domain.networking.dto.request.NetworkingCreateRequest;
import com.venture.networking.domain.networking.dto.request.NetworkingProfileCreateRequest;
import com.venture.networking.domain.networking.dto.request.NetworkingProfileExampleRequest;
import com.venture.networking.domain.networking.dto.response.NetworkingCreateResponse;
import com.venture.networking.domain.networking.dto.response.NetworkingInviteCodeResponse;
import com.venture.networking.domain.networking.dto.response.NetworkingParticipateListResponse;
import com.venture.networking.domain.networking.dto.response.NetworkingProfileExampleResponse;
import com.venture.networking.domain.networking.dto.response.NetworkingSummaryResponse;
import com.venture.networking.domain.networking.dto.response.ProfileFieldResponse;
import com.venture.networking.domain.networking.entity.Networking;
import com.venture.networking.domain.networking.entity.ProfileField;
import com.venture.networking.domain.networking.repository.NetworkingRepository;
import com.venture.networking.domain.profile.entity.Profile;
import com.venture.networking.domain.networking.repository.ProfileFieldRepository;
import com.venture.networking.domain.profile.entity.ProfileData;
import com.venture.networking.domain.profile.entity.ProfileTag;
import com.venture.networking.domain.profile.service.ProfileService;
import com.venture.networking.global.common.exception.InviteCodeNotFoundException;
import com.venture.networking.global.common.exception.NetworkingNotFoundException;
import com.venture.networking.global.common.exception.ProfileFieldNotFoundException;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NetworkingService {

    private final NetworkingRepository networkingRepository;
    private final ProfileFieldRepository profileFieldRepository;

    private final ProfileService profileService;
    private final JwtTokenProvider jwtTokenProvider;

    // 네트워킹 생성
    @Transactional
    public NetworkingCreateResponse createNetworking(NetworkingCreateRequest request, Member member) {

        Networking networking = Networking.builder()
            .name(request.name())
            .representativeImg(request.representativeImg())
            .description(request.description())
            .relatedLink(request.relatedLink())
            .inviteCode(generateInvitationCode()) // TODO: 초대 코드 중복 체크
            .organizer(member)
            .build();

        networkingRepository.save(networking);

        return NetworkingCreateResponse.from(networking.getId());
    }

    // 초대코드 확인
    public NetworkingInviteCodeResponse verifyInviteCode(String inviteCode) {
        Networking networking = networkingRepository.findByInviteCode(inviteCode)
            .orElseThrow(InviteCodeNotFoundException::new);

        return new NetworkingInviteCodeResponse(networking.getId());
    }

    // 네트워킹 참여자 정보 예시 생성
    @Transactional
    public AuthTokenIssueResponse createProfileExample(Long networkingId, NetworkingProfileExampleRequest request, Member member) {

        Networking networking = networkingRepository.findById(networkingId)
            .orElseThrow(NetworkingNotFoundException::new);

        Profile profile = profileService.saveProfile(
            Profile.builder()
                .member(member)
                .networking(networking)
                .name(request.name())
                .representativeImg(request.representativeImg())
                .isOrganizer(true)
                .build()
        );

        List<ProfileTag> profileTags = profileService.saveProfileTags(
            request.tags().stream().map(tag ->
                ProfileTag.builder()
                    .name(tag)
                    .profile(profile)
                    .build()
            ).toList());

        profile.updateProfileTags(profileTags);

        request.profileFields().forEach(profileFieldRequest -> {
                ProfileField profileField = profileFieldRepository.save(ProfileField.builder()
                    .name(profileFieldRequest.name())
                    .number(profileFieldRequest.number())
                    .type(profileFieldRequest.type())
                    .networking(networking)
                    .build()
                );

                profileService.saveProfileData(ProfileData.builder()
                    .value(profileFieldRequest.value())
                    .profile(profile)
                    .profileField(profileField)
                    .build()
                );
        });

        return  jwtTokenProvider.issueToken(member.getEmail(), profile.getId());
    }

    // 네트워킹 참여자 정보 예시 조회
    public NetworkingProfileExampleResponse getProfileExample(Long networkingId, Member member) {
        Networking networking = networkingRepository.findById(networkingId)
            .orElseThrow(NetworkingNotFoundException::new);

        List<ProfileField> profileFields = profileFieldRepository.findAllByNetworkingOrderByNumberAsc(networking);

        return new NetworkingProfileExampleResponse(
            profileFields.stream().map(ProfileFieldResponse::from).toList()
        );
    }

    // 네트워킹 참여자 정보 입력
    @Transactional
    public AuthTokenIssueResponse createNetworkingProfile(Long networkingId, NetworkingProfileCreateRequest request, Member member) {
        Networking networking = networkingRepository.findById(networkingId)
            .orElseThrow(NetworkingNotFoundException::new);

        Profile profile = profileService.saveProfile(
            Profile.builder()
                .member(member)
                .networking(networking)
                .name(request.name())
                .representativeImg(request.representativeImg())
                .isOrganizer(false)
                .build()
        );

        request.profileFields().forEach(profileDataRequest -> {
            ProfileField profileField = profileFieldRepository.findById(profileDataRequest.profileFieldId())
                .orElseThrow(ProfileFieldNotFoundException::new);

            profileService.saveProfileData(ProfileData.builder()
                .value(profileDataRequest.value())
                .profile(profile)
                .profileField(profileField)
                .build()
            );
        });

        List<ProfileTag> profileTags = profileService.saveProfileTags(
            request.tags().stream().map(tag ->
                ProfileTag.builder()
                    .name(tag)
                    .profile(profile)
                    .build()
            ).toList());
        profile.updateProfileTags(profileTags);

        return jwtTokenProvider.issueToken(member.getEmail(), profile.getId());
    }


    // 특정 유저의 참여중인 네트워킹 목록 조회
    public NetworkingParticipateListResponse getParticipateList(Member member) {

        List<Profile> profileList = profileService.getProfilesByMember(member);
        List<NetworkingSummaryResponse> networkingList = profileList.stream().map(profile ->
            NetworkingSummaryResponse.from(
                profile.getNetworking(),
                profileService.getParticipantCountByNetworking(profile.getNetworking())
            )
        ).toList();

        return NetworkingParticipateListResponse.from(networkingList);
    }

    private String generateInvitationCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }
}
