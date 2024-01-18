package com.kh.cinepic.repository;

import com.kh.cinepic.entity.Bookmark;
import com.kh.cinepic.entity.Member;
import com.kh.cinepic.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository <Bookmark, Long> {
   
}
