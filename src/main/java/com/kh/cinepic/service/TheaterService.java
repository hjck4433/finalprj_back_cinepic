package com.kh.cinepic.service;

import com.kh.cinepic.dto.TheaterDto;
import com.kh.cinepic.dto.TheaterReqDto;
import com.kh.cinepic.entity.Theater;
import com.kh.cinepic.repository.TheaterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TheaterService {
    private final TheaterRepository theaterRepository;

    @Scheduled(initialDelay = 0, fixedDelay = Long.MAX_VALUE)
    public void theaterScheduler() {
        Instant startTime = Instant.now();
        log.info("theaterScheduler start!");
        try {
            List<TheaterReqDto> response = theaterApiList();
            if(response != null) {
                saveTheater(response);
            }
        }catch (Exception e) {

        }
        Instant endTime = Instant.now();
        log.info("theater schedule end! 걸린 시간 : {}", Duration.between(startTime, endTime));
    }

    public List<TheaterReqDto> theaterApiList() {
        log.info("파이썬을 통해 영화관정보 받으러 가는 중");
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "http://localhost:5000/api/theater";
        ResponseEntity<List<TheaterReqDto>> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TheaterReqDto>>() {});
        if(responseEntity.getStatusCode().is2xxSuccessful()){
            return responseEntity.getBody();
        }else {
            log.error("Request failed with status code: {}", responseEntity.getStatusCodeValue());
            return null;
        }
    }

    public void saveTheater(List<TheaterReqDto> theaterList) {
        log.info("영화관 정보 저장 진입");
        for (TheaterReqDto theaterDto : theaterList) {
            try {
                Theater theater = theaterDto.toEntity();
                theaterRepository.save(theater);
            }catch (Exception e) {
                log.error("저장중 오류 :", e);
            }

        }
    }
    // 지도 "주소" 키워드 검색
    public List<TheaterDto> getSearchTheaterAddr(String keyword) {
        List<Theater> theaters = theaterRepository.findByTheaterAddrContaining(keyword);
        List<TheaterDto> theaterDtos= new ArrayList<>();
        for (Theater theater : theaters) {
            theaterDtos.add(convertEntityToDto(theater));
        }
        return theaterDtos;
    }

    // theaterId로 영화내용 상세 조회
    public TheaterDto getTheaterListById(Long theaterId) {
        log.info("theaterId" + theaterId);
        Theater theater = theaterRepository.findById(theaterId).orElseThrow(() -> new RuntimeException("해당 영화내용이 존재하지 않습니다."));
        return convertEntityToDto(theater);
    }

    // Entity -> Dto 전환
    private TheaterDto convertEntityToDto(Theater theater) {
        TheaterDto theaterDto = new TheaterDto();
        theaterDto.setTheaterId(theater.getTheaterId());
        theaterDto.setTheaterName(theater.getTheaterName());
        theaterDto.setScreens(theater.getScreens());
        theaterDto.setSeats(theater.getSeats());
        theaterDto.setScreenFilm(theater.getScreenFilm());
        theaterDto.setScreen2D(theater.getScreen2D());
        theaterDto.setScreen3D(theater.getScreen3D());
        theaterDto.setScreen4D(theater.getScreen4D());
        theaterDto.setScreenImax(theater.getScreenImax());
        theaterDto.setIsSpecialScreen(theater.getIsSpecialScreen());
        theaterDto.setTheaterAddr(theater.getTheaterAddr());
        theaterDto.setTheaterPhone(theater.getTheaterPhone());
        theaterDto.setTheaterUrl(theater.getTheaterUrl());
        theaterDto.setLatitude(theater.getLatitude());
        theaterDto.setLongitude(theater.getLongitude());
        return theaterDto;
    }
}
