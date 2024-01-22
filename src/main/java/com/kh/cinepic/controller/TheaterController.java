package com.kh.cinepic.controller;

import com.kh.cinepic.dto.TheaterDto;
import com.kh.cinepic.service.TheaterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/theater")
@RequiredArgsConstructor
public class TheaterController {
    private final TheaterService theaterService;

    // 지도 "주소" 키워드 검색
    @GetMapping("/searchTheaterAddr")
    public ResponseEntity<List<TheaterDto>> getSearchTheaterAddr(@RequestParam String keyword) {
        log.info("theaterAddrKeyword : ", keyword);
        List<TheaterDto> list = theaterService.getSearchTheaterAddr(keyword);
        return ResponseEntity.ok(list);
    }



}
