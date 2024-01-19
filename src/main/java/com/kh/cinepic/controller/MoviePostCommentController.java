package com.kh.cinepic.controller;

import com.kh.cinepic.dto.MovieCommentDto;
import com.kh.cinepic.dto.MoviePostDto;
import com.kh.cinepic.security.SecurityUtil;
import com.kh.cinepic.service.MovieCommentService;
import com.kh.cinepic.service.MoviePostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/movieDetail")
@RequiredArgsConstructor
public class MoviePostCommentController {
    private final MoviePostService moviePostService;
    private final MovieCommentService movieCommentService;

    // movie_post
    // 포스트 저장
    @PostMapping("/post/new")
    public ResponseEntity<Boolean> saveMoviePost(@RequestBody MoviePostDto moviePostDto) {
        Long id = SecurityUtil.getCurrentMemberId();
        return ResponseEntity.ok(moviePostService.saveMoviePost(moviePostDto, id));
    }

    // 포스트 상세 조회 (test 필요)
    @GetMapping("/post/{id}")
    public ResponseEntity<MoviePostDto> getMoviePost(@PathVariable Long postId) {
        MoviePostDto moviePostDto = moviePostService.getMoviePost(postId);
        return ResponseEntity.ok(moviePostDto);
    }

    // 포스트 수정
    @PostMapping("/post/modify")
    public ResponseEntity<Boolean> modifyMoviePost(@RequestBody MoviePostDto moviePostDto) {
        boolean result = moviePostService.modifyMoviePost(moviePostDto);
        return ResponseEntity.ok(result);
    }

    // 포스트 삭제
    @DeleteMapping("/post/delete/{id}")
    public ResponseEntity<Boolean> deleteMoviePost(@PathVariable Long id) {
        boolean result = moviePostService.deleteMoviePost(id);
        return ResponseEntity.ok(result);
    }

    // movie_comment
    // 관람평 저장
    @PostMapping("/comment/new")
    public ResponseEntity<Boolean> saveMovieComment(@RequestBody MovieCommentDto movieCommentDto) {
        log.info("댓글 저장 진입");
        Long id = SecurityUtil.getCurrentMemberId();
        return ResponseEntity.ok(movieCommentService.saveMovieComment(movieCommentDto, id));
    }

    // 관람평 수정
    @PostMapping("/comment/modify")
    public ResponseEntity<Boolean> modifyMovieComment(@RequestBody MovieCommentDto movieCommentDto) {
        boolean result = movieCommentService.modifyMovieComment(movieCommentDto);
        return ResponseEntity.ok(result);
    }

    // 관람평 삭제
    @DeleteMapping("/comment/delete/{id}")
    public ResponseEntity<Boolean> deleteMovieComment(@PathVariable Long id) {
        boolean result = movieCommentService.deleteMovieComment(id);
        return ResponseEntity.ok(result);
    }
}
