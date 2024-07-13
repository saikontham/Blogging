package org.blog.blogging.config.authenticationClasses;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class LoginRequest {
    private String username;
    private String password;


}
