package org.orphancare.dashboard.service;

import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.entity.User;
import org.orphancare.dashboard.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(identifier, identifier)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email: " + identifier));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }
}
