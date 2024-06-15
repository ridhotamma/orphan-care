package org.orphancare.dashboard.service;

import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.PaginatedResponse;
import org.orphancare.dashboard.dto.UserRequestDto;
import org.orphancare.dashboard.dto.UserResponseDto;
import org.orphancare.dashboard.entity.Role;
import org.orphancare.dashboard.entity.User;
import org.orphancare.dashboard.exception.ResourceNotFoundException;
import org.orphancare.dashboard.exception.UserAlreadyExistsException;
import org.orphancare.dashboard.repository.RoleRepository;
import org.orphancare.dashboard.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public PaginatedResponse<UserResponseDto> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = userRepository.findAll(pageable);

        List<UserResponseDto> userDtos = userPage.getContent().stream().map(user -> new UserResponseDto(
                user.getEmail(),
                user.getUsername()
        )).collect(Collectors.toList());

        return new PaginatedResponse<>(
                userDtos,
                userPage.getNumber(),
                userPage.getSize(),
                userPage.getTotalElements(),
                userPage.getTotalPages()
        );
    }

    public UserResponseDto getUserById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found for this id: " + id));
        return new UserResponseDto(user.getEmail(), user.getUsername());
    }

    public UserResponseDto createUser(UserRequestDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists: " + userDto.getEmail());
        }
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new UserAlreadyExistsException("Username already exists: " + userDto.getUsername());
        }

        Set<Role> roles = new HashSet<>();

        for (String roleName : userDto.getRoles()) {
            Optional<Role> role = roleRepository.findByRoleName(roleName);
            role.ifPresent(roles::add);
        }

        User user = new User();
        user.setRoles(roles);
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setUsername(userDto.getUsername());
        User savedUser = userRepository.save(user);
        return new UserResponseDto(savedUser.getEmail(), savedUser.getUsername());
    }

    public UserResponseDto updateUser(UUID id, UserRequestDto userDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found for this id: " + id));
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        // Only update password if it's provided and not blank
        if (userDto.getPassword() != null && !userDto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        User updatedUser = userRepository.save(user);
        return new UserResponseDto(updatedUser.getEmail(), updatedUser.getUsername());
    }

    public void deleteUser(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found for this id: " + id));
        userRepository.delete(user);
    }
}
