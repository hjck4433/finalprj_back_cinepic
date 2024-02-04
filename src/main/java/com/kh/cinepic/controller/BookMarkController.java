package com.kh.cinepic.controller;

import com.kh.cinepic.dto.MovieDto;
import com.kh.cinepic.security.SecurityUtil;
import com.kh.cinepic.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/bookmark")
@RequiredArgsConstructor
public class BookMarkController {
    private final BookmarkService bookmarkService;

    @GetMapping("/isbookmark")
    public ResponseEntity<Boolean> getIsBookMark(@RequestParam Long movieId) {
        log.info("북마크 여부 확인 진입");
        Long memberId = SecurityUtil.getCurrentMemberId();
        return ResponseEntity.ok(bookmarkService.isBookMarked(memberId, movieId));
    }

    @PostMapping("/save")
    public ResponseEntity<Boolean> saveBookmark(@RequestParam Long movieId) {
        log.info("북마크 저장 진입");
        Long memberId = SecurityUtil.getCurrentMemberId();
        return ResponseEntity.ok(bookmarkService.saveBookMark(memberId, movieId));
    }

    @DeleteMapping("/remove/{movieId}")
    ResponseEntity<Boolean> removeBookmark(@PathVariable Long movieId) {
        log.info("북마크 해제 진입");
        Long memberId = SecurityUtil.getCurrentMemberId();
        return ResponseEntity.ok(bookmarkService.removeBookMark(memberId, movieId));
    }

    @GetMapping("/member/movielist")
    ResponseEntity<List<MovieDto>> memberMovieList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size
    ) {
        log.info("북마크된 영화 리스트 진입");
        Long id = SecurityUtil.getCurrentMemberId();
        return ResponseEntity.ok(bookmarkService.memberMovieList(id, page, size));
    }
}
