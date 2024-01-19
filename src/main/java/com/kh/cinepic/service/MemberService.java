package com.kh.cinepic.service;

import com.kh.cinepic.dto.MemberReqDto;
import com.kh.cinepic.entity.Kakao;
import com.kh.cinepic.entity.Member;
import com.kh.cinepic.entity.RefreshToken;
import com.kh.cinepic.repository.KakaoRepository;
import com.kh.cinepic.repository.MemberRepository;
import com.kh.cinepic.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public MemberReqDto getMemberDetail(Long id){
        Member member = memberRepository.findById().orElseThrow(()-> new RuntimeException("해당 회원이 존재하지 않습니다."));
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


    // 회원 엔티티를 회원 DTO로 변환
    private MemberReqDto convertEntityToDto(Member member){
        MemberReqDto memberReqDto = new MemberReqDto();
        memberReqDto.setEmail(member.getEmail());
        memberReqDto.setName(member.getName());
        memberReqDto.setAlias(member.getAlias());
        memberReqDto.setPhone(member.getPhone());
        memberReqDto.setAddr(member.getAddr());
        memberReqDto.setImage(member.getImage());
        memberReqDto.setIsKakao(member.isKakao());
        memberReqDto.setIsMembership(member.isMembership());
        return memberReqDto;
    }
}
