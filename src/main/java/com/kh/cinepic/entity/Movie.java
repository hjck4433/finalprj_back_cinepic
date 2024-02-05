package com.kh.cinepic.entity;


import com.kh.cinepic.common.MovieInfo;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "movie")
@Getter @Setter
@ToString
@NoArgsConstructor
public class Movie implements MovieInfo {
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

    @Column(name = "movie_actors", columnDefinition = "TEXT") // 출연 배우
    private String movieActors;

    @Column(name = "movie_plot", columnDefinition = "TEXT") // 주요 정보
    private String moviePlot;

    @Column(name = "movie_stills", columnDefinition = "TEXT") // 스틸컷
    private String movieStills;

    @Builder
    public Movie(Long movieId, String movieTitle, String moviePoster, String movieTitleEng, String movieRelease, String movieGenre, String movieNation, String movieGrade, String movieRuntime, String movieScore, String movieDirector, String movieActors, String moviePlot, String movieStills) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.moviePoster = moviePoster;
        this.movieTitleEng = movieTitleEng;
        this.movieRelease = movieRelease;
        this.movieGenre = movieGenre;
        this.movieNation = movieNation;
        this.movieGrade = movieGrade;
        this.movieRuntime = movieRuntime;
        this.movieScore = movieScore;
        this.movieDirector = movieDirector;
        this.movieActors = movieActors;
        this.moviePlot = moviePlot;
        this.movieStills = movieStills;
    }
}
