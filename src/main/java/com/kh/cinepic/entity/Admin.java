package com.kh.cinepic.entity;

import com.kh.cinepic.constant.Authority;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="admin")
@Getter @Setter @ToString
@NoArgsConstructor
public class Admin {
    @Id
    @Column(name = "admin_id")
    private String adminId;

    @Column(name = "admin_password")
    private String adminPassword;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Builder
    Admin(String adminId, String adminPassword, Authority authority) {
        this.adminId = adminId;
        this.adminPassword = adminPassword;
        this.authority = authority;
    }
}
