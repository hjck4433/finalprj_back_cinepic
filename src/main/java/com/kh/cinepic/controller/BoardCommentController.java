package com.kh.cinepic.controller;

import com.kh.cinepic.dto.BoardCommentReqDto;
import com.kh.cinepic.dto.BoardCommentResDto;
import com.kh.cinepic.security.SecurityUtil;
import com.kh.cinepic.service.BoardCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/comment")
@RequiredArgsConstructor
public class BoardCommentController {
    private final BoardCommentService boardCommentService;

    // 댓글 저장
    @PostMapping("/new")
    public ResponseEntity<Boolean> saveNewComment(@RequestBody BoardCommentReqDto boardCommentReqDto) {
        Long id = SecurityUtil.getCurrentMemberId();
        return ResponseEntity.ok(boardCommentService.saveBoardComment(boardCommentReqDto, id));
    }

    // 댓글 수정
    @PostMapping("/modify")
    public ResponseEntity<Boolean> commentModify(@RequestBody BoardCommentReqDto boardCommentReqDto) {
        boolean result = boardCommentService.commentModify(boardCommentReqDto);
        return ResponseEntity.ok(result);
    }

    //댓글 전체 리스트 조회
    @GetMapping("/{id}")
    public ResponseEntity<List<BoardCommentResDto>> boardCommentList(@PathVariable Long id) {
        List<BoardCommentResDto> list = boardCommentService.getBoardCommentList(id);
        return ResponseEntity.ok(list);
    }
}