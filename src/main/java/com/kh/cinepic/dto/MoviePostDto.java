package com.kh.cinepic.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class MoviePostDto {
    private Long postId;
    private Long movieId;
    private String image;
    private String alias;
    private String postImage;
    private String postTitle;
    private String postContent;
    private LocalDateTime postRegDate;

}
