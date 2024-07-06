package org.blog.blogging.payloads;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    private Integer id;

    @NotBlank(message = "Category title cannot be blank")
    @Size(min = 3, message = "Category title must be at least 3 characters long")
    private String categoryTitle;

    @NotBlank(message = "Category description cannot be blank")
    @Size(min = 5, message = "Category description must be at least 5 characters long")
    private String categoryDescription;

}
