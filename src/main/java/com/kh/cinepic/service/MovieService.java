package com.kh.cinepic.service;


import com.kh.cinepic.common.MovieInfo;
import com.kh.cinepic.documents.MovieDocument;
import com.kh.cinepic.dto.MovieDto;
import com.kh.cinepic.entity.Movie;
import com.kh.cinepic.repository.MovieDocRepository;
import com.kh.cinepic.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieService {
    private final MovieRepository movieRepository;
    private final MovieDocRepository movieDocRepository;

    public MovieDto getMovieDetail(Long movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow();
        MovieDto movieDto = convertToDto(movie);
        return movieDto;
    }


    public List<MovieDto> searchMovies(String keyword,String sortType, int page, int size) {
        Page<MovieDocument> moviePage;

        if (keyword == null || keyword.trim().isEmpty()) {
            // 키워드 없는경우
            if ("recent".equals(sortType)) {
                // 최신영화 순
                moviePage = movieDocRepository.findAllByOrderByMovieReleaseDesc(PageRequest.of(page, size));
            } else {
                // 예전영화 순
                moviePage = movieDocRepository.findAllByOrderByMovieReleaseAsc(PageRequest.of(page, size));
            }
        } else {
            // 키워드 있는 경우 / sortType(정렬)에 따라 결과 다름
            Sort sort = "recent".equals(sortType) ? Sort.by(Sort.Direction.DESC, "movieRelease") : Sort.by(Sort.Direction.DESC, "_score");
            PageRequest pageable = PageRequest.of(page, size, sort);
            moviePage = movieDocRepository.findByKeyword(keyword, pageable);
        }

        // MovieDocument 를 Dto로 변환
        List<MovieDto> movieDtos = moviePage.getContent().stream()
                .map(MovieService::convertToDto)
                .collect(Collectors.toList());

        return movieDtos;
    }

    // Entity 또는 Document Dto로 변환
    public static <T extends MovieInfo> MovieDto convertToDto(T movieInfo) {
        MovieDto movieDto = new MovieDto();
        movieDto.setMovieId(movieInfo.getMovieId());
        movieDto.setMovieTitle(movieInfo.getMovieTitle());
        movieDto.setMoviePoster(movieInfo.getMoviePoster());
        movieDto.setMovieTitleEng(movieInfo.getMovieTitleEng());
        movieDto.setMovieRelease(movieInfo.getMovieRelease());
        movieDto.setMovieGenre(movieInfo.getMovieGenre());
        movieDto.setMovieNation(movieInfo.getMovieNation());
        movieDto.setMovieGrade(movieInfo.getMovieGrade());
        movieDto.setMovieRuntime(movieInfo.getMovieRuntime());
        movieDto.setMovieScore(movieInfo.getMovieScore());
        movieDto.setMovieDirector(movieInfo.getMovieDirector());
        movieDto.setMovieActors(movieInfo.getMovieActors());
        movieDto.setMoviePlot(movieInfo.getMoviePlot());
        movieDto.setMovieStills(movieInfo.getMovieStills());
        return movieDto;
    }

}
