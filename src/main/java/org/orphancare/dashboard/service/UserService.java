package org.orphancare.dashboard.service;

import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.PaginatedResponse;
import org.orphancare.dashboard.dto.UserRequestDto;
import org.orphancare.dashboard.dto.UserResponseDto;
import org.orphancare.dashboard.dto.ProfileResponseDto;
import org.orphancare.dashboard.entity.RoleType;
import org.orphancare.dashboard.entity.User;
import org.orphancare.dashboard.entity.Profile;
import org.orphancare.dashboard.exception.ResourceNotFoundException;
import org.orphancare.dashboard.exception.UserAlreadyExistsException;
import org.orphancare.dashboard.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public PaginatedResponse<UserResponseDto> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = userRepository.findAll(pageable);

        List<UserResponseDto> userDtos = userPage.getContent()
                .stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());

        return new PaginatedResponse<>(
                userDtos,
                userPage.getNumber(),
                userPage.getSize(),
                userPage.getTotalElements(),
                userPage.getTotalPages()
        );
    }

    public UserResponseDto getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id: " + id));
        return toResponseDto(user);
    }

    public UserResponseDto createUser(UserRequestDto userDto) {
        checkIfUserExists(userDto);

        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setUsername(userDto.getUsername());
        user.setRoles(userDto.getRoles().stream()
                .map(RoleType::valueOf)
                .collect(Collectors.toSet()));

        User savedUser = userRepository.save(user);
        return toResponseDto(savedUser);
    }

    public UserResponseDto updateUser(UUID id, UserRequestDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id: " + id));

        if (!user.getEmail().equals(userDto.getEmail()) && userRepository.existsByEmail(userDto.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists: " + userDto.getEmail());
        }
        if (!user.getUsername().equals(userDto.getUsername()) && userRepository.existsByUsername(userDto.getUsername())) {
            throw new UserAlreadyExistsException("Username already exists: " + userDto.getUsername());
        }

        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        if (userDto.getPassword() != null && !userDto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        user.setRoles(userDto.getRoles().stream()
                .map(RoleType::valueOf)
                .collect(Collectors.toSet()));

        User updatedUser = userRepository.save(user);
        return toResponseDto(updatedUser);
    }

    public void deleteUser(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id: " + id));
        userRepository.delete(user);
    }

    private UserResponseDto toResponseDto(User user) {
        Set<String> roles = user.getRoles().stream()
                .map(Enum::name)
                .collect(Collectors.toSet());

        Profile profile = user.getProfile();
        ProfileResponseDto profileDto = null;
        if (profile != null) {
            profileDto = new ProfileResponseDto(
                    profile.getId(),
                    profile.getProfilePicture(),
                    profile.getBio(),
                    profile.getAddress(),
                    profile.getDocuments(),
                    profile.getSchoolGrade(),
                    profile.getSchoolType()
            );
        }

        return new UserResponseDto(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                roles,
                profileDto
        );
    }

    private void checkIfUserExists(UserRequestDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists: " + userDto.getEmail());
        }
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new UserAlreadyExistsException("Username already exists: " + userDto.getUsername());
        }
    }
}
