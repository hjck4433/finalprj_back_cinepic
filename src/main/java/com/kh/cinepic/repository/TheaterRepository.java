package com.kh.cinepic.repository;

import com.kh.cinepic.entity.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TheaterRepository extends JpaRepository<Theater,Long> {
    List<Theater> findByTheaterAddrContaining(String keyword);
    Optional<Theater> findById(Long theaterId);
}
