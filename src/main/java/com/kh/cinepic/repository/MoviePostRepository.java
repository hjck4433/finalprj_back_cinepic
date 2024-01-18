package com.kh.cinepic.repository;

import com.kh.cinepic.entity.MoviePost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoviePostRepository extends JpaRepository<MoviePost,Long> {
}
