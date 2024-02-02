package com.kh.cinepic.service;

import com.kh.cinepic.entity.Theater;
import com.kh.cinepic.repository.TheaterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
@Slf4j
@RequiredArgsConstructor
@TestPropertySource(locations = "classpath:application-test.properties")
public class TheaterRepositoryTest {
    @Autowired
    TheaterRepository theaterRepository;

    @PersistenceContext
    EntityManager em;

    // 극장 정보 입력
    public Theater createTheaterInfo() {
        Theater theater = new Theater();
        theater.setTheaterAddr("강원도 춘천시 퇴계동 1017번지");
        theater.setTheaterName("CGV 춘천");
        theater.setTheaterPhone("1544-1122");
        theater.setProvince("강원도");
        theater.setCity("춘천");
        return theater;
    }

    // 극장 검색
    @Test
    @DisplayName("극장 검색 테스트")
    public void theaterSearchTest() {
        // 테스트 데이터 설정
        Theater theater = createTheaterInfo();

        // 극장을 저장
        Theater savedTheater = theaterRepository.save(theater);
        em.flush();
        em.clear();

        // 극장을 검색하고 결과 확인
        String keyword = "강원도"; // 검색할 키워드 설정
        List<Theater> searchedTheaters = theaterRepository.findByTheaterAddrContaining(keyword);

        // 검증 로직 추가
        assertNotNull(searchedTheaters);
        assertFalse(searchedTheaters.isEmpty());

        // 검색된 극장들 중에서 주소에 검색어가 포함되어 있는지 확인
        for (Theater searchedTheater : searchedTheaters) {
            assertTrue(searchedTheater.getTheaterAddr().contains(keyword));
        }
    }

}
