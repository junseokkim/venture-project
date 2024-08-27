package com.venture.networking.domain.profile.repository;

import com.venture.networking.domain.profile.entity.Profile;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByIdAndMemberId(Long id, Long memberId);
}
