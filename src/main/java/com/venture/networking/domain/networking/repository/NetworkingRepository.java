package com.venture.networking.domain.networking.repository;

import com.venture.networking.domain.networking.entity.Networking;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NetworkingRepository extends JpaRepository<Networking, Long> {

    Optional<Networking> findByInviteCode(String inviteCode);
}
