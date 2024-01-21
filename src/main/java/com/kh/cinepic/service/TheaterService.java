package com.kh.cinepic.service;

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

//    public List<TheaterDto> getSearch(String keyword) {
//
//    }
}
