package org.blog.blogging.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CustomerDTO {

    private Integer id;

    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 4, message = "name must be more than 4 characters")
    private String name;

    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email cannot be empty")
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,20}$",
            message = "Password must contain at least one uppercase, one lowercase, one digit, one special character, and be between 6 to 20 characters")
    private String password;

    @NotEmpty(message = "About cannot be empty")
    private String about;

    private Set<RoleDTO> roles=new HashSet<>();
}
