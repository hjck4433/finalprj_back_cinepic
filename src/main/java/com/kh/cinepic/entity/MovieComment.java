package com.kh.cinepic.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "movie_comment")
@Getter @Setter
public class MovieComment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_id")
    private Long id;

    // 회원
    @JoinColumn(name = "member_id")
    private Member member;

    // 영화
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    // 분야 평가
    @Column(name = "rating_field", nullable = false)
    private String ratingField;

    // 점수 평가
    @Column(name = "rating_num", nullable = false)
    private Integer ratingNum;

    // 텍스트 평가 - 100자 제한 (한국어 기준)
    @Column(name = "rating_text", nullable = false, length = 300)
    private String ratingText;
}
