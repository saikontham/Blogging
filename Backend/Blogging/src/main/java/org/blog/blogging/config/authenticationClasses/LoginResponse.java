package org.blog.blogging.config.authenticationClasses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginResponse {
//    private String username;
    private String emailId;
    private String password;


    private String jwttoken;


}
