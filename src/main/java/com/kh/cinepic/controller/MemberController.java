package com.kh.cinepic.controller;


import com.kh.cinepic.dto.AdminMemberDto;
import com.kh.cinepic.dto.MemberReqDto;
import com.kh.cinepic.dto.MemberResDto;
import com.kh.cinepic.dto.MovieDto;
import com.kh.cinepic.security.SecurityUtil;
import com.kh.cinepic.service.MemberService;
import com.kh.cinepic.service.PreferPythonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
    private final PreferPythonService preferPythonService;

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


    // 멤버십 여부 업데이트
    @PostMapping("/membership")
    public ResponseEntity<Boolean> updateMembership(@RequestBody Map<String, Boolean> data){
    Long id = SecurityUtil.getCurrentMemberId();
    log.info("id : {}", id);
    return ResponseEntity.ok(memberService.membershipSave(id));
    }
    // 멤버십 여부 가져오기
    @GetMapping("/ismembership")
    public ResponseEntity<Boolean> getIsMembership(){
        Long id = SecurityUtil.getCurrentMemberId();
        log.info("id : {}", id);
        return ResponseEntity.ok(memberService.isMembership(id));
    }


    // 회원 탈퇴
    @GetMapping("/withdraw")
    public ResponseEntity<Boolean> withdrawMember(){
        Long id = SecurityUtil.getCurrentMemberId();
        log.info("id : {}", id);
        return ResponseEntity.ok(memberService.withdrawMember(id));
    }

    // 회원 맞춤 영화 추천
    @GetMapping("/recs")
    public ResponseEntity<List<Map<String, MovieDto>>> getRecsMovies(){
        Long id = SecurityUtil.getCurrentMemberId();
        log.info("회원 맞춤 영화 추천 진입 id : {}", id);
        String genre = "";
        return ResponseEntity.ok(preferPythonService.getMovieList(id, genre));
    }


    // <Admin 영역>
    // admin - 총 페이지 수 가져오기
    @GetMapping("/admin/list/count")
    public ResponseEntity<Integer> adminMemberCount(@RequestParam (defaultValue = "10") int page,
                                                    @RequestParam(defaultValue = "0") int size){
        PageRequest pageRequest = PageRequest.of(page,size);
        int pageCnt = memberService.getAdminMemberPage(pageRequest);
        return ResponseEntity.ok(pageCnt);
    }

    // admin - 회원조회 - 페이지네이션
    @GetMapping("/admin/list/page")
    public ResponseEntity<List<AdminMemberDto>> adminMemberList(@RequestParam (defaultValue = "0") int page,
                                                                @RequestParam (defaultValue = "10") int size) {
        List<AdminMemberDto> list = memberService.getAdminMemList(page, size);
        return ResponseEntity.ok(list);
    }
    // admin - 회원정보 삭제
    @DeleteMapping("/admin/delete/{id}")
    ResponseEntity<Boolean> deleteMember(@PathVariable Long id){
        log.info("회원정보 삭제 {}", id);
        return ResponseEntity.ok(memberService.deleteMember(id));
    }
    // admin - 월별 가입자
    @GetMapping("/admin/monthly")
    public ResponseEntity<List<Map <String, Object>>> monthlyUserList(){
        log.info("montly 진입");
        List<Map <String, Object>> list = memberService.getMonthlySignupCount();
        return ResponseEntity.ok(list);
    }
    // admin - 회원가입 타입별 회원 수
    @GetMapping("/admin/counts")
    public ResponseEntity<List<Map<String, Object>>> getMemberTypeCounts(){
        List<Map<String, Object>> memberTypeCounts = memberService.getMemberTypeCount();
        return ResponseEntity.ok(memberTypeCounts);
    }


}
