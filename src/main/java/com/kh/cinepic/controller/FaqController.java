package com.kh.cinepic.controller;


import com.kh.cinepic.service.FaqService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequestMapping("/faq")
@RequiredArgsConstructor
public class FaqController {
    private final FaqService faqService;

}
