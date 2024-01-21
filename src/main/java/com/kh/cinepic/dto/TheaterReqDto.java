package com.kh.cinepic.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kh.cinepic.entity.Theater;
import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TheaterReqDto {
    private Long theaterId;
    private String province;
    private String city;
    private String theaterName;
    private int screens;
    private int seats;
    private int screenFilm;
    private int screen2D;
    private int screen3D;
    private int screen4D;
    private int screenImax;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Boolean isSpecialScreen;
    private String theaterAddr;
    private String theaterPhone;
    private String theaterUrl;
    private double latitude;
    private double longitude;
    public Theater toEntity(){
        return Theater.builder()
                .theaterId(theaterId)
                .province(province)
                .city(city)
                .theaterName(theaterName)
                .screens(screens)
                .seats(seats)
                .screenFilm(screenFilm)
                .screen2D(screen2D)
                .screen3D(screen3D)
                .screen4D(screen4D)
                .screenImax(screenImax)
                .isSpecialScreen(isSpecialScreen)
                .theaterAddr(theaterAddr)
                .theaterPhone(theaterPhone)
                .theaterUrl(theaterUrl)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }

}
