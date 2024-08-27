package com.venture.networking.global.common.entity;

public interface EntityLoader<T, ID> {
    T loadEntity(ID id);
}
