package com.kh.cinepic.service;


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
        MovieDto movieDto;
        movieDto = convertEntityToDto(movie);
        return movieDto;
    }

    // movie관련 entity → DTO로 변환
    private MovieDto convertEntityToDto (Movie movie) {
        MovieDto movieDto = new MovieDto();
        movieDto.setMovieId(movie.getMovieId());
        movieDto.setMovieTitle(movie.getMovieTitle());
        movieDto.setMoviePoster(movie.getMoviePoster());
        movieDto.setMovieTitleEng(movie.getMovieTitleEng());
        movieDto.setMovieRelease(movie.getMovieRelease());
        movieDto.setMovieGenre(movie.getMovieGenre());
        movieDto.setMovieNation(movie.getMovieNation());
        movieDto.setMovieGrade(movie.getMovieGrade());
        movieDto.setMovieRuntime(movie.getMovieRuntime());
        movieDto.setMovieScore(movie.getMovieScore());
        movieDto.setMovieDirector(movie.getMovieDirector());
        movieDto.setMovieActors(movie.getMovieActors());
        movieDto.setMoviePlot(movie.getMoviePlot());
        movieDto.setMovieStills(movie.getMovieStills());
        return movieDto;
    }

    private MovieDto convertDocToDto (MovieDocument movie) {
        MovieDto movieDto = new MovieDto();
        movieDto.setMovieId(movie.getMovieId());
        movieDto.setMovieTitle(movie.getMovieTitle());
        movieDto.setMoviePoster(movie.getMoviePoster());
        movieDto.setMovieTitleEng(movie.getMovieTitleEng());
        movieDto.setMovieRelease(movie.getMovieRelease());
        movieDto.setMovieGenre(movie.getMovieGenre());
        movieDto.setMovieNation(movie.getMovieNation());
        movieDto.setMovieGrade(movie.getMovieGrade());
        movieDto.setMovieRuntime(movie.getMovieRuntime());
        movieDto.setMovieScore(movie.getMovieScore());
        movieDto.setMovieDirector(movie.getMovieDirector());
        movieDto.setMovieActors(movie.getMovieActors());
        movieDto.setMoviePlot(movie.getMoviePlot());
        movieDto.setMovieStills(movie.getMovieStills());
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

        // Convert each MovieDocument to MovieDto
        List<MovieDto> movieDtos = moviePage.getContent().stream()
                .map(this::convertDocToDto)
                .collect(Collectors.toList());

        return movieDtos;
    }

}
