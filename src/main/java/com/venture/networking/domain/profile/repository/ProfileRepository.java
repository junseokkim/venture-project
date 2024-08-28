package com.venture.networking.domain.profile.repository;

import com.venture.networking.domain.member.entity.Member;
import com.venture.networking.domain.networking.entity.Networking;
import com.venture.networking.domain.profile.entity.Profile;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByIdAndMember(Long id, Member member);
    List<Profile> findAllByMember(Member member);
    long countAllByNetworking(Networking networking);

    List<Profile> findAllByNetworkingAndIdNot(Networking networking, Long id);

    @Query("SELECT p FROM Profile p WHERE p.networking = :networking AND p.id != :profileId ORDER BY FUNCTION('RAND') LIMIT 10")
    List<Profile> findRandomByNetworking(Networking networking, Long profileId);
}
