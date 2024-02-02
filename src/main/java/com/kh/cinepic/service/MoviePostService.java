package com.kh.cinepic.service;

import com.kh.cinepic.dto.MoviePostDto;
import com.kh.cinepic.dto.MoviePostReqDto;
import com.kh.cinepic.entity.Member;
import com.kh.cinepic.entity.Movie;
import com.kh.cinepic.entity.MoviePost;
import com.kh.cinepic.repository.MemberRepository;
import com.kh.cinepic.repository.MoviePostRepository;
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
public class MoviePostService {
    private final MemberRepository memberRepository;
    private final MovieRepository movieRepository;
    private final MoviePostRepository moviePostRepository;

    // 영화 포스트 등록
    public boolean saveMoviePost(MoviePostReqDto moviePostReqDto, Long id) {
        try {
            MoviePost moviePost = new MoviePost();
            Member member = memberRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("해당 회원이 존재하지 않습니다.")
            );
            Movie movie = movieRepository.findById(moviePostReqDto.getMovieId()).orElseThrow(
                    () -> new RuntimeException("해당 영화가 존재하지 않습니다.")
            );
            moviePost.setMember(member);
            moviePost.setMovie(movie);
            moviePost.setPostImage(moviePostReqDto.getPostImage());
            moviePost.setPostTitle(moviePostReqDto.getPostTitle());
            moviePost.setPostContent(moviePostReqDto.getPostContent());

            moviePostRepository.save(moviePost);
            return true;
        } catch (Exception e) {
            log.error("등록 중 오류 : {}", (Object) e.getStackTrace());
            return false;
        }
    }

    // 영화 포스트 상세 조회
    public MoviePostDto getMoviePost(Long postId) {
        MoviePost moviePost = moviePostRepository.findById(postId)
                .orElseThrow();
        MoviePostDto moviePostDto;
        moviePostDto = convertEntityToDto(moviePost);

        return moviePostDto;
    }

    // 영화 포스트 수정
    public Boolean modifyMoviePost(MoviePostDto moviePostDto) {
        try {
            MoviePost moviePost = moviePostRepository.findById(moviePostDto.getPostId()).orElseThrow(
                    () -> new RuntimeException("해당 포스트가 존재하지 않습니다.")
            );
            if (moviePostDto.getPostImage() != null) {
                moviePost.setPostImage(moviePostDto.getPostImage());
            }
            if (moviePostDto.getPostTitle() != null) {
                moviePost.setPostTitle(moviePostDto.getPostTitle());
            }
            if (moviePostDto.getPostContent() != null) {
                moviePost.setPostContent(moviePostDto.getPostContent());
            }
            moviePostRepository.save(moviePost);
            return true;
        } catch (Exception e) {
            log.error("포스팅 수정 중 오류 : {}", (Object) e.getStackTrace());
            return false;
        }
    }

    // 영화 포스트 삭제
    public boolean deleteMoviePost(Long id) {
        try {
            MoviePost moviePost = moviePostRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("해당 포스트가 존재하지 않습니다.")
            );
            moviePostRepository.delete(moviePost);
            return true;
        } catch (Exception e) {
            log.error("포스트 삭제 중 오류 : {}", (Object) e.getStackTrace());
            return false;
        }
    }

    // 영화 포스트 페이지 수 count
    public int getTotalMoviePostPages(Pageable pageable, Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(
                () -> new RuntimeException("해당 영화가 존재하지 않습니다." + movieId)
        );
        Page<MoviePost> moviePostPage = moviePostRepository.findByMovie(movie, pageable);
        return moviePostPage.getTotalPages();
    }

    // 영화 포스트 최신순 페이지 네이션
    public List<MoviePostDto> getPagedMoviePostList(int page, int size, Long movieId) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("postRegDate")));
        Movie movie = movieRepository.findById(movieId).orElseThrow(
                () -> new RuntimeException("해당하는 movieId값이 없습니다." + movieId)
        );
        List<MoviePost> moviePosts = moviePostRepository.findByMovie(movie, pageable).getContent();
        List<MoviePostDto> moviePostDtos = new ArrayList<>();
        for(MoviePost moviePost : moviePosts) {
            moviePostDtos.add(convertEntityToDto(moviePost));
        }
        return moviePostDtos;
    }

    // movie관련 entity → DTO로 변환
    private MoviePostDto convertEntityToDto (MoviePost moviePost) {
        MoviePostDto moviePostDto = new MoviePostDto();
        moviePostDto.setPostId(moviePost.getId());
        moviePostDto.setImage(moviePost.getMember().getImage());
        moviePostDto.setAlias(moviePost.getMember().getAlias());
        moviePostDto.setPostImage(moviePost.getPostImage());
        moviePostDto.setPostTitle(moviePost.getPostTitle());
        moviePostDto.setPostContent(moviePostDto.getPostContent());
        moviePostDto.setPostRegDate(moviePost.getPostRegDate());
        return moviePostDto;
    }
}
