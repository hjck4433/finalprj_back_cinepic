package com.kh.cinepic.controller;

import com.kh.cinepic.dto.BoardReqDto;
import com.kh.cinepic.dto.BoardResDto;
import com.kh.cinepic.security.SecurityUtil;
import com.kh.cinepic.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    // 새 게시글
    @PostMapping("/post/new")
    public ResponseEntity<Boolean> saveNewBoard(@RequestBody BoardReqDto boardReqDto) {
        log.info("새 게시글 저장");
        Long id = SecurityUtil.getCurrentMemberId();
        return ResponseEntity.ok(boardService.saveBoard(boardReqDto, id));
    }

    // 게시글 수정
    @PostMapping("/post/update")
    public ResponseEntity<Boolean> updateBoard(@RequestBody BoardReqDto boardReqDto) {
        log.info("boardReqDto : ", boardReqDto);
        return ResponseEntity.ok(boardService.modifyBoard(boardReqDto));
    }

    // 게시글 삭제
    @DeleteMapping("/post/delete/{id}")
    public ResponseEntity<Boolean> deleteBoard(@PathVariable Long id) {
        log.info("삭제하는 게시글 id : {}", id);
        return ResponseEntity.ok(boardService.deleteBoard(id));
    }

    // 게시글 상세 조회
    @GetMapping("/post/{postId}")
    public ResponseEntity<BoardResDto> boardDetail(@PathVariable Long postId) {
        log.info("게시글 조회 postId : {}", postId);
        BoardResDto boardResDto = boardService.getBoardDetail(postId);
        return ResponseEntity.ok(boardResDto);
    }


    // Admin
    // 게시글 리스트 조회(페이지네이션)
    @GetMapping("/admin/boardlist")
    public ResponseEntity<List<BoardResDto>> adminBoardList(@RequestParam (defaultValue = "0")int page,
                                                            @RequestParam(defaultValue = "10")int size){
        return ResponseEntity.ok(boardService.getAdminBoardList(page, size));
    }


    // 총 페이지 조회
    @GetMapping("/admin/totalpage")
    public ResponseEntity<Integer> adminBoardPages(@RequestParam(defaultValue = "0")int page,
                                                   @RequestParam(defaultValue = "10")int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        return ResponseEntity.ok(boardService.getAdminBoardPage(pageRequest));
    }



}
