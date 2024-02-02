package com.kh.cinepic.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="theater")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Theater {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "theater_id")
    private Long theaterId;

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

    @Column(name ="theater_url")
    private String theaterUrl;

    @Column(name ="latitude", nullable = false)
    private double latitude;

    @Column(name ="longitude", nullable = false)
    private double longitude;

    @Builder
    public Theater(Long theaterId, String province, String city, String theaterName, int screens, int seats, int screenFilm, int screen2D, int screen3D, int screen4D, int screenImax, Boolean isSpecialScreen, String theaterAddr, String theaterPhone, String theaterUrl, double latitude, double longitude) {
        this.theaterId = theaterId;
        this.province = province;
        this.city = city;
        this.theaterName = theaterName;
        this.screens = screens;
        this.seats = seats;
        this.screenFilm = screenFilm;
        this.screen2D = screen2D;
        this.screen3D = screen3D;
        this.screen4D = screen4D;
        this.screenImax = screenImax;
        this.isSpecialScreen = isSpecialScreen;
        this.theaterAddr = theaterAddr;
        this.theaterPhone = theaterPhone;
        this.theaterUrl = theaterUrl;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
