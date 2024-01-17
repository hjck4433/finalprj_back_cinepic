package com.kh.cinepic.service;

import com.kh.cinepic.entity.Admin;
import com.kh.cinepic.entity.Member;
import com.kh.cinepic.repository.AdminRepository;
import com.kh.cinepic.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerUserDetailService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 기존 회원정보 조회
        Optional<Member> memberOptional = memberRepository.findByEmail(username);
        if (memberOptional.isPresent()) {
            return createUserDetails(memberOptional.get());
        }

        // 회원 정보에 없으면 관리자 정보 조회
        Optional<Admin> adminOptional = adminRepository.findById(username);
        if (adminOptional.isPresent()) {
            return createAdminDetails(adminOptional.get());
        }

        // 둘다 없으면 예외 처리
        throw new UsernameNotFoundException(username + "을 DB에서 찾을 수 없습니다.");
    }

    // DB에서 가져온 회원 정보를 UserDetails 타입으로 변환한다.
    private UserDetails createUserDetails(Member member) {
        // 권한 정보를 문자열로 변환
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(member.getAuthority().toString());
        // UserDetails 타입의 객체를 생성해 리턴
        return new User(
                String.valueOf(member.getId()),
                member.getPassword(),
                Collections.singleton(grantedAuthority)
        );
    }

    // DB에서 가져온 관리자 정보를 UserDetails 타입으로 변환
    private UserDetails createAdminDetails(Admin admin) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(admin.getAuthority().toString());
        //UserDetails 타입의 객체를 생성해 리턴
        return new User(
                String.valueOf(admin.getAdminId()),
                admin.getAdminPassword(),
                Collections.singleton(grantedAuthority)
        );
    }
}
