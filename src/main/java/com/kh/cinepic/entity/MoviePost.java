package com.kh.cinepic.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "movie_post")
@Getter @Setter
public class MoviePost {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id")
    private Long id;

    // 회원
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 영화
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    // 이미지
    @Column(name = "post_img", nullable = false)
    private String postImage;

    // 제목
    @Column(name = "post_title", nullable = false)
    private String postTitle;

    // 내용
    @Column(name = "post_content", nullable = false, columnDefinition = "TEXT")
    private String postContent;

    // 등록날짜
    @Column(name = "post_reg_date")
    private LocalDateTime postRegDate;

    @PrePersist
    public void prepersist() {
        postRegDate = LocalDateTime.now();
    }
}
