package com.kh.cinepic.repository;

import com.kh.cinepic.entity.Bookmark;
import com.kh.cinepic.entity.Member;
import com.kh.cinepic.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository <Bookmark, Long> {
   boolean existsByMemberAndMovie(Member member, Movie movie);
   Optional<Bookmark> findByMemberAndMovie(Member member, Movie movie);

   Page<Bookmark> findAllByMember(Member member, Pageable pageable);

   @Query(value = "SELECT COALESCE(GROUP_CONCAT(b.movie_id), '') FROM bookmark b WHERE b.member_id = :memberId", nativeQuery = true)
   String findMovieIdsByMemberId(@Param("memberId") Long memberId);

}
