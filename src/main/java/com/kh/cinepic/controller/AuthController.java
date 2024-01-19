package com.kh.cinepic.controller;

import com.kh.cinepic.dto.*;
import com.kh.cinepic.service.AuthService;
import com.kh.cinepic.service.MemberService;
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
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final MemberService memberService;

    //중복체크
    @PostMapping("/isunique")
    public ResponseEntity<Boolean> isUnique(@RequestBody Map<String, String> dataMap) {
        int type = Integer.parseInt(dataMap.get("type"));
        return ResponseEntity.ok(authService.checkUnique(type, dataMap.get("data")));
    }

    // Member 회원가입
    @PostMapping("/join")
    public ResponseEntity<MemberResDto> join(@RequestBody MemberReqDto memberReqDto){
        log.info("memberReqDto : {}", memberReqDto);
        return ResponseEntity.ok(authService.join(memberReqDto));
    }

    // Member 로그인
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody MemberReqDto memberReqDto){
        return ResponseEntity.ok(authService.login(memberReqDto));
    }

    // Admin 추가 (Swagger 등을 통해서 추가)
    @PostMapping("/newadmin")
    public ResponseEntity<Boolean> addAdmin(@RequestBody AdminReqDto adminReqDto){
        log.info("관리자 추가 : {}", adminReqDto);
        return ResponseEntity.ok(authService.addAdmin(adminReqDto));
    }

    // Admin 로그인
    @PostMapping("/adminlogin")
    public ResponseEntity<TokenDto> adminLogin(@RequestBody AdminReqDto adminReqDto){
        log.info("관리자 로그인 : {}", adminReqDto.getAdminId());
        return ResponseEntity.ok(authService.adminLogin(adminReqDto));
    }

    // 리프레시 토큰으로 새 엑세스 토큰 발급
    @PostMapping("/refresh")
    public ResponseEntity<AccessTokenDto> newToken(@RequestBody String refreshToken){
        log.info("refreshToken : {}", refreshToken);
        return ResponseEntity.ok(authService.refreshAccessToken(refreshToken));
    }


}
