package org.blog.blogging.config;


import lombok.extern.slf4j.Slf4j;
import org.blog.blogging.config.authenticationClasses.LoginRequest;
import org.blog.blogging.config.authenticationClasses.LoginResponse;
import org.blog.blogging.config.jwt.JWTUtils;
import org.blog.blogging.payloads.CustomerDTO;
import org.blog.blogging.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController

@Slf4j
public class SecurityController {

    @Autowired
    private CustomerService customerService;
    @Autowired
     private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<String> registerCustomer(@RequestBody CustomerDTO customerDTO) {
        try {

            if (customerDTO.getEmail()!=null) {
                customerService.createCustomer(customerDTO);
                return ResponseEntity.ok("Customer Registration Successful");
            } else {
                return ResponseEntity.badRequest().body("Email is already registered");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error during customer registration: " + e.getMessage());
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest)
    {
        Authentication authentication;
        try {
            authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken( loginRequest.getUsername(),loginRequest.getPassword()));
            log.debug("authenticate user executed {}",authentication);
        }catch (AuthenticationException ae)
        {
            Map<String ,Object> map=new HashMap<>();
            map.put("message","Bad Credentials");
            map.put("status",false);
            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails= (UserDetails) authentication.getPrincipal();
        String JwtToken=jwtUtils.generateTokenFromUsername(userDetails);
        List<String > roles=userDetails.getAuthorities().stream()
                .map(item->item.getAuthority()).collect(Collectors.toList());
        LoginResponse response=new LoginResponse(userDetails.getUsername(), loginRequest.getPassword(), JwtToken);
        return ResponseEntity.ok(response);
    }
}
