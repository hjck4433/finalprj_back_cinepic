package com.kh.cinepic.dto;
import com.kh.cinepic.entity.Member;
import lombok.*;

import java.time.LocalDateTime;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class AdminMemberDto {
    // 유저(이미지, 닉네임) 이름, 이메일, 전화번호, 카카오 연동, 멤버십여부, 등록날짜, 탈퇴 정보, 주소 필요
    private Long id;
    private String image;
    private String alias;
    private String name;
    private String email;
    private String phone;
    private Boolean isKakao;
    private Boolean isMembership;
    private LocalDateTime regDate;
    private Boolean isWithdraw;
    private String addr;


    // DTO에 담음
    public static AdminMemberDto of(Member member){
        return AdminMemberDto.builder()
                .id(member.getId())
                .image(member.getImage())
                .alias(member.getAlias())
                .name(member.getName())
                .email(member.getEmail())
                .phone(member.getPhone())
                .isKakao(member.isKakao())
                .isMembership(member.isMembership())
                .regDate(member.getRegDate())
                .isWithdraw(member.isWithdraw())
                .addr(member.getAddr())
                .build();
    }
}
