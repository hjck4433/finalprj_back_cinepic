package com.kh.cinepic.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TheaterDto {
    private Long theaterId;
    private String theaterName;
    private int screens;
    private int seats;
    private int screenFilm;
    private int screen2D;
    private int screen3D;
    private int screen4D;
    private int screenImax;
    private Boolean isSpecialScreen;
    private String theaterAddr;
    private String theaterPhone;
    private String theaterUrl;
    private Double latitude;
    private Double longitude;

}
