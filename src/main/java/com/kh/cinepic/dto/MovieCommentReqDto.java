package com.kh.cinepic.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class MovieCommentReqDto {
    private Long movieId;
    private String ratingField;
    private Integer ratingNum;
    private String ratingText;
}
