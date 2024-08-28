package com.venture.networking.domain.networking.repository;

import com.venture.networking.domain.networking.entity.Networking;
import com.venture.networking.domain.networking.entity.ProfileField;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileFieldRepository extends JpaRepository<ProfileField, Long> {

    List<ProfileField> findAllByNetworkingOrderByNumberAsc(Networking networking);
}
