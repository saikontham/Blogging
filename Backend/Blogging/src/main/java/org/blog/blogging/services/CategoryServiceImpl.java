package org.blog.blogging.services;

import lombok.RequiredArgsConstructor;
import org.blog.blogging.entities.Category;
import org.blog.blogging.exceptions.ResourceNotFoundException;
import org.blog.blogging.payloads.CategoryDTO;
import org.blog.blogging.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.*;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category=modelMapper.map(categoryDTO,Category.class);
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory,CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId) {
        Category category=categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException(format("Category with Id %s Not Found",categoryId)));
        category.setCategoryDescription(categoryDTO.getCategoryDescription());
        category.setCategoryTitle(categoryDTO.getCategoryTitle());
        Category updatedCategory = categoryRepository.save(category);
        return modelMapper.map(updatedCategory,CategoryDTO.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category=categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException(format("Category with Id %s Not Found",categoryId)));
        categoryRepository.delete(category);
    }

    @Override
    public CategoryDTO getCategory(Integer categoryId) {
        Category category=categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException(format("Category with Id %s Not Found",categoryId)));
        return modelMapper.map(category,CategoryDTO.class);
    }

    @Override
    public List<CategoryDTO> getListOfCategory() {
        List<Category> categoryList=categoryRepository.findAll();
        List<CategoryDTO> categoryDTOList = categoryList.stream().map(category -> modelMapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());
        return categoryDTOList;
    }
    @Override
    public List<CategoryDTO> searchPost(String search) {

        List<Category> titleContaining = categoryRepository.searchByCategoryTitle(search);
        List<CategoryDTO> categoryDTOList = titleContaining.stream().map(category -> modelMapper.map(category, CategoryDTO.class)).collect(Collectors.toList());
        return categoryDTOList;
    }
}
