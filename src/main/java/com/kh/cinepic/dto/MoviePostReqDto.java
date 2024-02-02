package com.kh.cinepic.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class MoviePostReqDto {
    private Long movieId;
    private String postImage;
    private String postTitle;
    private String postContent;
}
