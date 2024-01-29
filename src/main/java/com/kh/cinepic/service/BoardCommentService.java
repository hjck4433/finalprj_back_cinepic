package com.kh.cinepic.service;

import com.kh.cinepic.dto.BoardCommentReqDto;
import com.kh.cinepic.dto.BoardCommentResDto;
import com.kh.cinepic.entity.*;
import com.kh.cinepic.repository.BoardCommentRepository;
import com.kh.cinepic.repository.BoardRepository;
import com.kh.cinepic.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardCommentService {
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private BoardCommentRepository boardCommentRepository;


    // 댓글 등록
    public boolean saveBoardComment (BoardCommentReqDto boardCommentReqDto, Long id) {
        try {
            BoardComment boardComment = new BoardComment();
            Member member = memberRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("존재하지 않는 회원입니다.")
            );
            Board board = boardRepository.findById(boardCommentReqDto.getBoardId()).orElseThrow(
                    () -> new RuntimeException("존재하지 않는 게시글입니다.")
            );
            boardComment.setMember(member);
            boardComment.setBoard(board);
            boardComment.setCommentText(boardCommentReqDto.getCommentText());
            boardComment.setCommentRegDate(LocalDateTime.now());
            boardCommentRepository.save(boardComment);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 댓글 수정
    public boolean commentModify (BoardCommentReqDto commentReqDto) {
        try {
            BoardComment boardComment = boardCommentRepository.findById(commentReqDto.getId()).orElseThrow(
                    () -> new RuntimeException("존재하지 않는 댓글입니다.")
            );
            String text = "";
            if (commentReqDto.getCommentText().isEmpty()){
                text = boardComment.getCommentText();
            }else {
                text = commentReqDto.getCommentText();
            }
            boardComment.setCommentText(text);
            boardCommentRepository.save(boardComment);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 댓글 전체 목록 조회
    public List<BoardCommentResDto> getBoardCommentList (Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("존재하지 않는 게시글입니다."));
        List<BoardComment> boardComments = boardCommentRepository.findByBoard(board);
        List<BoardCommentResDto> boardCommentResDtos = new ArrayList<>();
        for (BoardComment boardComment : boardComments) {
            boardCommentResDtos.add(convertEntityToDto(boardComment));
        }
        return boardCommentResDtos;
    }

    // 댓글 엔티티를 Dto로 변환
    public BoardCommentResDto convertEntityToDto (BoardComment boardComment) {
        BoardCommentResDto boardCommentResDto = new BoardCommentResDto();
        boardCommentResDto.setBoardId(boardComment.getBoard().getId());
        boardCommentResDto.setMemberAlias(boardComment.getMember().getAlias());
        boardCommentResDto.setMemberImage(boardComment.getMember().getImage());
        boardCommentResDto.setCommentText(boardComment.getCommentText());
        boardCommentResDto.setCommentId(boardComment.getId());
        boardCommentResDto.setCommentRegDate(boardComment.getCommentRegDate());
        return boardCommentResDto;
    }


}
