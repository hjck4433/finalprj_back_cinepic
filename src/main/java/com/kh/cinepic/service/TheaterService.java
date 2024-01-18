package com.kh.cinepic.service;

import com.kh.cinepic.dto.TheaterDto;
import com.kh.cinepic.entity.Theater;
import com.kh.cinepic.repository.TheaterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TheaterService {
    private final TheaterRepository theaterRepository;

//    public List<TheaterDto> getSearch(String keyword) {
//
//    }
}
