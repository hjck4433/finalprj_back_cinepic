package com.kh.cinepic.entity;

import com.kh.cinepic.constant.Authority;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="member")
@Getter @Setter @ToString
@NoArgsConstructor
public class Member {
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    private String password;
    @Column(name="name", nullable = false)
    private String name;
    @Column(name="alias", nullable = false, unique = true)
    private String alias;
    @Column(name="phone", nullable = false, unique = true)
    private String phone;
    @Column(name="addr", nullable = false)
    private String addr;
    private String image;

    @Column(name="is_membership", columnDefinition = "TINYINT(1)")
    private boolean isMembership;
    @Column(name="is_withdraw", columnDefinition = "TINYINT(1)")
    private boolean isWithdraw;

    @Column(name="is_kakao", columnDefinition = "TINYINT(1)")
    private boolean isKakao;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    private LocalDateTime regDate;

    @PrePersist
    public void prePersist() {regDate = LocalDateTime.now();}

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Board> boards = new ArrayList<>();
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bookmark> bookmarks = new ArrayList<>();
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardComment> boardComments = new ArrayList<>();
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovieComment> movieComments = new ArrayList<>();
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MoviePost> moviePosts = new ArrayList<>();

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private RefreshToken refreshToken;
    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private Prefer prefer;



    @Builder
    public Member(String email, String password, String name, String alias, String phone, String addr, String image, boolean isMembership, boolean isWithdraw,boolean isKakao ,Authority authority) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.alias = alias;
        this.phone = phone;
        this.addr = addr;
        this.image = image;
        this.regDate = LocalDateTime.now();
        this.isMembership = isMembership;
        this.isWithdraw = isWithdraw;
        this.isKakao = isKakao;
        this.authority = authority;
    }
}
