package blogApp.application.springBoot.service;

import blogApp.application.springBoot.payload.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto addCategory(CategoryDto categoryDto);

    CategoryDto getCategoryById(long categoryId);
    List<CategoryDto> getAllCategories();
}
