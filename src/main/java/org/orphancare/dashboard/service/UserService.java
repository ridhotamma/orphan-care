package org.orphancare.dashboard.service;

import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.*;
import org.orphancare.dashboard.entity.Profile;
import org.orphancare.dashboard.entity.RoleType;
import org.orphancare.dashboard.entity.User;
import org.orphancare.dashboard.exception.ResourceNotFoundException;
import org.orphancare.dashboard.exception.UserAlreadyExistsException;
import org.orphancare.dashboard.repository.ProfileRepository;
import org.orphancare.dashboard.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
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

    public UserDto changeUserPassword(ChangePasswordUserDto changePasswordUserDto) throws AccessDeniedException {
        String currentUsername = getCurrentUsername();
        User existingUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username " + currentUsername));

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

    public List<UserDto.UserWithProfileDto> getAllUsersWithShortProfile() {
        return userRepository.findAll().stream()
                .map(this::convertToWithProfileDto)
                .collect(Collectors.toList());
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

    private UserDto.UserWithProfileDto convertToWithProfileDto(User user) {
        Profile profile = profileRepository.findByUserId(user.getId()).orElseGet(Profile::new);

        ProfileDto.ShortResponse profileDto = new ProfileDto.ShortResponse();
        profileDto.setFullName(profile.getFullName());
        profileDto.setProfilePicture(profile.getProfilePicture());

        UserDto.UserWithProfileDto userDto = new UserDto.UserWithProfileDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setUsername(user.getUsername());
        userDto.setRoles(user.getRoles().stream().map(Enum::name).collect(Collectors.toSet()));
        userDto.setActive(user.isActive());
        userDto.setProfile(profileDto);
        return userDto;
    }

    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
}
