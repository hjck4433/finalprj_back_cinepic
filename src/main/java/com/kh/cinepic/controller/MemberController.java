package com.kh.cinepic.controller;


import com.kh.cinepic.dto.AdminMemberDto;
import com.kh.cinepic.dto.MemberReqDto;
import com.kh.cinepic.dto.MemberResDto;
import com.kh.cinepic.security.SecurityUtil;
import com.kh.cinepic.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    // 회원 상세 조회
    @GetMapping("/detail")
    public ResponseEntity<MemberResDto> memberDetail() {
        Long id = SecurityUtil.getCurrentMemberId();
        log.info("id : {} ", id);
        MemberResDto memberResDto = memberService.getMemberDetail(id);
        return ResponseEntity.ok(memberResDto);
    }

    // 회원 정보 수정
    @PostMapping("/update")
    public ResponseEntity<Boolean> updateMember(@RequestBody MemberReqDto memberReqDto){
        log.info("MemberReqDto : {}", memberReqDto);
        return ResponseEntity.ok(memberService.modifyMember(memberReqDto));
    }

    // 비밀 번호 체크
    @PostMapping("/ispassword")
    public ResponseEntity<Boolean> checkPw(@RequestBody Map<String, String> data){
        Long id = SecurityUtil.getCurrentMemberId();
        String password =data.get("password");
        log.info("password : {}", password);
        return ResponseEntity.ok(memberService.isPassword(password, id));
    }

    // 회원 탈퇴
    @GetMapping("/withdraw")
    public ResponseEntity<Boolean> withdrawMember(){
        Long id = SecurityUtil.getCurrentMemberId();
        log.info("id : {}", id);
        return ResponseEntity.ok(memberService.withdrawMember(id));
    }

}
