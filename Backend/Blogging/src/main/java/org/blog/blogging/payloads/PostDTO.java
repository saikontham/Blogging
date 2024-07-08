package org.blog.blogging.payloads;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.blog.blogging.entities.Category;
import org.blog.blogging.entities.Customer;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private Integer id;
    @NotBlank(message = "Title cannot be blank")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;

    @NotBlank(message = "Content cannot be blank")
    private String content;

    private Date addedDate;
    private String image;

    private CustomerDTO customer;

    private CategoryDTO category;
}
