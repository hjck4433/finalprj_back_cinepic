package com.kh.cinepic.service;


import com.kh.cinepic.dto.FaqDto;
import com.kh.cinepic.entity.Faq;
import com.kh.cinepic.repository.FaqRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FaqService {
    private final FaqRepository faqRepository;


    // faq 추가
    public boolean createFaq(FaqDto faqDto) {

        // faq 객체 생성
        Faq faq = new Faq();
        faq.setFaqQuestion(faqDto.getFaqQuestion());
        faq.setFaqAnswer(faqDto.getFaqAnswer());

        // faq 저장
        Faq saved = faqRepository.save(faq);

        // 저장된 faq를 Dto로 저장
        log.info("savedFaq : {}", faqDto);
        return true;
    }
}
