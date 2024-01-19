package com.kh.cinepic.controller;

import com.kh.cinepic.service.KakaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/kakao")
@RequiredArgsConstructor
public class KakaoController {
    private final KakaoService kakaoService;

    //카카오로 회원가입 여부
    @PostMapping("/ismember")
    public ResponseEntity<Map<String,Object>> isMember(@RequestBody String kakaoToken) {
        log.info("카카오 토큰 : {}", kakaoToken);
        Map<String, Object> response = kakaoService.kakaoUserInfo(kakaoToken);
        log.info("response : {}", response);
        return ResponseEntity.ok(response);
    }
}
