package com.kh.cinepic.service;

import com.kh.cinepic.dto.PreferDto;
import com.kh.cinepic.entity.Member;
import com.kh.cinepic.entity.Prefer;
import com.kh.cinepic.repository.MemberRepository;
import com.kh.cinepic.repository.PreferRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;



@Slf4j
@RequiredArgsConstructor
@Service
public class PreferService {
    private final PreferRepository preferRepository;
    private final MemberRepository memberRepository;

    // 감독 존재 여부


    // 배우 존재 여부


    // 취향 등록
    public boolean savePrefer(PreferDto preferDto, Long id) {
        try {
            Member member = memberRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("회원 정보가 없습니다.")
            );

            Prefer prefer = new Prefer();
            prefer.setMember(member);
            prefer.setDirectorName(preferDto.getDirectorName());
            prefer.setActorName(preferDto.getActorName());
            prefer.setGenre(preferDto.getGenre());
            prefer.setGender(preferDto.getGender());
            preferRepository.save(prefer);

            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 회원id로 취향정보 가져오기
    public PreferDto getPreferByInfo (Long id) {
        Member member = memberRepository.findById(id).orElseThrow(
                () -> new RuntimeException("회원 정보가 없습니다.")
        );

        PreferDto preferDto = new PreferDto();
        Prefer prefer = preferRepository.findByMember(member)
                .orElseThrow(() -> new RuntimeException("해당 회원의 취향정보가 없습니다."));

        preferDto.setId(prefer.getId());
        preferDto.setActorName(prefer.getActorName());
        preferDto.setDirectorName(prefer.getDirectorName());
        preferDto.setGenre(prefer.getGenre());
        preferDto.setGender(prefer.getGender());

        return preferDto;
    }

    // 취향 수정
    public boolean modifyPrefer(PreferDto preferDto) {
         try {
             Prefer prefer = preferRepository.findById(preferDto.getId()).orElseThrow(() -> new RuntimeException("해당 취향이 존재하지 않습니다."));
             prefer.setDirectorName(preferDto.getDirectorName());
             prefer.setActorName(preferDto.getActorName());
             prefer.setGenre(preferDto.getGenre());
             prefer.setGender(preferDto.getGender());
             preferRepository.save(prefer);
             return true;
         } catch (Exception e) {
             e.printStackTrace();
             return false;
         }
    }
}