package org.orphancare.dashboard.config;

import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.entity.Gender;
import org.orphancare.dashboard.entity.Profile;
import org.orphancare.dashboard.entity.RoleType;
import org.orphancare.dashboard.entity.User;
import org.orphancare.dashboard.repository.ProfileRepository;
import org.orphancare.dashboard.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializerConfig implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        String email = "admin@psaa.com";
        String password = "Admin123";
        String username = "adminpsaa";
        String profilePicture = "https://images.alphacoders.com/475/475526.jpg";
        boolean active = true;

        Set<RoleType> roles = new HashSet<>();
        roles.add(RoleType.ROLE_ADMIN);

        Optional<User> existingUserByEmail = userRepository.findByEmail(email);
        Optional<User> existingUserByUsername = userRepository.findByUsername(username);

        if (existingUserByEmail.isEmpty() && existingUserByUsername.isEmpty()) {
            User user = new User();
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            user.setUsername(username);
            user.setActive(active);
            user.setRoles(roles);

            Profile profile = new Profile();
            profile.setFullName("Admin PSAA Annajah");
            profile.setGender(Gender.MALE);
            profile.setUser(user);
            profile.setProfilePicture(profilePicture);

            user.setProfile(profile);

            try {
                userRepository.save(user);
                System.out.println("Admin user created successfully");
            } catch (Exception e) {
                System.out.println("Failed: " + e);
            }
        } else {
            System.out.println("Admin user already exists");
        }
    }
}
