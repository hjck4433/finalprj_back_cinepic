package com.kh.cinepic.dto;

import com.kh.cinepic.entity.Movie;
import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieDto {
    private String movieId;
    private String movieTitle;
    private String moviePoster;
    private String movieTitleEng;
    private String movieRelease;
    private String movieGenre;
    private String movieNation;
    private String movieGrade;
    private String movieRuntime;
    private String movieScore;
    private String movieDirector;
    private String movieActors;
    private String moviePlot;
    private String movieStills;

    public Movie toEntity(){
        return Movie.builder()
                .movieId(movieId)
                .movieTitle(movieTitle)
                .moviePoster(moviePoster)
                .movieTitleEng(movieTitleEng)
                .movieRelease(movieRelease)
                .movieGenre(movieGenre)
                .movieNation(movieNation)
                .movieGrade(movieGrade)
                .movieRuntime(movieRuntime)
                .movieScore(movieScore)
                .movieDirector(movieDirector)
                .movieActors(movieActors)
                .moviePlot(moviePlot)
                .movieStills(movieStills)
                .build();
    }

}
