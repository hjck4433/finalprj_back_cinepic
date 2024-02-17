package com.kh.cinepic.service;

import com.kh.cinepic.dto.*;
import com.kh.cinepic.entity.Admin;
import com.kh.cinepic.entity.Member;
import com.kh.cinepic.entity.RefreshToken;
import com.kh.cinepic.entity.RefreshTokenAdmin;
import com.kh.cinepic.jwt.TokenProvider;
import com.kh.cinepic.repository.AdminRepository;
import com.kh.cinepic.repository.MemberRepository;
import com.kh.cinepic.repository.RefreshTokenAdminRepository;
import com.kh.cinepic.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final AuthenticationManagerBuilder managerBuilder; // 인증 담당 클래스
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final AdminRepository adminRepository;
    private final RefreshTokenAdminRepository refreshAdminRepository;

    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    // member 이메일, 닉네임, 전화번호 중복 확인
    public Boolean checkUnique(int type, String info){
        boolean isUnique;
        switch(type){
            case 0 :
                isUnique = memberRepository.existsByEmail(info);
                break;
            case 1 :
                isUnique = memberRepository.existsByAlias(info);
                break;
            case 2:
                isUnique = memberRepository.existsByPhone(info);
                break;
            case 3:
                if(memberRepository.existsByEmail(info)) {
                    Member member = memberRepository.findByEmail(info)
                            .orElseThrow(() -> new RuntimeException("해당 이메일로 회원이 존재하지 않습니다"));
                    isUnique= !member.isKakao();
                }else {
                    isUnique = false;
                }
                break;
            default: isUnique = true; log.info("중복체크 구분이 잘 못 되었습니다.");
        }
        return isUnique;
    }

    // member 회원 가입

    public MemberResDto join(MemberReqDto memberReqDto) {
        log.info("join isKakao : {}", memberReqDto.getIsKakao());
        if(memberRepository.existsByEmail(memberReqDto.getEmail())){
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }
        Member member = memberReqDto.toEntity(passwordEncoder);
        return MemberResDto.of(memberRepository.save(member));
    }

    // member 로그인

    public TokenDto login(MemberReqDto memberReqDto) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = memberReqDto.toAuthentication();
            log.info("authenticationToken : {}", authenticationToken);

            Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);
            log.info("authentication : {}", authentication);

            TokenDto token = tokenProvider.genterateTokenDto(authentication);

            //refreshToken DB에 저장
            Member member = memberRepository.findByEmail(memberReqDto.getEmail())
                    .orElseThrow(() -> new RuntimeException("존재하지 않는 이메일입니다."));

            //탈퇴한 회원인지 체크
            if(member.isWithdraw()){
                return null;
            }

            //이미 db에 해당 계정으로 저장된 refreshToken 정보가 있다면 삭제
            log.info("Exiists by member : {}", refreshTokenRepository.existsByMember(member));
            if(refreshTokenRepository.existsByMember(member)){
                refreshTokenRepository.deleteByMember(member);
            }

            RefreshToken refreshToken = new RefreshToken();
            String encodedToken = token.getRefreshToken();
            refreshToken.setRefreshToken(encodedToken.concat("="));
            refreshToken.setRefreshTokenExpiresIn(token.getRefreshTokenExpiresIn());
            refreshToken.setMember(member);

            refreshTokenRepository.save(refreshToken);

            return token;
        }catch (Exception e) {
            log.error("로그인 중 에러 발생 : ", e);
            throw new RuntimeException("로그인 중 에러 발생", e);
        }

    }

    // 비밀번호 변경
    public boolean changePw (MemberReqDto memberReqDto) {
        try {
            Member member = memberRepository.findByEmail(memberReqDto.getEmail())
                    .orElseThrow(() -> new RuntimeException("해당 이메일의 회원이 없습니다."));

            // 카카오 회원인 경우 비밀번호 수정 X
            log.info("카카오 회원? : {}", member.isKakao());
            if(!member.isKakao()){
                member.setPassword(passwordEncoder.encode(memberReqDto.getPassword()));
            }else {
                return false;
            }

            memberRepository.save(member);

            return true;
        }catch (Exception e) {
            log.error("비밀번호 변경 중 에러 : ", e);
            return false;
        }
    }

    // 관리자 추가
    public boolean addAdmin (AdminReqDto adminReqDto) {
        log.info("새 관리자 추가 시도");
        boolean rst = false;
        if(adminRepository.existsById(adminReqDto.getAdminId())) {
            throw new RuntimeException("이미 존재하는 관리자 입니다.");
        }
        Admin admin = adminReqDto.toEntity(passwordEncoder);
        Admin addedAdmin = adminRepository.save(admin);
        if (addedAdmin != null) {
            rst = true;
            log.info("관리자 추가 성공");
        }else {
            rst = false;
            log.info("관리자 추가 실패");
        }
        return rst;
    }

    // 관리자 로그인
    public TokenDto adminLogin(AdminReqDto adminReqDto) {
        UsernamePasswordAuthenticationToken authenticationToken = adminReqDto.toAuthentication();
        log.info("authenticationToken(Admin) : {}", authenticationToken);

        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);
        log.info("authentication(Admin) : {}", authentication);

        TokenDto token = tokenProvider.genterateTokenDto(authentication);

        // 관리자 refreshToken DB에 저장
        Admin admin = adminRepository.findById(adminReqDto.getAdminId())
                .orElseThrow(() -> new RuntimeException("관리자 정보가 존재 하지 않습니다."));

        // 이미 DB에 해당 계정으로 저장된 refreshToken 정보가 있다면 삭제
        log.info("Exists by Admin : {}", refreshAdminRepository.existsByAdmin(admin));
        if(refreshAdminRepository.existsByAdmin(admin)){
            refreshAdminRepository.deleteByAdmin(admin);
        }

        RefreshTokenAdmin refreshTokenAdmin = new RefreshTokenAdmin();
        String encodedToken = token.getRefreshToken();
        refreshTokenAdmin.setRefreshToken(encodedToken.concat("="));
        refreshTokenAdmin.setRefreshTokenExpiresIn(token.getRefreshTokenExpiresIn());
        refreshTokenAdmin.setAdmin(admin);

        refreshAdminRepository.save(refreshTokenAdmin);

        return token;
    }

    // 리프레시 토큰으로 엑세스 토큰 재 발급
    public AccessTokenDto refreshAccessToken(String refreshToken) {
        log.info("refreshToken : {}", refreshToken);
        log.info("일반 refreshExist : {}", refreshTokenRepository.existsByRefreshToken(refreshToken));
        log.info("관리자 refreshExist : {}", refreshAdminRepository.existsByRefreshToken(refreshToken));

        // DB에 일치하는 refreshToken이 있으면
        if(refreshTokenRepository.existsByRefreshToken(refreshToken) || refreshAdminRepository.existsByRefreshToken(refreshToken)) {
            // refreshToken 검증
            try {
                if(tokenProvider.validateToken(refreshToken)) {
                    return tokenProvider.generateAccessTokenDto(tokenProvider.getAuthentication(refreshToken));
                }
            }catch (RuntimeException e) {
                log.error("토큰 유효성 검증 중 예외 발생 : {}", e.getMessage());
            }
        }
        return null;
    }

}
