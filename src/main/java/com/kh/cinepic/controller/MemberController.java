package com.kh.cinepic.controller;


import com.kh.cinepic.dto.MemberResDto;
import com.kh.cinepic.security.SecurityUtil;
import com.kh.cinepic.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
        private final MemberService memberService;

        // 회원 상세 조회
        @GetMapping("/detail")
        public ResponseEntity<MemberResDto> memberDetail(){
            Long id = SecurityUtil.getCurrentMemberId();
            log.info("id : {} ", id);
            MemberResDto memberResDto = memberService.getMemberDetail(id);
            return ResponseEntity.ok(memberResDto);
        }
    }
}
