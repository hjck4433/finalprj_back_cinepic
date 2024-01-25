package com.kh.cinepic.service;

import com.kh.cinepic.dto.BoardReqDto;
import com.kh.cinepic.dto.BoardResDto;
import com.kh.cinepic.entity.Board;
import com.kh.cinepic.entity.Category;
import com.kh.cinepic.entity.Member;
import com.kh.cinepic.repository.BoardRepository;
import com.kh.cinepic.repository.CategoryRepository;
import com.kh.cinepic.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {
    private final BoardRepository boardRepository;
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;

    // 게시물 등록
    public boolean saveBoard(BoardReqDto boardReqDto, Long id) {
        try {
            Board board = new Board();
            Member member = memberRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("존재하지 않는 회원!")
            );
            Category category = categoryRepository.findByCategoryName(boardReqDto.getCategoryName()).orElseThrow(
                    () -> new RuntimeException("존재하지 않는 카테고리!")
            );
            board.setMember(member);
            board.setCategory(category);
            board.setGatherType(boardReqDto.getGatherType());
            board.setTitle(boardReqDto.getTitle());
            board.setImage(boardReqDto.getImage());
            board.setBoardContent(boardReqDto.getBoardContent());

            boardRepository.save(board);
            return true;
        } catch (Exception e) {
            log.error("저장 오류 발생 : {}", (Object) e.getStackTrace());
            return false;
        }
    }

    // 게시물 전체 조회
    public List<BoardResDto> getBoardList() {
        List<Board> boards = boardRepository.findAll();
        List<BoardResDto> boardResDtos = new ArrayList<>();
        for (Board board : boards) {
            boardResDtos.add(convertEntityToDto(board));
        }
        return boardResDtos;
    }

    // 게시물 상세 조회
    public BoardResDto getBoardDetail(Long postId) {
        BoardResDto boardResDto = new BoardResDto();
        Board board = boardRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 게시글!"));
        boardResDto = convertEntityToDto(board);
        log.info("{}게시글 정보 : ", boardResDto.getTitle());
        return boardResDto;
    }
    // 게시글 수정
    public boolean modifyBoard(BoardReqDto boardReqDto) {
        try {
            Board board = boardRepository.findById(boardReqDto.getId()).orElseThrow(
                    () -> new RuntimeException("존재하지 않는 게시글!"));
            Category category = categoryRepository.findByCategoryName(boardReqDto.getCategoryName()).orElseThrow(
                    () -> new RuntimeException("존재하지 않는 카테고리!"));

            log.info("category : {}", category.getCategoryName());

            // 값이 null이 아니면 업데이트, null이면 기존 값 유지
            if (boardReqDto.getCategoryName() != null) {
                board.setCategory(category);
            }
            if (boardReqDto.getGatherType() != null) {
                board.setGatherType(boardReqDto.getGatherType());
            }
            if (boardReqDto.getTitle() != null) {
                board.setTitle(boardReqDto.getTitle());
            }
            if (boardReqDto.getImage() != null) {
                board.setImage(boardReqDto.getImage());
            }
            if (boardReqDto.getBoardContent() != null) {
                board.setBoardContent(boardReqDto.getBoardContent());
            }

            boardRepository.save(board);
            return true;
        } catch (Exception e) {
            log.error("게시글 수정 오류 : {}", (Object) e.getStackTrace());
            return false;
        }
    }
    // 게시물 삭제
    public boolean deleteBoard(Long id) {
        try {
            Board board = boardRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("존재하지 않는 게시글!")
            );
            boardRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("게시글 삭제 오류 : {}", (Object) e.getStackTrace());
            return false;
        }
    }
    // 게시글 엔티티를 DTO로 변환
    private BoardResDto convertEntityToDto (Board board) {
        BoardResDto boardResDto = new BoardResDto();
        boardResDto.setId(board.getId());
        boardResDto.setMemberAlias(board.getMember().getAlias());
        boardResDto.setMemberImage(board.getMember().getImage());
        boardResDto.setCategoryName(board.getCategory().getCategoryName());
        boardResDto.setTitle(board.getTitle());
        boardResDto.setBoardContent(board.getBoardContent());
        boardResDto.setImage(board.getImage());
        boardResDto.setGatherType(board.getGatherType());
        boardResDto.setRegDate(board.getRegDate());
//        boardResDto.setCount(board.getCount());
        return boardResDto;
    }
}
