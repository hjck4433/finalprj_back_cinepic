package com.kh.cinepic.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MovieCommentDto {
    private Long commentId;
    private String image;
    private String alias;
    private String ratingField;
    private Integer ratingNum;
    private String ratingText;
}
