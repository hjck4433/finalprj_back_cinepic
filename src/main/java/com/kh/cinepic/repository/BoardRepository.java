package com.kh.cinepic.repository;

import com.kh.cinepic.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    // 관리자 - board 모든 정보 가져오기
    Page<Board> findAll(Pageable pageable);
}
