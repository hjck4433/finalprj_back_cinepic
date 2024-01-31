package com.kh.cinepic.repository;

import com.kh.cinepic.entity.Member;
import com.kh.cinepic.entity.Prefer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PreferRepository extends JpaRepository<Prefer,Long> {
    Optional<Prefer> findByMember(Member member);

    boolean existsByMember(Member member);
}
