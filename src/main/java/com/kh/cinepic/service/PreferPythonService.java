package com.kh.cinepic.service;

import com.kh.cinepic.dto.MovieDto;
import com.kh.cinepic.dto.UserPreferDto;
import com.kh.cinepic.entity.Member;
import com.kh.cinepic.entity.Movie;
import com.kh.cinepic.entity.Prefer;
import com.kh.cinepic.repository.BookmarkRepository;
import com.kh.cinepic.repository.MemberRepository;
import com.kh.cinepic.repository.MovieRepository;
import com.kh.cinepic.repository.PreferRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PreferPythonService {
    private final PreferRepository preferRepository;
    private final MemberRepository memberRepository;
    private final BookmarkRepository bookmarkRepository;
    private final MovieRepository movieRepository;

    public List<Map<String, MovieDto>> getMovieList(Long id, String genre) {
        List<Map<String, MovieDto>> movieList = new ArrayList<>();

        // 회원 취향
        if(id != null) {
            UserPreferDto preferDto = userPreference(id);
            Map<String, Integer> recsMovies = movieRecsList(preferDto);
            movieList = findMovieList(recsMovies);
        }else {
            UserPreferDto preferDto = genrePrefer(genre);
            Map<String, Integer> recsMovies = movieRecsList(preferDto);
            movieList = findMovieList(recsMovies);
        }

        return movieList;

    }

    public List<Map<String, MovieDto>> findMovieList(Map<String, Integer> recsMovies) {
        List<Map<String, MovieDto>> movieList = new ArrayList<>();

        for(Map.Entry<String, Integer> entry : recsMovies.entrySet()) {
            String key = entry.getKey();
            Long movieId = entry.getValue().longValue();


            Movie movie = movieRepository.findById(movieId)
                    .orElseThrow(() -> new RuntimeException("해당 영화가 없습니다."));

            MovieDto movieDto = convertEntityToDto(movie);

            Map<String, MovieDto> movieMap = new HashMap<>();
            movieMap.put(key, movieDto);
            movieList.add(movieMap);
        }

        return movieList;
    }


    public Map<String, Integer> movieRecsList(UserPreferDto preferDto) {
        log.info("파이썬을 통해 영화추천 받으러 가는 중");
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "http://localhost:5000/api/recommendation";

        HttpEntity<UserPreferDto> requestEntity = new HttpEntity<>(preferDto);

        ResponseEntity<Map<String, Integer>> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<Map<String, Integer>>() {});
        if(responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        }else {
            log.error("Request failed with status code: {}", responseEntity.getStatusCodeValue());
            return null;
        }
    }

    //  로그인 회원 영화 취향
    public UserPreferDto userPreference(Long id) {
        log.info("회원의 취향정보 담는 중");
        
        UserPreferDto preferDto = new UserPreferDto();

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 회원이 존재 하지 않습니다."));
        Prefer prefer = preferRepository.findByMember(member)
                .orElseThrow(() -> new RuntimeException("해당 회원의 취향정보가 없습니다."));

        String movieIds = bookmarkRepository.findMovieIdsByMemberId(id);

        preferDto.setPreferActors(prefer.getActorName());
        preferDto.setPreferDirectors(prefer.getDirectorName());
        preferDto.setPreferGenres(prefer.getGenre());
        preferDto.setMovieId(movieIds);
        
        return preferDto;
    }

    // 비로그인 메인 페이지 추천 요청
    public UserPreferDto genrePrefer(String genre) {
        UserPreferDto preferDto = new UserPreferDto();
        preferDto.setPreferActors("");
        preferDto.setPreferDirectors("");
        preferDto.setPreferGenres(genre);
        preferDto.setMovieId("");

        return preferDto;
    }

    public MovieDto convertEntityToDto (Movie movie) {
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
