package com.kh.cinepic.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="prefer_movie")
@Getter @Setter
@ToString
@NoArgsConstructor
public class PreferMovie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="prefer_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "recs1")
    private Movie recs1;

    @ManyToOne
    @JoinColumn(name = "recs2")
    private Movie recs2;

    @ManyToOne
    @JoinColumn(name = "recs3")
    private Movie recs3;

    @ManyToOne
    @JoinColumn(name = "recs4")
    private Movie recs4;

}
