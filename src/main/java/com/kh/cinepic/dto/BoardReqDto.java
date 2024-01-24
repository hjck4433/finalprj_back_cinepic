package com.kh.cinepic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardReqDto {
    private Long id;
    private String categoryName;
    private String gatherType;
    private String title;
    private String image;
    private String boardContent;
}
