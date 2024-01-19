package com.kh.cinepic.repository;

import com.kh.cinepic.entity.Movie;
import com.kh.cinepic.entity.MoviePost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoviePostRepository extends JpaRepository<MoviePost,Long> {
    Page<MoviePost> findByMovie(Movie movie, Pageable pageable);
}
