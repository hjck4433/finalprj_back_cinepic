package com.kh.cinepic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPreferDto {
    private String preferActors;
    private String preferDirectors;
    private String preferGenres;
    private String movieId;
}
