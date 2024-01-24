package com.kh.cinepic.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FaqDto {
    private Long faqId;
    private String faqQuestion;
    private String faqAnswer;

}
