package com.kh.cinepic.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="prefer")
@Getter
@Setter
@ToString
public class Prefer {
    @Id
    @GeneratedValue
    @Column(name = "prefer_id")
    private Long id;

    @Column(name = "user_id")
    private Member member;

    @Column(name ="director_name", nullable = false)
    private String directorName;

    @Column(name ="actor_name", nullable = false)
    private String actorName;

    @Column(name ="gender", nullable = false)
    private String gender;

    @Column(name ="genre", nullable = false)
    private String genre;
}
