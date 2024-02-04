package com.kh.cinepic.repository;

import com.kh.cinepic.documents.MovieDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface MovieDocRepository extends ElasticsearchRepository<MovieDocument, Long> {

    // 최신 영화 순
    Page<MovieDocument> findAllByOrderByMovieReleaseDesc(Pageable pageable);

    // 예전 영화 순
    Page<MovieDocument> findAllByOrderByMovieReleaseAsc(Pageable pageable);

    @Query("{\"bool\": {\"should\": [" +
            "{\"match\": {\"movieTitle\": {\"query\": \"?0\", \"boost\": 5}}}," +
            "{\"match\": {\"movieGenre\": {\"query\": \"?0\", \"boost\": 4}}}," +
            "{\"match\": {\"movieActors\": {\"query\": \"?0\", \"boost\": 3}}}," +
            "{\"match\": {\"movieDirector\": {\"query\": \"?0\", \"boost\": 2}}}," +
            "{\"match\": {\"moviePlot\": {\"query\": \"?0\", \"boost\": 1}}}" +
            "]}}")
    Page<MovieDocument> findByKeyword(String keyword, Pageable pageable);
}
