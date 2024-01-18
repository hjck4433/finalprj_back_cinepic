package com.kh.cinepic.dto;

import com.kh.cinepic.constant.Authority;
import com.kh.cinepic.entity.Member;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberReqDto {
    private String email;
    private String password;
    private String name;
    private String alias;
    private String phone;
    private String addr;
    private String image;
    private Boolean isKakao;
    //MemberReqDto -> Member
    public Member toEntity(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .name(name)
                .alias(alias)
                .phone(phone)
                .addr(addr)
                .image(image)
                .isMembership(false)
                .isWithdraw(false)
                .authority(Authority.ROLE_USER)
                .isKakao(isKakao)
                .build();
    }
    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}
