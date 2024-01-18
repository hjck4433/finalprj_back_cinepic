package com.kh.cinepic.service;

import com.kh.cinepic.repository.MoviePostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MoviePostService {
    private final MoviePostRepository moviePostRepository;


    // movie관련 entity → DTO로 변환
}
