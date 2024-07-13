package org.blog.blogging.repositories;

import org.blog.blogging.entities.Customer;
import org.blog.blogging.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    boolean existsByEmail(String email);

    Optional<Customer> findByEmail(String username);
}
