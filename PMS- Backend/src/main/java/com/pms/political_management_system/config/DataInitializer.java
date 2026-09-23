package com.pms.political_management_system.config;

import com.pms.political_management_system.entity.Role;
import com.pms.political_management_system.entity.User;
import com.pms.political_management_system.repository.RoleRepository;
import com.pms.political_management_system.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner init(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {

        return args -> {

            Role adminRole = roleRepository.findByRoleName("ADMIN")
                    .orElseGet(() -> roleRepository.save(new Role(null, "ADMIN")));

            roleRepository.findByRoleName("MANAGER")
                    .orElseGet(() -> roleRepository.save(new Role(null, "MANAGER")));

            roleRepository.findByRoleName("MEMBER")
                    .orElseGet(() -> roleRepository.save(new Role(null, "MEMBER")));

            // Seed the administrator independently of other existing users.
            // This account is also a break-glass safety net: if it already
            // exists but its role got changed to something else (e.g. via
            // the Users screen), a single restart restores it to ADMIN so
            // nobody can accidentally lock everyone out of the app - there's
            // always at least one guaranteed-working ADMIN login.
            User admin = userRepository.findByEmail("admin@gmail.com").orElse(null);

            if (admin == null) {

                admin = new User();
                admin.setName("Admin");
                admin.setEmail("admin@gmail.com");
                admin.setPassword(passwordEncoder.encode("123456"));
                admin.setStatus(true);
                admin.setRole(adminRole);

                userRepository.save(admin);

                System.out.println("✅ Admin User Created Successfully");

            } else if (admin.getRole() == null
                    || !"ADMIN".equalsIgnoreCase(admin.getRole().getRoleName())) {

                admin.setRole(adminRole);
                userRepository.save(admin);

                System.out.println("⚠️ admin@gmail.com's role was not ADMIN - restored automatically.");
            }
        };
    }
}
