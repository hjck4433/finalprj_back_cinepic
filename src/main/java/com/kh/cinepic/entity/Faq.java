package com.kh.cinepic.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


@Entity
@Table(name= "faq")
@Getter@Setter@ToString
@NoArgsConstructor

public class Faq {
    @Id
    @Column(name= "faq_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long faqId;
    @Column(name ="faq_question")
    private String faqQuestion;
    @Column(name = "faq_answer", columnDefinition = "TEXT")
    private String faqAnswer;


}
