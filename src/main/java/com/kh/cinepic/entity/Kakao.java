package com.kh.cinepic.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="kakao")
@Getter @Setter @ToString
@NoArgsConstructor
public class Kakao {
    @Id
    @Column(name ="id", nullable = false, unique = true)
    private Long id;
    private String email;
    private String profile;

    @Builder
    public Kakao(Long id, String email, String profile) {
        this.id = id;
        this.email = email;
        this.profile = profile;
    }
}
