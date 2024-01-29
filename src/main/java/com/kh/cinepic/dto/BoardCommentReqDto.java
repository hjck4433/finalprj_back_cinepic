package com.kh.cinepic.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardCommentReqDto {
    private Long id;
    private Long boardId;
    private String commentText;
}
