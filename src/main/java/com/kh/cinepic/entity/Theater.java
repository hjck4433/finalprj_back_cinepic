package com.kh.cinepic.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="theater")
@Getter
@Setter
@ToString
public class Theater {
    @Id
    @Column(name = "theater_id")
    private Long id;

    @Column(name ="province", nullable = false)
    private String province;

    @Column(name ="city", nullable = false)
    private String city;

    @Column(name ="theater_name", nullable = false)
    private String theaterName;

    @Column(name ="screens")
    private int screens;

    @Column(name ="seats")
    private int seats;

    @Column(name ="screen_film")
    private int screenFilm;

    @Column(name ="screen_2d")
    private int screen2D;

    @Column(name ="screen_3d")
    private int screen3D;

    @Column(name ="screen_4d")
    private int screen4D;

    @Column(name ="screen_imax")
    private int screenImax;

    @Column(name ="is_special_screen", columnDefinition = "TINYINT(1)")
    private Boolean isSpecialScreen;

    @Column(name ="theater_addr", nullable = false)
    private String theaterAddr;

    @Column(name ="theater_phone", nullable = false)
    private String theaterPhone;

    @Column(name ="theater_url", nullable = false)
    private String theaterUrl;

    @Column(name ="latitude", nullable = false)
    private Double latitude;

    @Column(name ="longitude", nullable = false)
    private Double longitude;

}
