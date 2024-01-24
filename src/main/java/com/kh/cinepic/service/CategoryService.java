package com.kh.cinepic.service;

import com.kh.cinepic.dto.CategoryDto;
import com.kh.cinepic.entity.Category;
import com.kh.cinepic.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {
    // CategoryRepository 주입을 위한 final 필드
    private final CategoryRepository categoryRepository;


    // 카테고리 만들기
    public CategoryDto createCategory(String categoryNm){
        // 카테고리 객체 생성
        Category category = new Category();
        category.setCategoryName(categoryNm);
        // 카테고리 저장
        Category saved = categoryRepository.save(category);
        // 저장된 카테고리를 DTO로 변환
        CategoryDto categoryDto = convertEntityToDto(saved);
        log.info("savedCategory : {}", categoryDto);
        return categoryDto;
    }

    // 엔티티를 DTO로 변환
    public CategoryDto convertEntityToDto (Category category){
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryId(category.getId());
        categoryDto.setCategoryName(category.getCategoryName());
        return categoryDto;
    }
}
