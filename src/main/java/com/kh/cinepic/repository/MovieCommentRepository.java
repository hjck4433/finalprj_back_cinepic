package com.kh.cinepic.repository;

import com.kh.cinepic.entity.Movie;
import com.kh.cinepic.entity.MovieComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieCommentRepository extends JpaRepository<MovieComment, Long> {
    Page<MovieComment> findByMovie(Movie movie, Pageable pageable);
}
