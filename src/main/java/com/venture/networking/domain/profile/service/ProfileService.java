package com.venture.networking.domain.profile.service;

import com.venture.networking.domain.member.entity.Member;
import com.venture.networking.domain.networking.entity.Networking;
import com.venture.networking.domain.profile.dto.response.ProfileDataResponse;
import com.venture.networking.domain.profile.dto.response.ProfileDetailResponse;
import com.venture.networking.domain.profile.dto.response.ProfileListResponse;
import com.venture.networking.domain.profile.dto.response.ProfileSummaryResponse;
import com.venture.networking.domain.profile.entity.Profile;
import com.venture.networking.domain.profile.entity.ProfileData;
import com.venture.networking.domain.profile.entity.ProfileTag;
import com.venture.networking.domain.profile.repository.ProfileDataRepository;
import com.venture.networking.domain.profile.repository.ProfileRepository;
import com.venture.networking.domain.profile.repository.ProfileTagRepository;
import com.venture.networking.global.common.exception.ProfileNotFoundException;
import com.venture.networking.global.common.exception.ProfileNotSelectedException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileDataRepository profileDataRepository;
    private final ProfileTagRepository profileTagRepository;


    // 특정 유저의 전체 프로필 조회
    public List<Profile> getProfilesByMember(Member member) {
        return profileRepository.findAllByMember(member);
    }

    // 특정 네트워킹의 전체 인원수 조회
    public long getParticipantCountByNetworking(Networking networking) {
        return profileRepository.countAllByNetworking(networking);
    }

    @Transactional
    public Profile saveProfile(Profile profile) {
        return profileRepository.save(profile);
    }

    @Transactional
    public void saveProfileData(ProfileData profileData) {
        profileDataRepository.save(profileData);
    }

    @Transactional
    public List<ProfileTag> saveProfileTags(List<ProfileTag> profileTags) {
        return profileTagRepository.saveAll(profileTags);
    }

    public ProfileListResponse getRecommendProfiles(Long profileId) {
        if (profileId == null) {
            throw new ProfileNotSelectedException();
        }
        Profile profile = profileRepository.findById(profileId)
            .orElseThrow(ProfileNotFoundException::new);

        // TODO: GPT-3를 이용한 추천 로직 구현
        List<Profile> profiles = profileRepository.findRandomByNetworking(profile.getNetworking(), profile.getId());

        return new ProfileListResponse(
            profiles.stream()
                .map(ProfileSummaryResponse::from)
                .toList());
    }

    public ProfileListResponse getProfiles(Long profileId) {
        if (profileId == null) {
            throw new ProfileNotSelectedException();
        }
        Profile profile = profileRepository.findById(profileId)
            .orElseThrow(ProfileNotFoundException::new);

        List<Profile> profiles = profileRepository.findAllByNetworkingAndIdNot(profile.getNetworking(), profileId);

        return new ProfileListResponse(
            profiles.stream()
                .map(ProfileSummaryResponse::from)
                .toList());
    }

    public ProfileDetailResponse getProfileDetail(Long profileId) {
        Profile profile = profileRepository.findById(profileId)
            .orElseThrow(ProfileNotFoundException::new);

        List<ProfileData> profileFields = profileDataRepository.findAllByProfileOrderByProfileFieldNumberAsc(profile);

        return ProfileDetailResponse.from(
            profile,
            profileFields.stream()
                .map(ProfileDataResponse::from)
                .toList());
    }
}
