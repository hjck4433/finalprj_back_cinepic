package com.kh.cinepic.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class BoardCommentResDto {
    private Long commentId;
    private Long boardId;
    private String memberAlias;
    private String memberImage;
    private String commentText;
    private LocalDateTime commentRegDate;
}
