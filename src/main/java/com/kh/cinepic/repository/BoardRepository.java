package com.kh.cinepic.repository;

import com.kh.cinepic.entity.Board;
import com.kh.cinepic.entity.Category;
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
    long countByCategory(Category category);

    // gatherType 별 게시글 수 조회-씨네크루만
    long countByCategory_CategoryNameAndGatherType(String categoryName, String gatherType);
}

