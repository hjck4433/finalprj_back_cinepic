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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    // 새 게시글
    @PostMapping("/new")
    public ResponseEntity<Boolean> saveNewBoard(@RequestBody BoardReqDto boardReqDto) {
        log.info("새 게시글 저장");
        Long id = SecurityUtil.getCurrentMemberId();
        return ResponseEntity.ok(boardService.saveBoard(boardReqDto, id));
    }

    // 게시글 수정
    @PostMapping("/update")
    public ResponseEntity<Boolean> updateBoard(@RequestBody BoardReqDto boardReqDto) {
        log.info("boardReqDto : ", boardReqDto);
        return ResponseEntity.ok(boardService.modifyBoard(boardReqDto));
    }

    // 게시글 삭제
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteBoard(@PathVariable Long id) {
        log.info("삭제하는 게시글 id : {}", id);
        return ResponseEntity.ok(boardService.deleteBoard(id));
    }

    // 게시글 전체 조회
    @GetMapping("/list")
    public ResponseEntity<List<BoardResDto>> boardList() {
        List<BoardResDto> list = boardService.getBoardList();
        return ResponseEntity.ok(list);
    }

    // 게시글 상세 조회
    @GetMapping("/post/{postId}")
    public ResponseEntity<BoardResDto> boardDetail(@PathVariable Long postId) {
        log.info("게시글 조회 postId : {}", postId);
        BoardResDto boardResDto = boardService.getBoardDetail(postId);
        return ResponseEntity.ok(boardResDto);
    }

    @PutMapping("/post/counter")
    public ResponseEntity<Boolean> boardCounter(@RequestBody Long postId) {
        log.info("게시판 조회수 올리는 중 post Id : {}", postId);
        return ResponseEntity.ok(boardService.addCount(postId));
    }

    // 게시글 총 페이지 수
    @GetMapping("/totalpages")
    public ResponseEntity<Integer> getBoardTotalPages (
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) String gatherType
    ) {
        log.info("totalpages 진입");
        int totalPages = boardService.getBoardListPage(page, size, keyword, categoryName, gatherType);
        log.info("보드 총 페이지 : {}", totalPages);
        return ResponseEntity.ok(totalPages);
    }

    // 게시글 리스트
    @GetMapping("/processedlist")
    public ResponseEntity<List<BoardResDto>> getProcessedBoardList (
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size,
            @RequestParam(defaultValue = "recent") String sort,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = true) String categoryName,
            @RequestParam(required = true) String gatherType
    ) {
        List<BoardResDto> boardList = boardService.getProcessedBoardList(page, size, sort, keyword, categoryName, gatherType);
        return ResponseEntity.ok(boardList);
    }

    // 회원이 작성한 보드 / 댓글 포함 보드 페이지 수
    @GetMapping("/memboard/page")
    public ResponseEntity<Integer> getMemBoardPages (
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size,
            @RequestParam(defaultValue = "written") String type
    ) {
        Long id = SecurityUtil.getCurrentMemberId();
        PageRequest pageRequest = PageRequest.of(page, size);
        int totalPages = boardService.searchMemBoardPage(id, type, pageRequest);
        return ResponseEntity.ok(totalPages);
    }
    // 회원이 작성한 보드 / 댓글 포함 게시글 리스트
    public ResponseEntity<List<BoardResDto>> getMemBoardList (
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size,
            @RequestParam(defaultValue = "written") String type
    ) {
        Long id = SecurityUtil.getCurrentMemberId();
        List<BoardResDto> memBoardList = boardService.searchMemBoardList(id, type, page, size);
        return ResponseEntity.ok(memBoardList);
    }

    // Admin
    // 게시글 리스트 조회(페이지네이션)
    @GetMapping("/admin/boardlist")
    public ResponseEntity<List<BoardResDto>> adminBoardList(@RequestParam (defaultValue = "0")int page,
                                                            @RequestParam(defaultValue = "10")int size){
        log.info("어드민 게시글 리스트 진입");
        return ResponseEntity.ok(boardService.getAdminBoardList(page, size));
    }


    // 총 페이지 조회
    @GetMapping("/admin/totalpage")
    public ResponseEntity<Integer> adminBoardPages(@RequestParam(defaultValue = "0")int page,
                                                   @RequestParam(defaultValue = "10")int size){
        PageRequest pageRequest = PageRequest.of(page, size);
        log.info("total page : {}", boardService.getAdminBoardPage(pageRequest));
        return ResponseEntity.ok(boardService.getAdminBoardPage(pageRequest));
    }

    // 모든 카테고리 게시글 수 조회
    @GetMapping("/admin/boardtype")
    public ResponseEntity<Map<String, Long>> getCountCategoryAndType() {
        Map<String, Long> result = boardService.getCountCategoryAndType();
        return ResponseEntity.ok(result);
    }
}
