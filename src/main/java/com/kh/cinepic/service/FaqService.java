package com.kh.cinepic.service;

import com.kh.cinepic.dto.FaqDto;
import com.kh.cinepic.entity.Faq;
import com.kh.cinepic.repository.FaqRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


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


    // faq 수정
    public boolean reviseFaq(FaqDto faqDto) {
        try {
            Faq faq = faqRepository.findById(faqDto.getFaqId()).orElseThrow(
                    () -> new RuntimeException("수정할 게시글이 업습니다")
            );

            // 수정할 내용을 설정
            faq.setFaqAnswer(faqDto.getFaqAnswer());
            faq.setFaqQuestion(faqDto.getFaqQuestion());


        // 수정된 faq 저장
        Faq saved = faqRepository.save(faq);
            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    // faq 삭제

    public boolean deleteFaq(Long faqId) {
        try {
            Faq faq = faqRepository.findById(faqId)
                    .orElseThrow(() -> new RuntimeException("해당 게시글이 존재하지 않습니다"));

            faqRepository.delete(faq);

            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    // DB에서 FAQ 정보 가져오기
    public List<FaqDto> getFaqList(){
        List<Faq> faqs = faqRepository.findAll();
        List<FaqDto> faqDtoList = new ArrayList<>();
        for (Faq faq: faqs) {
            FaqDto faqDto = convertEntityToDto(faq);
            faqDtoList.add(faqDto);
        }
        return faqDtoList;
    }





    // 엔티티를 Dto로 변환
    public FaqDto convertEntityToDto(Faq faq){
        FaqDto faqDto = new FaqDto();
        faqDto.setFaqId(faq.getFaqId());
        faqDto.setFaqQuestion(faq.getFaqQuestion());
        faqDto.setFaqAnswer(faq.getFaqAnswer());
        return faqDto;
    }
}
