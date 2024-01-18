package com.kh.cinepic.dto;

import com.kh.cinepic.constant.Authority;
import com.kh.cinepic.entity.Admin;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminReqDto {
    private String adminId;
    private String adminPassword;

    public Admin toEntity(PasswordEncoder passwordEncoder) {
        return Admin.builder()
                .adminId(adminId)
                .adminPassword(passwordEncoder.encode(adminPassword))
                .authority(Authority.ROLE_ADMIN)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(adminId, adminPassword);
    }
}
