package com.kh.cinepic.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "board")
@Getter
@Setter

public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "board_id")
    private Long id;

    // 회원 ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 카테고리 (주제)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    // 모임 유형
    @Column(name = "gather_type")
    private String gatherType;

    // 게시글 제목
    @Column(name = "board_title", nullable = false)
    private String title;

    // 게시글 내용
    @Column(name = "board_content", nullable = false, columnDefinition = "TEXT") // TEXT로 하지 않으면 받아오는 글이 너무 길 때 오류남.
    private String boardContent;

    // 이미지
    @Column(name = "board_image", nullable = false)
    private String image;

    // 조회수
    private int count;

    // 게시글의 댓글
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardComment> comments = new ArrayList<>();

    // 등록 날짜
    @Column(name = "board_reg_date")
    private LocalDateTime regdate;

    @PrePersist
    public void prepersist() {regdate = LocalDateTime.now();}

}
