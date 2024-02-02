package com.kh.cinepic.service;

import com.kh.cinepic.entity.Faq;
import com.kh.cinepic.repository.FaqRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Slf4j
@SpringBootTest
@Transactional
@RequiredArgsConstructor
@TestPropertySource(locations = "classpath:application-test.properties")
public class FaqRepositoryTest {

    @Autowired
    FaqRepository faqRepository;

    @PersistenceContext
    EntityManager em;


    @Test
    @DisplayName("FAQ 추가 테스트")
    public void addFaqTest() {
        Faq faq = new Faq();
        faq.setFaqQuestion("테스트 작동?");
        faq.setFaqAnswer("잘 작동됨");

        // 추가된 faq를 저장하는 코드
        Faq savedFaq = faqRepository.save(faq);

        em.flush();
        em.clear();
        System.out.println("Added FAQ: " + savedFaq);
    }

        @Test
        @DisplayName("FAQ 수정 테스트")
        public void reviseFaqTest () {
            Faq faq = new Faq();

            faq.setFaqQuestion("테스트 작동?");
            faq.setFaqAnswer("잘 작동됨");

            Faq savedFaq = faqRepository.save(faq);

            // 수정할 내용
            savedFaq.setFaqQuestion("수정된 질문");
            savedFaq.setFaqAnswer("수정된 답변");

            // 수정된 내용 업데이트
            Faq reviseFaq = faqRepository.save(savedFaq);

            em.flush();
            em.clear();
            System.out.println("revise FAQ:" + reviseFaq);

        }

        @Test
        @DisplayName("FAQ 삭제 테스트")
        public void deleteFaqTest() {
        Faq faq = new Faq();
        faq.setFaqQuestion("테스트 작동?");
        faq.setFaqAnswer("잘 작동됨");

        Faq savedFaq = faqRepository.save(faq);

        faqRepository.deleteById(savedFaq.getFaqId());

        em.flush();
        em.clear();
        System.out.println("delete FAQ: " + savedFaq.getFaqId());


        }
    }


