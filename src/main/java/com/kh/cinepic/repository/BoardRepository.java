package com.kh.cinepic.repository;

import com.kh.cinepic.entity.Board;
import com.kh.cinepic.entity.Category;
import com.kh.cinepic.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    // 관리자 - board 모든 정보 가져오기
    Page<Board> findAll(Pageable pageable);

    @Query("SELECT b FROM Board b WHERE" +
            "(:keyword IS NULL OR (:keyword IS NOT NULL AND " +
            "(LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(b.boardContent) LIKE LOWER(CONCAT('%', :keyword, '%'))))) AND " +
            "LOWER(b.category.categoryName) LIKE LOWER(CONCAT('%', :categoryName, '%')) AND " +
            "LOWER(b.gatherType) LIKE LOWER(CONCAT('%', :gatherType, '%'))")
    Page<Board> findByKeywordAndCategoryNameAndGatherType(
            @Param("keyword") String keyword,
            @Param("categoryName") String categoryName,
            @Param("gatherType") String gatherType,
            Pageable pageable
    );

    // 회원이 작성한 보드
    Page<Board> findByMember(Member member, Pageable pageable);

    // 회원이 작성한 댓글이 포함 된 보드
    @Query("SELECT b FROM Board b " +
            "WHERE b.id IN (SELECT c.board.id FROM Comment c WHERE c.member = :member)")
    Page<Board> findByCommentingMember(@Param("member") Member member, Pageable pageable);


    // categoryName 별 게시글 수 조회
    long countByCategory(Category category);

    // gatherType 별 게시글 수 조회-씨네크루만
    long countByCategory_CategoryNameAndGatherType(String categoryName, String gatherType);
}

