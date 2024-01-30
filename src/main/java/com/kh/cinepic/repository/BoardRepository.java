package com.kh.cinepic.repository;

import com.kh.cinepic.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    // 관리자 - board 모든 정보 가져오기
    Page<Board> findAll(Pageable pageable);

    // categoryName 별 게시글 수 조회
    @Query("SELECT b.category.categoryName AS categoryName, COUNT(b) AS count FROM Board b GROUP BY b.category.categoryName")
    Map<String, Long> countByCategoryName();

    // gatherType 별 게시글 수 조회
    @Query("SELECT b.gatherType, COUNT(b) FROM Board b GROUP BY b.gatherType")
    Map<String, Long> countGatherType();

}
