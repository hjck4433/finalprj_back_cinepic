package com.kh.cinepic.service;

import com.kh.cinepic.entity.Member;
import com.kh.cinepic.repository.MemberRepository;
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
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@Transactional
@Slf4j
@RequiredArgsConstructor
@TestPropertySource(locations = "classpath:application-test.properties")
public class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;
    @PersistenceContext
    EntityManager em;

    public Member createMemberInfo() {
        Member member = new Member();
        member.setEmail("test@gmail.com");
        member.setPassword("test1234!");
        member.setName("테스트");
        member.setAlias("다람쥐");
        member.setPhone("010-1111-2222");
        member.setAddr("서울시 강남구 역삼동");
        member.setRegDate(LocalDateTime.now());
        return member;
    }


    @Test
    @DisplayName("회원 정보 저장 테스트")
    public void saveMemberTest() {
        Member member = createMemberInfo();
        Member testMember = memberRepository.save(member);
        em.flush();
        em.clear();
        System.out.println("testMember 결과 : " + testMember);
    }

    @Test
    @DisplayName("이메일 중복체크 테스트")
    public void isUniqeEmailTest() {
        Member member = createMemberInfo();
        memberRepository.save(member);
        boolean isUnique = memberRepository.existsByEmail("test0@gmail.com");

        em.flush();
        em.clear();
        System.out.println("isUnique 결과 : " + isUnique);
    }

    @Test
    @DisplayName("닉네임 중복 테스트")
    public void isUniqueAliasTest() {
        Member member = createMemberInfo();
        memberRepository.save(member);
        boolean isAliasUnique = memberRepository.existsByAlias("다람쥐");

        em.flush();
        em.clear();
        System.out.println("isAliasUnique결과 : " + isAliasUnique);
    }

    @Test
    @DisplayName("전화번호 중복 테스트")
    public void isUniquePhoneTest() {
        Member member = createMemberInfo();
        memberRepository.save(member);
        boolean isPhoneUnique = memberRepository.existsByPhone("010-1111-2222");

        em.flush();
        em.clear();
        System.out.println("isPhoneUnique 결과 : " + isPhoneUnique);
    }

    @Test
    @DisplayName("내 정보 요청 테스트")
    public void memberDetailTest() {

        Member member = createMemberInfo();
        memberRepository.save(member);
        Member detailMember = memberRepository.findById(member.getId())
                .orElseThrow(() -> new RuntimeException("없는 회원입니다."));
        em.flush();
        em.clear();
        System.out.println(" memberDetailTest결과 : " + detailMember);

    }

    @Test
    @DisplayName("이메일과 비밀번호로 회원 찾기 테스트")
    public void findMemberByEmailAndPasswordTest() {
        Member member = createMemberInfo();
        memberRepository.save(member);

        Member foundMember = memberRepository.findByEmailAndPassword("test@gmail.com", "test1234!").orElseThrow();

        em.flush();
        em.clear();

        System.out.println("찾은 회원 : " + foundMember);
    }


    @Test
    @DisplayName("멤버십 여부 테스트")
    public void isMembershipTest() {
        Member member = createMemberInfo();
        memberRepository.save(member);
        boolean isMembership = memberRepository.existsById(member.getId());

        em.flush();
        em.clear();
        System.out.println("isMembership 결과 : " + isMembership);
    }


    @Test
    @DisplayName("회원 삭제 테스트")
    public void deleteMember() {
        Member member = createMemberInfo();

        memberRepository.save(member);

        memberRepository.deleteById(member.getId());
        em.flush();
        em.clear();
        System.out.println("회원 삭제 테스트 : " + member.getId());


    }
}



