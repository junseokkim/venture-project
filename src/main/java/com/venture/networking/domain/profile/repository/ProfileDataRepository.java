package com.venture.networking.domain.profile.repository;

import com.venture.networking.domain.profile.entity.Profile;
import com.venture.networking.domain.profile.entity.ProfileData;
import io.lettuce.core.dynamic.annotation.Param;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProfileDataRepository extends JpaRepository<ProfileData, Long>{

    @Query("SELECT pd FROM ProfileData pd WHERE pd.profile = :profile ORDER BY pd.profileField.number ASC")
    List<ProfileData> findAllByProfileOrderByProfileFieldNumberAsc(@Param("profile") Profile profile);
}
