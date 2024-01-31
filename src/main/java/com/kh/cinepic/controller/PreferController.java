package com.kh.cinepic.controller;


import com.kh.cinepic.dto.PreferDto;
import com.kh.cinepic.security.SecurityUtil;

import com.kh.cinepic.service.PreferPythonService;
import com.kh.cinepic.service.PreferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/prefer")
@RequiredArgsConstructor
public class PreferController {
    private final PreferService preferService;
    private final PreferPythonService preferPythonService;

    // 취향 등록
    @PostMapping("/new")
    public ResponseEntity<Boolean> savePrefer(@RequestBody PreferDto preferDto) {
        Long id = SecurityUtil.getCurrentMemberId();
        return ResponseEntity.ok(preferService.savePrefer(preferDto, id));
    }

    // 회원id로 정보 가져오기
    @GetMapping("/getPreferInfo")
    public ResponseEntity<PreferDto> getPreferInfo() {
        Long id = SecurityUtil.getCurrentMemberId();
        return ResponseEntity.ok(preferService.getPreferByInfo(id));
    }

    // 취향 수정
    @PostMapping("/modify")
    public ResponseEntity<Boolean> modifyPrefer(@RequestBody PreferDto preferDto) {
        boolean isModified = preferService.modifyPrefer(preferDto);
        return ResponseEntity.ok(isModified);
    }

    // 취향정보로 추천영화 저장
    @GetMapping("/savemovie")
    public ResponseEntity<Boolean> saveRecsMovie() {
        Long id = SecurityUtil.getCurrentMemberId();
        return ResponseEntity.ok(preferPythonService.savePreferMovie(id));
    }

    // 취향정보 여부
    @GetMapping("/isprefer")
    public ResponseEntity<Boolean> getIsPrefer() {
        Long id = SecurityUtil.getCurrentMemberId();
        return ResponseEntity.ok(preferService.isPrefer(id));
    }

}
