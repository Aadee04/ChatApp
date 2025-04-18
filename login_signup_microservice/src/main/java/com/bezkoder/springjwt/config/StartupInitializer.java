package com.bezkoder.springjwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.bezkoder.springjwt.models.ERole;
import com.bezkoder.springjwt.models.Role;
import com.bezkoder.springjwt.repository.RoleRepository;

@Component
public class StartupInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        initializeRoles();
    }

    private void initializeRoles() {
        if (!roleRepository.existsByName(ERole.ROLE_USER)) {
            roleRepository.save(new Role(ERole.ROLE_USER));
        }
        if (!roleRepository.existsByName(ERole.ROLE_MODERATOR)) {
            roleRepository.save(new Role(ERole.ROLE_MODERATOR));
        }
        if (!roleRepository.existsByName(ERole.ROLE_ADMIN)) {
            roleRepository.save(new Role(ERole.ROLE_ADMIN));
        }
        System.out.println("Roles initialized successfully.");
    }
}
