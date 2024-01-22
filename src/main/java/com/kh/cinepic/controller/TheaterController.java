package com.kh.cinepic.controller;

import com.kh.cinepic.dto.MemberResDto;
import com.kh.cinepic.dto.TheaterDto;
import com.kh.cinepic.security.SecurityUtil;
import com.kh.cinepic.service.TheaterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

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

    // 영화관 내용 상세 조회
    @GetMapping("/theaterListById/{theaterId}")
    public ResponseEntity<TheaterDto> getTheaterListById(@PathVariable Long theaterId) {
        log.info("theaterId로 영화관 내용 상세 조회 : " + theaterId);
        return ResponseEntity.ok(theaterService.getTheaterListById(theaterId));
    }

}
