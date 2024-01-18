package com.kh.cinepic.repository;

import com.kh.cinepic.entity.MovieComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieCommentRepository extends JpaRepository<MovieComment, Long> {
}
