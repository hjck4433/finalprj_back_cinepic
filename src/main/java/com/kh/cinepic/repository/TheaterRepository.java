package com.kh.cinepic.repository;

import com.kh.cinepic.entity.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TheaterRepository extends JpaRepository<Theater,Long> {
    List<Theater> findByProvinceContaining(String keyword);
    List<Theater> findByCityContaining(String keyword);
    List<Theater> findByTheaterAddrContaining(String keyword);
}
