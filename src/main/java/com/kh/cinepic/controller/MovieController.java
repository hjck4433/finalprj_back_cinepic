package com.kh.cinepic.controller;

import com.kh.cinepic.dto.MovieCommentDto;
import com.kh.cinepic.dto.MoviePostDto;
import com.kh.cinepic.service.MovieCommentService;
import com.kh.cinepic.service.MoviePostService;
import com.kh.cinepic.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/movie")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;
    private final MoviePostService moviePostService;
    private final MovieCommentService movieCommentService;

    // movie


    // movie_post
    // 포스트 총 페이지 수
    @GetMapping("/post/page/{movieId}")
    public ResponseEntity<Integer> getTotalMoviePostPages(@PathVariable Long movieId,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "5") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        int pageCnt = moviePostService.getTotalMoviePostPages(pageRequest, movieId);
        return ResponseEntity.ok(pageCnt);
    }

    // 포스트 페이지 네이션
    @GetMapping("/post/page/list/{movieId}")
    public ResponseEntity<List<MoviePostDto>> getPagedMoviePostList(@PathVariable Long movieId,
                                                                    @RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "5") int size) {
        List<MoviePostDto> list = moviePostService.getPagedMoviePostList(page, size, movieId);
        return ResponseEntity.ok(list);
    }


    // movie_comment
    // 관람평 전체 리스트 조회
    @GetMapping("/comment/{id}")
    public ResponseEntity<List<MovieCommentDto>> getMovieComment(@PathVariable Long id) {
        List<MovieCommentDto> list = movieCommentService.getMovieComment(id);
        return ResponseEntity.ok(list);
    }

    // 총 페이지 수
    @GetMapping("/comment/page/{movieId}")
    public ResponseEntity<Integer> getTotalMovieCommentPages(@PathVariable Long movieId,
                                                             @RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "5") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        int pageCnt = movieCommentService.getTotalMovieCommentPages(pageRequest, movieId);
        return ResponseEntity.ok(pageCnt);
    }

    // 페이지 네이션
    @GetMapping("/comment/page/list/{movieId}")
    public ResponseEntity<List<MovieCommentDto>> getPagedMovieComments(@PathVariable Long movieId,
                                                                       @RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "5") int size) {
        List<MovieCommentDto> list = movieCommentService.getPagedMovieComments(page, size, movieId);
        return ResponseEntity.ok(list);
    }
}
