package org.blog.blogging.repositories;

import org.blog.blogging.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {
    @Query("select p from Category p where p.categoryTitle like :key")
    List<Category> searchByCategoryTitle(@Param("key") String title);
}
