package com.kh.cinepic.service;

import com.kh.cinepic.dto.KakaoDto;
import com.kh.cinepic.entity.Kakao;
import com.kh.cinepic.repository.KakaoRepository;
import com.kh.cinepic.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class KakaoService {
    private final MemberRepository memberRepository;
    private final RestTemplate restTemplate;
    private final KakaoRepository kakaoRepository;

    public Map<String, Object> kakaoUserInfo (String kakaoToken) {
            Map<String, Object> kakaoInfo = new HashMap<>();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        headers.set("Authorization", "Bearer " + kakaoToken);

        String url = "https://kapi.kakao.com/v2/user/me";

        try {
            ResponseEntity<KakaoDto> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    KakaoDto.class
            );
            KakaoDto kakaoDto = responseEntity.getBody();
            boolean isExist = false;
            if (kakaoDto != null) {
                isExist = kakaoRepository.existsById(kakaoDto.getId());
                log.info("kakaoId exists? : {}", isExist);

                String kakaoEmail = kakaoDto.getKakaoAccount().getEmail();
                if(memberRepository.existsByEmail(kakaoEmail) && !isExist) {
                    log.error("카카오 : 이미 가입된 이메일 입니다.");
                    throw new RuntimeException("카카오 : 이미 가입된 이메일 입니다.");
                }

                if(!isExist)saveKakaoEntity(kakaoDto);
                else {
                    isExist = memberRepository.existsByEmail(kakaoDto.getKakaoAccount().getEmail());
                }
            }
            kakaoInfo.put("isMember", isExist);
            kakaoInfo.put("userInfo", kakaoDto);

            return kakaoInfo;
        }catch(Exception e) {
            log.error("카카오 가입 시도 중 오류 발생(카카오 서비스)");
            return null;
        }
    }

    private void saveKakaoEntity(KakaoDto kakaoDto) {
        Kakao kakao = kakaoDto.toEntity();
        kakaoRepository.save(kakao);
    }



}
