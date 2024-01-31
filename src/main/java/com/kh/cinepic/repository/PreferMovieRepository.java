package com.kh.cinepic.repository;

import com.kh.cinepic.entity.Member;
import com.kh.cinepic.entity.PreferMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PreferMovieRepository extends JpaRepository<PreferMovie, Long> {
    Optional<PreferMovie> findByMember(Member member);
    boolean existsByMember(Member member);
}
