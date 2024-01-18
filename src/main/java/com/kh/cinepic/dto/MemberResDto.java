package com.kh.cinepic.dto;

import com.kh.cinepic.entity.Member;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResDto {
    private String email;
    private String name;
    private String alias;
    private String phone;
    private String addr;
    private String image;
    private Boolean isMembership;
    private Boolean isKakao;
    private LocalDateTime regDate;

    //Member -> MemberResDto
    public static MemberResDto of(Member member) {
        return MemberResDto.builder()
                .email(member.getEmail())
                .name(member.getName())
                .alias(member.getAlias())
                .phone(member.getPhone())
                .addr(member.getAddr())
                .image(member.getImage())
                .isMembership(member.isMembership())
                .isKakao(member.isKakao())
                .regDate(member.getRegDate())
                .build();
    }
}
