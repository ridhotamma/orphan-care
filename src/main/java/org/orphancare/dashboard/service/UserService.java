package org.orphancare.dashboard.service;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.orphancare.dashboard.dto.*;
import org.orphancare.dashboard.entity.Profile;
import org.orphancare.dashboard.entity.User;
import org.orphancare.dashboard.exception.ResourceNotFoundException;
import org.orphancare.dashboard.exception.UserAlreadyExistsException;
import org.orphancare.dashboard.mapper.UserMapper;
import org.orphancare.dashboard.repository.UserRepository;
import org.orphancare.dashboard.util.RequestUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final UserMapper userMapper;
    private final RequestUtil requestUtil;

    public UserDto createUser(CreateUserDto createUserDto) {
        if (userRepository.existsByEmail(createUserDto.getEmail())) {
            throw new UserAlreadyExistsException("Email is already in use");
        }
        if (userRepository.existsByUsername(createUserDto.getUsername())) {
            throw new UserAlreadyExistsException("Username is already in use");
        }
        User user = userMapper.toEntity(createUserDto);
        user.setPassword(passwordEncoder.encode(createUserDto.getPassword()));

        Profile profile = userMapper.toProfileEntity(createUserDto);
        profile.setUser(user);
        user.setProfile(profile);

        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
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

        userMapper.updateUserFromDto(updateUserDto, existingUser);

        User updatedUser = userRepository.save(existingUser);
        return userMapper.toDto(updatedUser);
    }

    public UserDto changeUserPassword(UserPasswordChangeDto changePasswordUserDto) throws BadRequestException {
        String currentUsername = requestUtil.getCurrentUsername();
        User existingUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username " + currentUsername));

        if (!passwordEncoder.matches(changePasswordUserDto.getOldPassword(), existingUser.getPassword())) {
            throw new BadRequestException("Old password is wrong");
        }

        existingUser.setPassword(passwordEncoder.encode(changePasswordUserDto.getNewPassword()));
        User updatedUser = userRepository.save(existingUser);
        return userMapper.toDto(updatedUser);
    }

    public UserDto changeUserPasswordByAdmin(UserPasswordChangeDto.AdminChange adminChange) {
        User existingUser = userRepository.findById(adminChange.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + adminChange.getUserId()));
        existingUser.setPassword(passwordEncoder.encode(adminChange.getNewPassword()));
        User updatedUser = userRepository.save(existingUser);
        return userMapper.toDto(updatedUser);
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
        return userMapper.toDto(user);
    }

    public List<UserDto.UserWithProfileDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserWithProfileDto)
                .collect(Collectors.toList());
    }
}
