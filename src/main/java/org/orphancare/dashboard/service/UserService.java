package org.orphancare.dashboard.service;

import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.CreateUserDto;
import org.orphancare.dashboard.dto.UpdateUserDto;
import org.orphancare.dashboard.dto.ChangePasswordUserDto;
import org.orphancare.dashboard.dto.UserDto;
import org.orphancare.dashboard.entity.RoleType;
import org.orphancare.dashboard.entity.User;
import org.orphancare.dashboard.exception.ResourceNotFoundException;
import org.orphancare.dashboard.exception.UserAlreadyExistsException;
import org.orphancare.dashboard.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDto createUser(CreateUserDto createUserDto) {
        if (userRepository.existsByEmail(createUserDto.getEmail())) {
            throw new UserAlreadyExistsException("Email is already in use");
        }
        if (userRepository.existsByUsername(createUserDto.getUsername())) {
            throw new UserAlreadyExistsException("Username is already in use");
        }
        User user = new User();
        user.setEmail(createUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
        user.setUsername(createUserDto.getUsername());
        user.setRoles(createUserDto.getRoles().stream().map(RoleType::valueOf).collect(Collectors.toSet()));
        user.setActive(createUserDto.isActive());
        User savedUser = userRepository.save(user);
        return convertToDto(savedUser);
    }

    public UserDto updateUser(UUID userId, UpdateUserDto updateUserDto) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

        if (updateUserDto.getEmail() != null && !existingUser.getEmail().equals(updateUserDto.getEmail())
                && userRepository.existsByEmail(updateUserDto.getEmail())) {
            throw new UserAlreadyExistsException("Email is already in use");
        }

        if (updateUserDto.getUsername() != null && !existingUser.getUsername().equals(updateUserDto.getUsername())
                && userRepository.existsByUsername(updateUserDto.getUsername())) {
            throw new UserAlreadyExistsException("Username is already in use");
        }

        existingUser.setEmail(updateUserDto.getEmail() != null ? updateUserDto.getEmail() : existingUser.getEmail());
        existingUser.setUsername(updateUserDto.getUsername() != null ? updateUserDto.getUsername() : existingUser.getUsername());
        existingUser.setRoles(updateUserDto.getRoles() != null ? updateUserDto.getRoles().stream().map(RoleType::valueOf).collect(Collectors.toSet()) : existingUser.getRoles());
        existingUser.setActive(updateUserDto.isActive());

        User updatedUser = userRepository.save(existingUser);
        return convertToDto(updatedUser);
    }

    public UserDto changeUserPassword(UUID userId, ChangePasswordUserDto changePasswordUserDto) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

        existingUser.setPassword(passwordEncoder.encode(changePasswordUserDto.getNewPassword()));
        User updatedUser = userRepository.save(existingUser);
        return convertToDto(updatedUser);
    }

    public void deleteUser(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id " + userId);
        }
        userRepository.deleteById(userId);
    }

    public UserDto getUserById(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
        return convertToDto(user);
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email " + email));
        return convertToDto(user);
    }

    public UserDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username " + username));
        return convertToDto(user);
    }

    private UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setUsername(user.getUsername());
        userDto.setRoles(user.getRoles().stream().map(Enum::name).collect(Collectors.toSet()));
        userDto.setActive(user.isActive());
        return userDto;
    }
}
