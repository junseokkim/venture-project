package com.venture.networking.domain.profile.entity;

import com.venture.networking.domain.member.entity.Member;
import com.venture.networking.domain.networking.entity.Networking;
import com.venture.networking.global.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String representativeImg;

    @Column(nullable = false)
    private boolean isOrganizer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Networking networking;

    @OneToMany(mappedBy = "profile")
    private List<ProfileTag> profileTags;

    public void updateProfileTags(List<ProfileTag> profileTags) {
        this.profileTags = profileTags;
    }
}
