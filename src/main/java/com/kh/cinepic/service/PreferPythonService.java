package com.kh.cinepic.service;

import com.kh.cinepic.dto.MovieDto;
import com.kh.cinepic.dto.UserPreferDto;
import com.kh.cinepic.entity.Member;
import com.kh.cinepic.entity.Movie;
import com.kh.cinepic.entity.Prefer;
import com.kh.cinepic.entity.PreferMovie;
import com.kh.cinepic.repository.*;
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
    private final PreferMovieRepository preferMovieRepository;


    // 회원 / 비회원 여부에 따라 영화 추천 다르게 요청
    public List<Map<String, MovieDto>> getMovieList(String genre) {
        List<Map<String, MovieDto>> movieList = new ArrayList<>();

        UserPreferDto preferDto = genrePrefer(genre);
        Map<String, Integer> recsMovies = movieRecsList(preferDto);
        movieList = findMovieList(recsMovies);

        return movieList;
    }

    // 리스트에 추천 영화 담기 (비회원)
    public List<Map<String, MovieDto>> findMovieList(Map<String, Integer> recsMovies) {
        List<Map<String, MovieDto>> movieList = new ArrayList<>();

        for(Map.Entry<String, Integer> entry : recsMovies.entrySet()) {
            String key = entry.getKey();
            Long movieId = entry.getValue().longValue();


            Movie movie = movieRepository.findById(movieId)
                    .orElseThrow(() -> new RuntimeException("해당 영화가 없습니다."));

            MovieDto movieDto = MovieService.convertToDto(movie);

            Map<String, MovieDto> movieMap = new HashMap<>();
            movieMap.put(key, movieDto);
            movieList.add(movieMap);
        }

        return movieList;
    }

    // 회원 추천 영화
    public List<Map<String, MovieDto>> getPreferMovie(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 회원이 존재 하지 않습니다."));

        PreferMovie preferMovie = preferMovieRepository.findByMember(member)
                .orElseThrow(() -> new RuntimeException("해당 회원의 취향 정보가 없습니다."));

        List<Map<String, MovieDto>> movieList = new ArrayList<>();

        // recs1
        Movie recs1 = preferMovie.getRecs1();
        if (recs1 != null) {
            Map<String, MovieDto> recs1Map = new HashMap<>();
            recs1Map.put("recs1", MovieService.convertToDto(recs1));
            movieList.add(recs1Map);
        }

        // recs2
        Movie recs2 = preferMovie.getRecs2();
        if (recs2 != null) {
            Map<String, MovieDto> recs2Map = new HashMap<>();
            recs2Map.put("recs2", MovieService.convertToDto(recs2));
            movieList.add(recs2Map);
        }

        // recs3
        Movie recs3 = preferMovie.getRecs3();
        if (recs3 != null) {
            Map<String, MovieDto> recs3Map = new HashMap<>();
            recs3Map.put("recs3", MovieService.convertToDto(recs3));
            movieList.add(recs3Map);
        }

        // recs4
        Movie recs4 = preferMovie.getRecs4();
        if (recs4 != null) {
            Map<String, MovieDto> recs4Map = new HashMap<>();
            recs4Map.put("recs4", MovieService.convertToDto(recs4));
            movieList.add(recs4Map);
        }

        return movieList;

    }

    // 회원별 추천 영화리스트 DB 저장
    public boolean savePreferMovie(Long id) {
        try {
            Member member = memberRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("해당 회원이 존재하지 않습니다."));

            UserPreferDto preferDto = userPreference(id);
            Map<String, Integer> recsMovies = movieRecsList(preferDto);

            PreferMovie preferMovie;

            if(preferMovieRepository.existsByMember(member)) {
                preferMovie = preferMovieRepository.findByMember(member)
                        .orElseThrow(() -> new RuntimeException("해당 회원의 취향 정보가 없습니다."));
            }else {
                preferMovie = new PreferMovie();
                preferMovie.setMember(member);
            }

            for(Map.Entry<String, Integer> entry : recsMovies.entrySet()) {
                String key = entry.getKey();
                Long movieId = entry.getValue().longValue();

                Movie movie = movieRepository.findById(movieId)
                        .orElseThrow(() -> new RuntimeException("해당 영화가 없습니다."));

                switch (key) {
                    case "recs1":
                        preferMovie.setRecs1(movie);
                        break;
                    case "recs2":
                        preferMovie.setRecs2(movie);
                        break;
                    case "recs3":
                        preferMovie.setRecs3(movie);
                        break;
                    case "recs4":
                        preferMovie.setRecs4(movie);
                        break;
                    default:
                        break;
                }

            }

            preferMovieRepository.save(preferMovie);

            return true;
        }catch (Exception e) {
            log.error("추천영화 저장 중 오류 :",e);
            return false;
        }
    }


    // 파이썬에 영화 추천 리스트 요청
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

}
