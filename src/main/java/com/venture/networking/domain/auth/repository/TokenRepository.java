package com.venture.networking.domain.auth.repository;

import com.venture.networking.domain.auth.entity.Token;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<Token, String> {
}