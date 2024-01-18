package com.kh.cinepic.service;

import com.kh.cinepic.dto.MovieCommentDto;
import com.kh.cinepic.entity.Member;
import com.kh.cinepic.entity.Movie;
import com.kh.cinepic.entity.MovieComment;
import com.kh.cinepic.repository.MemberRepository;
import com.kh.cinepic.repository.MovieCommentRepository;
import com.kh.cinepic.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieCommentService {
    private final MemberRepository memberRepository;
    private final MovieRepository movieRepository;
    private final MovieCommentRepository movieCommentRepository;

    // 관람평 등록
    public boolean saveMovieComment(MovieCommentDto movieCommentDto, Long id) {
        try {
            MovieComment movieComment = new MovieComment();
            Member member = memberRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("해당 회원이 존재하지 않습니다.")
            );
            Movie movie = movieRepository.findById(movieCommentDto.getMovieId()).orElseThrow(
                    () -> new RuntimeException("해당 영화가 존재하지 않습니다.")
            );
            movieComment.setMember(member);
            movieComment.setMovie(movie);
            movieComment.setRatingField(movieCommentDto.getRatingField());
            movieComment.setRatingNum(movieCommentDto.getRatingNum());
            movieComment.setRatingText(movieCommentDto.getRatingText());

            movieCommentRepository.save(movieComment);
            return true;
        } catch (Exception e) {
            log.error("등록 중 오류 : {}", (Object) e.getStackTrace());
            return false;
        }
    }

    // 관람평 전체 조회
    public List<MovieCommentDto> getMovieComment() {
        List<MovieComment> movieComments = movieCommentRepository.findAll();
        List<MovieCommentDto> movieCommentDtos = new ArrayList<>();
        for (MovieComment movieComment : movieComments) {
            movieCommentDtos.add(convertEntityToDto(movieComment));
        }
        return movieCommentDtos;
    }

    // 관람평 수정
    public boolean modifyMovieComment(MovieCommentDto movieCommentDto) {
        try {
            MovieComment movieComment = movieCommentRepository.findById(movieCommentDto.getCommentId()).orElseThrow(
                    () -> new RuntimeException("해당 게시글이 존재하지 않습니다."));

            if (movieCommentDto.getRatingField() != null) {
                movieComment.setRatingField(movieCommentDto.getRatingField());
            }
            if (movieCommentDto.getRatingNum() != null) {
                movieComment.setRatingNum(movieCommentDto.getRatingNum());
            }
            if (movieCommentDto.getRatingText() != null) {
                movieComment.setRatingText(movieCommentDto.getRatingText());
            }
            movieCommentRepository.save(movieComment);
            return true;
        } catch (Exception e) {
            log.error("관람평 수정 중 오류 : {}", (Object) e.getStackTrace());
            return false;
        }
    }

    // 관람평 삭제
    public boolean deleteMovieComment(Long id) {
        try {
            MovieComment movieComment = movieCommentRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("해당 관람평이 존재하지 않습니다.")
            );
            movieCommentRepository.delete(movieComment);
            return true;
        } catch (Exception e) {
            log.error("관람평 삭제 중 오류 : {}", (Object) e.getStackTrace());
            return false;
        }
    }

    // 관람평 페이지 수 count
    public int getTotalMovieCommentPages(Pageable pageable, Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(
                () -> new RuntimeException("해당하는 영화가 없습니다." + movieId)
        );
        Page<MovieComment> movieCommentPage = movieCommentRepository.findByMovie(movie, pageable);
        return movieCommentPage.getTotalPages();
    }

    // 관람평 최신순 페이지네이션
    public List<MovieCommentDto> getPagedMovieComments(int page, int size, Long movieId) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("commentRegdate")));
        Movie movie = movieRepository.findById(movieId).orElseThrow(
                () -> new RuntimeException("해당하는 movieId값이 없습니다." + movieId)
        );
        List<MovieComment> movieComments = movieCommentRepository.findByMovie(movie, pageable).getContent();
        List<MovieCommentDto> movieCommentDtos = new ArrayList<>();
        for(MovieComment movieComment : movieComments) {
            movieCommentDtos.add(convertEntityToDto(movieComment));
        }
        return movieCommentDtos;
    }
    // movieComment entity → DTO로 변환
    private MovieCommentDto convertEntityToDto (MovieComment movieComment) {
        MovieCommentDto movieCommentDto = new MovieCommentDto();
        movieCommentDto.setCommentId(movieComment.getId());
        movieCommentDto.setImage(movieComment.getMember().getImage());
        movieCommentDto.setAlias(movieComment.getMember().getAlias());
        movieCommentDto.setRatingField(movieComment.getRatingField());
        movieCommentDto.setRatingNum(movieComment.getRatingNum());
        movieCommentDto.setRatingText(movieComment.getRatingText());
        return movieCommentDto;
    }

}
