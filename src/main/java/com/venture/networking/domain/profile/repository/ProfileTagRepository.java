package com.venture.networking.domain.profile.repository;

import com.venture.networking.domain.profile.entity.ProfileTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileTagRepository extends JpaRepository<ProfileTag, Long> {
}
