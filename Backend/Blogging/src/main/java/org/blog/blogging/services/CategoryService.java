package org.blog.blogging.services;

import org.blog.blogging.payloads.CategoryDTO;

import java.util.List;

public interface CategoryService {

     CategoryDTO createCategory(CategoryDTO categoryDTO);
     CategoryDTO updateCategory(CategoryDTO categoryDTO,Integer categoryId);

     void deleteCategory(Integer categoryId );
     CategoryDTO getCategory(Integer categoryId);
     List<CategoryDTO> getListOfCategory();

}
