package com.kh.cinepic.service;


import com.kh.cinepic.dto.MovieDto;
import com.kh.cinepic.entity.Movie;
import com.kh.cinepic.repository.MovieRepository;
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
public class MoviePythonService {
    private final MovieRepository movieRepository;

    @Scheduled(initialDelay = 1 * 60 * 1000, fixedDelay = Long.MAX_VALUE)
    public void movieScheduler() {
        if (movieRepository.count() > 0) {
            log.info("Movie 테이블이 비어 있지 않습니다. 스케쥴 종료");
            return;
        }

        Instant startTime = Instant.now();
        log.info("movieScheduler start!");
        try {
            List<MovieDto> response = movieApiList();
            if(response != null) {
                saveMovies(response);
            }
        }catch (Exception e) {
            log.error("영화 스케쥴 작동 중 오류 : ", e);
        }
        Instant endTime = Instant.now();
        log.info("movie schedule end! 걸린 시간 : {}", Duration.between(startTime, endTime));
    }

    public List<MovieDto> movieApiList() {
        log.info("파이썬을 통해 영화정보 받으러 가는 중");
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "http://localhost:5000/api/movies";
        ResponseEntity<List<MovieDto>> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<MovieDto>>() {});
        if(responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        }else {
            log.error("Request failed with status code: {}", responseEntity.getStatusCodeValue());
            return null;
        }
    }

    public void saveMovies(List<MovieDto> movieList) {
        log.info("영화 정보 저장 진입");
        for(MovieDto movieDto : movieList) {
            try {
                Movie movie = movieDto.toEntity();
                movieRepository.save(movie);
            }catch (Exception e) {
                log.error("저장중 오류 :", e);
            }
        }
    }
}
