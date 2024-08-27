package com.venture.networking.domain.message.entity;

import com.venture.networking.domain.profile.entity.Profile;
import com.venture.networking.global.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean isFixed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Profile sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Profile receiver;
}