package com.kh.cinepic.dto;

import com.kh.cinepic.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PreferDto {
    private Long id;
    private String directorName; // 감독 이름
    private String actorName; // 배우 이름
    private String gender;
    private String genre; // 장르
}
