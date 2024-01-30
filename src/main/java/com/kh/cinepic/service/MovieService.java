package com.kh.cinepic.service;


import com.kh.cinepic.dto.MovieDto;
import com.kh.cinepic.entity.Movie;
import com.kh.cinepic.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieService {
    private final MovieRepository movieRepository;

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
}
