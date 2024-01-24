package com.kh.cinepic.controller;

import com.kh.cinepic.dto.CategoryDto;
import com.kh.cinepic.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    //CategoryService 주입을 위한 final 필드
    private final CategoryService categoryService;

    @PostMapping("/new")
    public ResponseEntity<CategoryDto> createCategory(@RequestParam String categoryName){
        CategoryDto categoryDto = categoryService.createCategory(categoryName); // 새 카테고리 생성
        log.info("카테고리 저장하기: {}", categoryDto);
        return ResponseEntity.ok(categoryDto); // 저장된 카테고리 DTO를 반환
    }
}
