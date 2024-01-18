package com.kh.cinepic.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "movie")
@Getter @Setter
@NoArgsConstructor
public class Movie {
    @Id
    @Column(name = "movie_id") // 영화 ID
    private Long movieId;

    @Column(name = "movie_title") // 제목
    private String movieTitle;

    @Column(name = "movie_poster") // 영화 포스터 url
    private String moviePoster;

    @Column(name = "movie_titleEng") // 영문 제목
    private String movieTitleEng;

    @Column(name = "movie_release") // 개봉일
    private String movieRelease;

    @Column(name = "movie_genre") // 장르
    private String movieGenre;

    @Column(name = "movie_nation") // 국가
    private String movieNation;

    @Column(name = "movie_grade") // 등급
    private String movieGrade;

    @Column(name = "movie_runtime") // 상영 시간
    private String movieRuntime;

    @Column(name = "movie_score") // 평점
    private String movieScore;

    @Column(name = "movie_director") // 감독
    private String movieDirector;

    @Column(name = "movie_actors") // 출연 배우
    private String movieActors;

    @Column(name = "movie_plot", columnDefinition = "TEXT") // 주요 정보
    private String moviePlot;

    @Column(name = "movie_stills", columnDefinition = "TEXT") // 스틸컷
    private String movieStills;
}
