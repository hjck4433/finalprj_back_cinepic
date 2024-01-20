package com.kh.cinepic.service;

import com.kh.cinepic.dto.AdminMemberDto;
import com.kh.cinepic.dto.MemberReqDto;
import com.kh.cinepic.dto.MemberResDto;
import com.kh.cinepic.entity.Kakao;
import com.kh.cinepic.entity.Member;
import com.kh.cinepic.entity.RefreshToken;
import com.kh.cinepic.repository.KakaoRepository;
import com.kh.cinepic.repository.MemberRepository;
import com.kh.cinepic.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final KakaoRepository kakaoRepository;
    private final RefreshTokenRepository refreshTokenRepository;


    // 회원 상세 조회
    public MemberResDto getMemberDetail(Long id){
        Member member = memberRepository.findById(id).orElseThrow(()-> new RuntimeException("해당 회원이 존재하지 않습니다."));
        return convertEntityToDto(member);
    }

    // 비밀번호 일치 체크
    public boolean isPassword(String password, Long id) {
        log.info("password : {}", password);
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 회원이 존재하지 않습니다."));
        boolean isPw = passwordEncoder.matches(password, member.getPassword());
        log.info("isPw : {}", isPw);
        return isPw;
    }

    // 회원 정보 수정
    public boolean modifyMember(MemberReqDto memberReqDto) {
        log.info("password : {}", memberReqDto.getPassword());
        try {
            Member member = memberRepository.findByEmail(memberReqDto.getEmail())
                    .orElseThrow(() -> new RuntimeException("해당 회원이 존재하지 않습니다."));
            // 카카오 회원인 경우 비밀번호 수정 X
            log.info("카카오 회원? : {}", member.isKakao());
            if(!member.isKakao() && !memberReqDto.getPassword().isEmpty()){
                member.setPassword(passwordEncoder.encode(memberReqDto.getPassword()));
            }
            member.setAlias(memberReqDto.getAlias());
            member.setPhone(memberReqDto.getPhone());
            member.setAddr(memberReqDto.getAddr());
            member.setImage(memberReqDto.getImage());
            memberRepository.save(member);
            return true;
        }catch(Exception e) {
            log.error("회원 정보 수정 중 오류 발생");
            return false;
        }
    }

    // 회원 탈퇴
    public boolean withdrawMember(Long id){
        try {
            Member member = memberRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("해당 회원이 존재하지 않습니다."));
            member.setWithdraw(true);

            if(kakaoRepository.existsByEmail(member.getEmail())){
                Kakao kakao = kakaoRepository.findByEmail(member.getEmail())
                        .orElseThrow(() -> new RuntimeException("카카오 회원 정보가 없습니다."));
                kakaoRepository.delete(kakao);
            }

            member.setEmail(member.getEmail().concat("-"));
            member.setPhone("");
            member.setAddr("");
            member.setName("");
            memberRepository.save(member);

            RefreshToken refreshToken = refreshTokenRepository.findByMember(member)
                    .orElseThrow(() -> new RuntimeException("Token정보가 업습니다."));
            refreshTokenRepository.delete(refreshToken);

            return true;
        }catch (Exception e){
            log.error("회원 탈퇴 처리 중 오류 발생");
            return false;

        }
    }

    // Admin - 회원 전체 조회
    public List<AdminMemberDto> getAdminMemList() {
        List<Member> members = memberRepository.findAll();
        List<AdminMemberDto> adminMemberDtos = new ArrayList<>();
        for(Member member : members) {
            adminMemberDtos.add(convertMemEntityToDto(member));
        }
        return adminMemberDtos;
    }

    // Admin - 페이지 수 가져오기
    public List<AdminMemberDto> getAdminMemList(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        List<Member> members = memberRepository.findAll(pageable).getContent();
        List<AdminMemberDto> adminMemberDtos = new ArrayList<>();
        for(Member member : members) {
            adminMemberDtos.add(convertMemEntityToDto(member));
        }
        return adminMemberDtos;
    }

    // admin - 로그인 타입별

    // Admin - 회원정보 삭제
    public boolean deleteMember(Long id) {
        try {
            Member member = memberRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("해당 회원이 존재하지 않습니다."));
            log.info("회원 정보가 삭제되었습니다. 회원 ID: {}", id);

            if(kakaoRepository.existsByEmail(member.getEmail())){
                Kakao kakao = kakaoRepository.findByEmail(member.getEmail())
                        .orElseThrow(() -> new RuntimeException("카카오 회원 정보가 없습니다."));
                kakaoRepository.delete(kakao);
            }
            memberRepository.delete(member);
            return true;
        } catch (Exception e) {
            log.error("회원 정보 삭제 중 오류 발생", e);
            return false;
        }
    }

    // 월별 가입자 수
    public List<Map<String, Object>> getMonthlySignupCount() {
        try{
            List<Map<String, Object>> result = new ArrayList<>();
            List<Map<String, Object>> rawResult = memberRepository.getMonthlySignupCount();

            //rawResult 가공
            for (Map<String, Object> entry : rawResult){
                Long month = ((Number) entry.get("month")).longValue(); // Convert to Long
                Object countObject = entry.get("count");

                // Check the type of count and perform the conversion
                Integer count;
                if (countObject instanceof Number) {
                    count = ((Number) countObject).intValue();
                } else {
                    throw new IllegalStateException("Unexpected count type: " + countObject.getClass());
                }

                Map<String, Object> monthData = new HashMap<>();
                monthData.put("month",month+"월");
                monthData.put("monthlyUser", count);

                result.add(monthData);
            }
            log.info("monthly : {}", result );
            return result;
        }catch (Exception e) {
            log.error("월별 가입자 수 조회 중 오류 발생", e);
            throw new RuntimeException("월별 가입자 수 조회 중 오류 발생2", e);
        }

    }

    // 회원 엔티티를 회원 DTO로 변환
    private MemberResDto convertEntityToDto(Member member){
        MemberResDto memberResDto = new MemberResDto();
        memberResDto.setEmail(member.getEmail());
        memberResDto.setName(member.getName());
        memberResDto.setAlias(member.getAlias());
        memberResDto.setPhone(member.getPhone());
        memberResDto.setAddr(member.getAddr());
        memberResDto.setImage(member.getImage());
        memberResDto.setIsMembership(member.isMembership());
        memberResDto.setIsKakao(member.isKakao());
        return memberResDto;
    }
    private AdminMemberDto convertMemEntityToDto(Member member){
        AdminMemberDto adminMemberDto = new AdminMemberDto();
        adminMemberDto.setId(member.getId());
        adminMemberDto.setImage(member.getImage());
        adminMemberDto.setAlias(member.getAlias());
        adminMemberDto.setName(member.getName());
        adminMemberDto.setEmail(member.getEmail());
        adminMemberDto.setPhone(member.getPhone());
        adminMemberDto.setIsKakao(member.isKakao());
        adminMemberDto.setIsMembership(member.isMembership());
        adminMemberDto.setRegDate(member.getRegDate());
        adminMemberDto.setIsWithdraw(member.isWithdraw());
        adminMemberDto.setAddr(member.getAddr());

        return adminMemberDto;
    }
}
