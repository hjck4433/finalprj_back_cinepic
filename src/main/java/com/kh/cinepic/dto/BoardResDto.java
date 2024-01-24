package com.kh.cinepic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardResDto {
    private Long id;
    private String memberAlias;
    private String memberImage;
    private String categoryName;
    private String gatherType;
    private String title;
    private String image;
    private String boardContent;
    private int count;
    private LocalDateTime regDate;
}