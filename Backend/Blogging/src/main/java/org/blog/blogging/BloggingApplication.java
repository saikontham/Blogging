package org.blog.blogging;

import org.blog.blogging.config.AppConstants;
import org.blog.blogging.entities.Role;
import org.blog.blogging.exceptions.ResourceNotFoundException;
import org.blog.blogging.repositories.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

import static java.lang.String.*;

@SpringBootApplication
public class BloggingApplication implements CommandLineRunner {


    private final RoleRepository roleRepository;

    public BloggingApplication(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(BloggingApplication.class, args);

    }


    @Bean
    public ModelMapper modelMapper()
    {
        return new ModelMapper();
    }




    @Override
    public void run(String... args) throws Exception {
        try {
            Role role1 = new Role();
            role1.setId(AppConstants.NORMAL_USER);
            role1.setName("NORMAL_USER");

            Role role2 = new Role();
            role2.setId(AppConstants.ADMIN_USER);
            role2.setName("ADMIN_USER");

            List<Role> roleList = List.of(role1, role2);
            List<Role> savedRoles = roleRepository.saveAll(roleList);

            savedRoles.forEach(role -> {
                System.out.println("Role saved with name: " + role.getName());
            });
        } catch (Exception e) {
            throw new ResourceNotFoundException("Role Not Found");
        }
    }

}
