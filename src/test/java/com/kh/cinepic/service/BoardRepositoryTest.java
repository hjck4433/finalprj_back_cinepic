package com.kh.cinepic.service;

import com.kh.cinepic.entity.Board;
import com.kh.cinepic.entity.Category;
import com.kh.cinepic.entity.Member;
import com.kh.cinepic.repository.BoardRepository;
import com.kh.cinepic.repository.CategoryRepository;
import com.kh.cinepic.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@SpringBootTest
@Transactional
@Slf4j
@RequiredArgsConstructor
@TestPropertySource(locations = "classpath:application-test.properties")
public class BoardRepositoryTest {
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    MemberRepository memberRepository;

    @PersistenceContext
    EntityManager em;

    public Member createMemberInfo() {
        Member member = new Member();
        member.setEmail("cinecine@gmail.com");
        member.setPassword("Cinepic1234!");
        member.setName("테스트유저01");
        member.setAlias("서울살아요");
        member.setPhone("010-1234-5678");
        member.setAddr("서울시 강남구 역삼동");
        member.setRegDate(LocalDateTime.now());
        return member;
    }

    public Category createCategoryInfo() {
        Category category = new Category();
        category.setCategoryName("크루후기");
        return category;
    }

    @Test
    @DisplayName("게시글 카테고리 및 회원 정보 매핑 테스트")
    public void findCategoryAndMemberSaveTest() {
        Member member = createMemberInfo();
        memberRepository.save(member);
        Category category = createCategoryInfo();
        categoryRepository.save(category);

        Board board = new Board();
        board.setMember(member);
        board.setCategory(category);
        board.setGatherType("오프라인");
        board.setTitle("2월 8일 모임을 가진 후기");
        board.setBoardContent("다음 모임도 기대됩니다.");
        board.setImage("pikachu.jpg");
        boardRepository.save(board);

        em.flush();
        em.clear();


        // 게시글 리스트 조회 및 출력
        List<Board> boards = boardRepository.findAll();
        System.out.println("전체 게시글 수: " + boards.size());

        System.out.println("게시글 리스트:");
        for (Board savedBoard : boards) {
            System.out.println("boardID: " + savedBoard.getId() + ", title: " + savedBoard.getTitle());
        }
        Board savedBoard = boardRepository.findById(board.getId()).orElseThrow(EntityNotFoundException::new);

        System.out.println("savedBoard test 결과 : " + savedBoard);
    }

    @Test
    @DisplayName("특정 회원이 작성한 게시글 조회")
    public void findBoardsByMemberTest() {
        Member member = createMemberInfo();
        memberRepository.save(member);

        Category category = createCategoryInfo();
        categoryRepository.save(category);

        Board board1 = new Board();
        board1.setMember(member);
        board1.setCategory(category);
        board1.setGatherType("온라인");
        board1.setTitle("온라인 모임을 가졌어요!");
        board1.setBoardContent("온라인 모임 좋으니까 앞으로 온라인에서만 만나요!!");
        board1.setImage("pikachu.jpg");
        boardRepository.save(board1);

        Board board2 = new Board();
        board2.setMember(member);
        board2.setCategory(category);
        board2.setGatherType("오프라인");
        board2.setTitle("오프라인 모임을 가졌어요!");
        board2.setBoardContent("새로운 만남!!");
        board2.setImage("fairy.jpg");
        boardRepository.save(board2);

        em.flush();
        em.clear();

        Optional<Member> savedMember = memberRepository.findById(member.getId());
        if (savedMember.isPresent()) {
            Member foundMember = savedMember.get();
            System.out.println("작성 게시글 리스트: " + foundMember.getBoards());
        } else {
            throw new EntityNotFoundException("이 유저는 누구 인가요? 정보가 없네요.");
        }

    }
}