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

    public List<Map<String, MovieDto>> movieDtos(Long id, String genre) {
        List<Map<String, MovieDto>> movieList = new ArrayList<>();

        // 회원 취향
        if(id != null) {
            UserPreferDto preferDto = userPreference(id);
            Map<String, String> recsMovies = movieRecsList(preferDto);

        }else {
            UserPreferDto preferDto = genrePrefer(genre);
            Map<String, String> recsMovies = movieRecsList(preferDto);
        }

        return movieList;

    }

    public List<Map<String, MovieDto>> findMovieList(Map<String, String> recsMovies) {
        List<Map<String, MovieDto>> movieList = new ArrayList<>();

        for(Map.Entry<String, String> entry : recsMovies.entrySet()) {
            String key = entry.getKey();
            String movieId = entry.getValue();

            Movie movie = movieRepository.findById(movieId)
                    .orElseThrow(() -> new RuntimeException("해당 영화가 없습니다."));


        }

        return movieList;
    }


    public Map<String, String> movieRecsList(UserPreferDto preferDto) {
        Map<String, String> recsMovies = new HashMap<>();
        log.info("파이썬을 통해 영화추천 받으러 가는 중");
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "http://localhost:5000/api/recommendation";

        return recsMovies;
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
