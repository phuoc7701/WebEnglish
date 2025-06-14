package vn.edu.engzone.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import vn.edu.engzone.entity.User;
import vn.edu.engzone.entity.Role;
import vn.edu.engzone.repository.RoleRepository;
import vn.edu.engzone.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;
    UserRepository userRepository;

    @Bean
    ApplicationRunner applicationRunner(RoleRepository roleRepository) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {

                Role adminRole = new Role();
                adminRole.setName(vn.edu.engzone.enums.Role.ADMIN.name());
                adminRole.setDescription("ssss");

                var roles = new HashSet<Role>();
                roles.add(adminRole);

                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .email("admin@engzone.edu.vn")
                        .roles(roles)
                        .accountStatus(true)
                        .build();

                userRepository.save(user);

                log.warn("admin user has been created with default password: admin, please change it");
            }
        };
    }
}
