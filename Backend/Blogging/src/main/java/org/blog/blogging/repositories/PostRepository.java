package org.blog.blogging.repositories;

import org.blog.blogging.entities.Category;
import org.blog.blogging.entities.Customer;
import org.blog.blogging.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {

    Page<Post> findByCustomer(Customer customer, Pageable pageable);
    Page<Post> findByCategory(Category category, Pageable pageable);

    @Query("select p from Post p where p.title like :key")
    List<Post> searchByTitle(@Param("key") String title);
}
