package com.kh.cinepic.service;

import com.kh.cinepic.entity.Member;
import com.kh.cinepic.entity.Prefer;
import com.kh.cinepic.repository.MemberRepository;
import com.kh.cinepic.repository.PreferRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;


@SpringBootTest
@Transactional
@Slf4j
@RequiredArgsConstructor
@TestPropertySource(locations = "classpath:application-test.properties")
public class PreferRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PreferRepository preferRepository;

    @PersistenceContext
    EntityManager em;

    // 회원 정보 입력
    public Member createMemberInfo() {
        Member member = new Member();
        member.setEmail("test1234@gmail.com");
        member.setPassword("test1234!");
        member.setName("선영");
        member.setAlias("선영이");
        member.setPhone("010-1234-1234");
        member.setAddr("서울시 강남구 역삼동");
        member.setRegDate(LocalDateTime.now());
        return member;
    }

    // 취향 등록
    @Test
    @DisplayName("취향 정보 등록 테스트")
    public void savePreferTest() {
        Member member = createMemberInfo();

        // member 엔티티를 먼저 저장
        Member savedMemver = memberRepository.save(member);

        Prefer prefer = new Prefer();
        prefer.setMember(savedMemver); // 저장된 member 엔티티 참조 설정
        prefer.setDirectorName("피터 손");
        prefer.setActorName("손예진");
        prefer.setGender("여자");
        prefer.setGenre("로맨스");

        // 추가 된 prefer 저장하는 코드
       Prefer savedPrefer = preferRepository.save(prefer);

        em.flush();
        em.clear();
        System.out.println("saved Prefer : " + savedPrefer);
    }


    // 취향 수정
    @Test
    @DisplayName("취향 정보 수정 테스트")
    public void revisePreferTest() {
        Member member = createMemberInfo();

        // member 엔티티를 먼저 저장
        Member savedMemver = memberRepository.save(member);

        Prefer prefer = new Prefer();
        prefer.setMember(savedMemver);
        prefer.setDirectorName("피터 손");
        prefer.setActorName("손예진");
        prefer.setGender("여자");
        prefer.setGenre("로맨스");

        Prefer savedPrefer = preferRepository.save(prefer);

        // 수정할 내용
        savedPrefer.setDirectorName("이지은");
        savedPrefer.setActorName("현빈");
        savedPrefer.setGenre("코미디");

        // 수정된 내용 업데이트
        Prefer revisePrefer = preferRepository.save(savedPrefer);

        em.flush();
        em.clear();
        System.out.println("revise Prefer : " + revisePrefer);
    }
}
