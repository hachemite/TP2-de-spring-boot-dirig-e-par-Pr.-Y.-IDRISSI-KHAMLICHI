package com.example.demo.config;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;


@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Create Roles if they don't exist
        Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElseGet(() -> roleRepository.save(new Role("ROLE_ADMIN")));
        Role pharmacienRole = roleRepository.findByName("ROLE_PHARMACIEN").orElseGet(() -> roleRepository.save(new Role("ROLE_PHARMACIEN")));
        Role caissierRole = roleRepository.findByName("ROLE_CAISSIER").orElseGet(() -> roleRepository.save(new Role("ROLE_CAISSIER")));
        Role clientRole = roleRepository.findByName("ROLE_CLIENT").orElseGet(() -> roleRepository.save(new Role("ROLE_CLIENT")));

        // Create Users from TP2 if they don't exist
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User("admin", passwordEncoder.encode("admin123"), true);
            admin.setRoles(Set.of(adminRole));
            userRepository.save(admin);
        }

        if (userRepository.findByUsername("pharma").isEmpty()) {
            User pharma = new User("pharma", passwordEncoder.encode("pharma123"), true);
            pharma.setRoles(Set.of(pharmacienRole));
            userRepository.save(pharma);
        }

        if (userRepository.findByUsername("caisse").isEmpty()) {
            User caisse = new User("caisse", passwordEncoder.encode("caisse123"), true);
            caisse.setRoles(Set.of(caissierRole));
            userRepository.save(caisse);
        }

        if (userRepository.findByUsername("client").isEmpty()) {
            User client = new User("client", passwordEncoder.encode("client123"), true);
            client.setRoles(Set.of(clientRole));
            userRepository.save(client);
        }
    }
}