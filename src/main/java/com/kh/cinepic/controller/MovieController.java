package com.kh.cinepic.controller;

import com.kh.cinepic.dto.MovieCommentDto;
import com.kh.cinepic.security.SecurityUtil;
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

    // movie_comment
    // 관람평 저장
    @PostMapping("/new")
    public ResponseEntity<Boolean> saveMovieComment(@RequestBody MovieCommentDto movieCommentDto) {
        log.info("댓글 저장 진입");
        Long id = SecurityUtil.getCurrentMemberId();
        return ResponseEntity.ok(movieCommentService.saveMovieComment(movieCommentDto, id));
    }

    // 관람평 전체 리스트 조회
    @GetMapping("/{id}")
    public ResponseEntity<List<MovieCommentDto>> getMovieComment(@PathVariable Long id) {
        List<MovieCommentDto> list = movieCommentService.getMovieComment(id);
        return ResponseEntity.ok(list);
    }

    // 관람평 수정
    @PostMapping("/modify")
    public ResponseEntity<Boolean> modifyMovieComment(@RequestBody MovieCommentDto movieCommentDto) {
        boolean result = movieCommentService.modifyMovieComment(movieCommentDto);
        return ResponseEntity.ok(result);
    }

    // 관람평 삭제
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteMovieComment(@PathVariable Long id) {
        boolean result = movieCommentService.deleteMovieComment(id);
        return ResponseEntity.ok(result);
    }

    // 총 페이지 수
    @GetMapping("/page/{movieId}")
    public ResponseEntity<Integer> getTotalMovieCommentPages(@PathVariable Long movieId,
                                                             @RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "5") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        int pageCnt = movieCommentService.getTotalMovieCommentPages(pageRequest, movieId);
        return ResponseEntity.ok(pageCnt);
    }

    // 페이지네이션
    @GetMapping("page/list/{movieId}")
    public ResponseEntity<List<MovieCommentDto>> getPagedMovieComments(@PathVariable Long movieId,
                                                                       @RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "5") int size) {
        List<MovieCommentDto> list = movieCommentService.getPagedMovieComments(page, size, movieId);
        return ResponseEntity.ok(list);
    }




















}
