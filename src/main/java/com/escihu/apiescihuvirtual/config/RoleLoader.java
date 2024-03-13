package com.escihu.apiescihuvirtual.config;

import com.escihu.apiescihuvirtual.persistence.Entity.Role;
import com.escihu.apiescihuvirtual.persistence.Repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
@Component
public class RoleLoader implements CommandLineRunner {
    private final RoleRepository repository;

    public RoleLoader(RoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args)  {
        List<Role> roles = Arrays.asList(
                new Role("ADMIN"),
                new Role("STUDENT"),
                new Role("TEACHER"),
                new Role("VIGILANT")

        );

        for (Role role : roles) {
            if (!repository.existsByAuthority(role.getAuthority())) {
                repository.save(role);
            }
        }
    }

}